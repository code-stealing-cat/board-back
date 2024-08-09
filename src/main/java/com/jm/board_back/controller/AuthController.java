package com.jm.board_back.controller;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.dto.request.auth.SignInRequestDto;
import com.jm.board_back.dto.request.auth.SignUpRequestDto;
import com.jm.board_back.dto.response.auth.SignInResponseDto;
import com.jm.board_back.dto.response.auth.SignUpResponseDto;
import com.jm.board_back.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
컨트롤러는 레이어드 아키텍쳐 상에서 컨트롤러는 요청(입력)을 받고 그에 대한 응답을 주는 것에 대한 검증처리를 하는 구역이다.
비지니스 로직은 서비스에서 진행해야 한다.
 */
@RestController
@TimeTraceAnnotation
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor // @RequiredArgsConstructor 는 초기화 되지않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성해준다.
public class AuthController {

    private final AuthService authService;

    /**
     * 회원가입
     * Valid 실패시 응답 { "code:":"AF","message":"Authorization Failed" }
     *
     * @param requestBody 사용자로부터 입력 받은 요청 데이터 SignUpRequestDto 객체
     * @return 반환타입은 ResponseEntity 의 SignUpResponseDto 또는 부모클래스인 ResponseDto 객체를 반환한다.
     */
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto requestBody) {
        return authService.signUp(requestBody);
    }

    /**
     * 로그인
     * Valid 실패시 응답 { "code:":"VF","message":"Validation failed." } 이메일 또는 비밀번호가 null 일 경우
     * 로그인 실패 { "code:":"SF","message":"Login information mismatch." } 이메일 또는 비밀번호 불일치
     *
     * @param requestBody 로그인을 위해 사용자가 입력한 email, password
     * @return HttpStatus status code, message, token, expirationTime
     */
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(@RequestBody @Valid SignInRequestDto requestBody) {
        return authService.signIn(requestBody);
    }
}