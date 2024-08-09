package com.jm.board_back.service.implement;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.dto.request.auth.SignInRequestDto;
import com.jm.board_back.dto.request.auth.SignUpRequestDto;
import com.jm.board_back.dto.response.ResponseDto;
import com.jm.board_back.dto.response.auth.SignInResponseDto;
import com.jm.board_back.dto.response.auth.SignUpResponseDto;
import com.jm.board_back.entity.UserEntity;
import com.jm.board_back.provider.JwtProvider;
import com.jm.board_back.repository.UserRepository;
import com.jm.board_back.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 회원가입 기능을 구현한다는 것은 USER 테이블에 데이터를 넣는 작업을 말한다.
 * 이 작업을 하기 위해선 Repository 를 통해서 작업을 해야 한다.
 */
@Service
@Slf4j
@TimeTraceAnnotation
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService {

    /*
    @Autowired 를 사용하면 Bean 으로 등록되어있는 녀석을 알아서 의존성을 주입해준다.
    @Autowired
    public AuthServiceImplement(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    final 로 선언하게 되면 위와 같은 작업이 @RequiredArgsConstructor 를 통해 이루어진다.
     */
    private final UserRepository userRepository;

    /*
    final 로 선언하게되면 필수 멤버로 받아들여지기 때문에 외부에서 인스턴스를 생성해서 주입해주어야 한다.
    하지만 여기서는 외부에서 주입하지 않고 내부에서 주입하기 때문에 final 로 선언하지 않았다.
     */
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final JwtProvider jwtProvider;

    /**
     * 회원가입
     *
     * @param dto 사용자로부터 입력 받은 요청 데이터 SignUpRequestDto 객체
     * @return 반환타입은 ResponseEntity 의 SignUpResponseDto 또는 부모클래스인 ResponseDto 객체를 반환한다.
     */
    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {
        try {
            // 이메일 중복확인
            String email = dto.getEmail();
            boolean existedEmail = userRepository.existsByEmail(email);
            if (existedEmail) return SignUpResponseDto.duplicateEmail();

            // 닉네임 중복확인
            String nickname = dto.getNickname();
            boolean existedNickname = userRepository.existsByNickname(nickname);
            if (existedNickname) return SignUpResponseDto.duplicateNickname();

            // 전화번호 중복확인
            String telNumber = dto.getTelNumber();
            boolean existedTelNumber = userRepository.existsByTelNumber(telNumber);
            if (existedTelNumber) return SignUpResponseDto.duplicateTelNumber();

            // 비밀번호 암호화
            String password = dto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword);

            // 사용자로부터 입력(request)받은 값을 검증 후 UserEntity 에 넣어서 저장
            UserEntity userEntity = new UserEntity(dto);
            userRepository.save(userEntity);

        } catch (Exception exception) {
            log.error("회원가입 오류", exception);

            // 예기치 못한 오류 발생시 데이터베이스 에러 내보내기
            return ResponseDto.databaseError();
        }
        // 위 조건에 모두 만족하였고 에러가 없으면 성공 응답 내보내기
        return SignUpResponseDto.success();
    }

    /**
     * 로그인
     *
     * @param dto 로그인을 위해 사용자가 입력한 email, password
     * @return HttpStatus status code, message, token, expirationTime
     */
    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        String token = null;
        try {
            // 이메일 검증
            String email = dto.getEmail();
            UserEntity userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return SignInResponseDto.signInFailed();

            // 패스워드 검증
            String password = dto.getPassword();
            String encodedPassword = userEntity.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched) return SignInResponseDto.signInFailed();

            // 토큰 생성
            token = jwtProvider.create(email);

        } catch (Exception exception) {
            log.error("로그인 오류", exception);
            return ResponseDto.databaseError();
        }
        return SignInResponseDto.success(token);
    }
}
