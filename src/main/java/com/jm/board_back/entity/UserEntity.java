package com.jm.board_back.entity;

import com.jm.board_back.dto.request.auth.SignUpRequestDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "user")
@Table(name = "user")
public class UserEntity {
    /* 사용자 이메일 */
    @Id
    private String email;
    /* 사용자 비밀번호 */
    private String password;
    /* 사용자 닉네임 */
    private String nickname;
    /* 사용자 휴대전화번호 */
    private String telNumber;
    /* 사용자 주소 */
    private String address;
    /* 사용자 상세 주소 */
    private String addressDetail;
    /* 사용자 프로필 사진 URL */
    private String profileImage;
    /* 개인정보 동의 여부 */
    private boolean agreedPersonal;

    /**
     * 회원가입을 위해 사용자가 입력한 SignUpRequestDto 형태의 데이터를 UserEntity 으로 변환하기 위한 생성자
     *
     * @param dto 사용자로부터 입력(request)받은 값
     */
    public UserEntity(SignUpRequestDto dto) {
        this.email = dto.getEmail();
        this.password = dto.getPassword();
        this.nickname = dto.getNickname();
        this.telNumber = dto.getTelNumber();
        this.address = dto.getAddress();
        this.addressDetail = dto.getAddressDetail();
        this.agreedPersonal = dto.getAgreedPersonal();
    }
}
