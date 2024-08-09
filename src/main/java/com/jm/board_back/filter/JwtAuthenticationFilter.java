package com.jm.board_back.filter;

import com.jm.board_back.provider.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor // 필수 생성자 만들기(final 로 지정된 것을 필수로 인식함)
public class JwtAuthenticationFilter extends OncePerRequestFilter { // OncePerRequestFilter 는 인증 인가와 같이 한번만 거쳐야하는 Logic 에 많이 사용함
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String token = parseBearerToken(request);

            // token 이 null 일 경우 로그인이 되어있지 않은 상황이기 때문에 요청한 url 의 컨트롤러로 돌아가게 된다.
            if (token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            // token 이 null 이 아닐 경우 검증을 통해 유효한 토큰인지 확인한다.
            String email = jwtProvider.validate(token);

            // 검증 후 email 에 값이 담겼다면 성공적으로 인증된 것이고 아니라면 요청한 url 의 컨트롤러로 돌아가게 된다.
            if (email == null) {
                filterChain.doFilter(request, response);
                return;
            }

            /*
            여기까지 왔다면 토큰이 정상적으로 왔다는 것이다.
            이 토큰을 컨텍스트에 등록해주어야 한다.

            컨텍스트에 등록하기 전에 사용자의 이름과 비밀번호, 권한을 포함하는 UsernamePasswordAuthenticationToken 을 생성한다.
             */

            // email : 사용자 ID, null 비밀번호(사용하지 않음), 권한
            AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    email, null, AuthorityUtils.NO_AUTHORITIES);

            // 리퀘스트에 디테일한 정보 설정
            // 인증 요청에 대한 세부정보 설정(웹인증 세부정보 소스)
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 컨텍스트 등록
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authenticationToken);

            // 채워 넣은 컨텍스트 상자를 외부에서 사용할 수 있도록 홀더에 다시 담아주기
            SecurityContextHolder.setContext(securityContext);
        } catch (Exception exception) {
            log.error("필터 오류", exception);
        }

        // 다음 필터로 넘기기
        filterChain.doFilter(request, response);
    }

    /**
     * Header 에서 인증키를 받고 인증 확인하여 토큰 반환
     *
     * @param request HttpServletRequest
     * @return token
     */
    private String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        boolean hasAutorization = StringUtils.hasText(authorization); // null 이거나 길이가 0이거나 공백이 들어있는지 확인
        if (!hasAutorization) return null;

        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) return null;

        return authorization.substring(7); // 토큰 뽑아내기
    }
}
