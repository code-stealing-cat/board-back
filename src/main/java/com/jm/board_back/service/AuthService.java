package com.jm.board_back.service;

import com.jm.board_back.dto.request.auth.SignInRequestDto;
import com.jm.board_back.dto.request.auth.SignUpRequestDto;
import com.jm.board_back.dto.response.auth.SignInResponseDto;
import com.jm.board_back.dto.response.auth.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    // 회원가입
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);

    // 로그인
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
