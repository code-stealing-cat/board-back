package com.jm.board_back.provider;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Jwt 토큰 생성하고 검증하여 반환
 */
@Slf4j
@Component // 자동 bean 등록
@TimeTraceAnnotation
public class JwtProvider {

    @Value("${secret-key}")
    private String secretKey;

    /**
     * Jwt 토큰 생성 메소드
     *
     * @param email 요청받은 email
     * @return jwt token
     */
    public String create(String email) {
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS)); // 만료기간 1시간
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();
    }

    /**
     * Jwt 검증 메소드
     *
     * @param jwt 인증할 token
     * @return 클레임에서 주체(subject)를 추출한다. 여기서는 email 이 반환된다.
     */
    public String validate(String jwt) {
        Claims claims = null;
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {
            /*
            JWT 토큰을 파싱하고 서명을 검증하여 토큰의 클레임 부분을 가져온다.
            여기서 secretKey 는 토큰을 생성할 때 사용된 비밀키이다. 이 비밀키를 사용해 토큰의 서명이 유효한지 확인한다.
            이후 JwtAuthenticationFilter 에서 토큰에 대한 검증 후 처리를 한다.
             */
            claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
        } catch (Exception exception) {
            log.error("토큰 검증 오류", exception);
            return null;
        }

        return claims.getSubject();
    }
}
