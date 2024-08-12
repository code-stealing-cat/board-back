package com.jm.board_back.dto.request.auth;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignUpRequestDto {
    /* 사용자 이메일 */
    @NotBlank // 문자열이 Null 이 아니며 빈 문자열이 아님 문자열 형태 변수에서만 사용할 수 있음
    @Email
    private String email;
    /* 사용자 비밀번호 */
    @NotBlank
    @Size(min = 8, max = 20)
    private String password;
    /* 사용자 닉네임 */
    @NotBlank
    private String nickname;
    /* 사용자 휴대전화번호 */
    @NotBlank
    @Pattern(regexp = "^[0-9]{11,13}$")
    private String telNumber;
    /* 사용자 주소 */
    @NotBlank
    private String address;
    private String addressDetail;
    /* 개인정보 동의 여부 */
    @NotNull // 참조형 변수에서 사용
    @AssertTrue // true 가 아니면 받지 않게함
    private Boolean agreedPersonal;
}
