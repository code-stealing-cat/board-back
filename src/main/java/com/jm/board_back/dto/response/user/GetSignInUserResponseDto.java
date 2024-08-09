package com.jm.board_back.dto.response.user;

import com.jm.board_back.common.ResponseCode;
import com.jm.board_back.common.ResponseMessage;
import com.jm.board_back.dto.response.ResponseDto;
import com.jm.board_back.entity.UserEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 로그인을 시도한 유저에 대한 응답처리
 */
@Getter
public class GetSignInUserResponseDto extends ResponseDto {

    /* 사용자 이메일 */
    private String email;
    /* 사용자 닉네임 */
    private String nickname;
    /* 사용자 프로필 사진 URL */
    private String profileImage;

    private GetSignInUserResponseDto(UserEntity userEntity) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.email = userEntity.getEmail();
        this.nickname = userEntity.getNickname();
        this.profileImage = userEntity.getProfileImage();
    }

    /**
     * 사용자 로그인이 성공적으로 되면 GetSignInUserResponseDto 에 응답객체를 답아 내보내기
     *
     * @param userEntity email 로 찾은 사옹자 객체 데이터
     * @return 성공 코드와 인증된 사용자 데이터 (GetSignInUserResponseDto 타입)
     */
    public static ResponseEntity<GetSignInUserResponseDto> success(UserEntity userEntity) {
        GetSignInUserResponseDto result = new GetSignInUserResponseDto(userEntity);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    /**
     * 존재하지 않는 사용자에 대한 응답처리
     *
     * @return 존재하지 않는 사용자 코드와 메세지
     */
    public static ResponseEntity<ResponseDto> notExistUser() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_USER, ResponseMessage.NOT_EXISTED_USER);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
    }
}
