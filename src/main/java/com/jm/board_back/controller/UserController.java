package com.jm.board_back.controller;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.dto.response.user.GetSignInUserResponseDto;
import com.jm.board_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TimeTraceAnnotation
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    /**
     * 사용자가 로그인한 또는 로그아웃 후 User 객체를 갱신하기
     * `@AuthenticationPrincipal` 는 Spring boot 에서 해당 요청에 대하여 인증을 반드시 필요로 하기 때문에
     * 요청 인증정보인 access token 을 header 에 authorization 필드에 포함해서 보내주기 위해 사용
     *
     * @param email 사용자 이메일
     * @return User 객체
     */
    @GetMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(@AuthenticationPrincipal String email) {
        return userService.getSignInUser(email);
    }
}
