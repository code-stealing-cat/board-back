package com.jm.board_back.service;

import com.jm.board_back.dto.response.user.GetSignInUserResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    // 사용자가 로그인한 또는 로그아웃 후 User 객체를 갱신하기
    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
}
