package com.jm.board_back.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 로그인할 때 사용자가 서버로 보낼 데이터의 객체 형태
 */
@Getter
@Setter
@NoArgsConstructor
public class SignInRequestDto {
    /* 사용자 이메일 */
    @NotBlank
    private String email;
    /* 사용자 비밀번호 */
    @NotBlank
    private String password;
}
