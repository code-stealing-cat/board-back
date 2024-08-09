package com.jm.board_back.dto.response;

import com.jm.board_back.common.ResponseCode;
import com.jm.board_back.common.ResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 사용자가 요청한 것에 대한 응답을 처리하기 위한 클래스로 각 기능에 때라 상속받아 사용할 수 있도록 만든 부모클래스로 사용
 */
@Getter
@AllArgsConstructor
public class ResponseDto {
    private String code;
    private String message;

    /**
     * 데이터베이스 오류
     *
     * @return DBE, Database error.
     */
    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

    /**
     * 사용자 로그인데이터 인증 오류
     *
     * @return BAD_REQUEST
     */
    public static ResponseEntity<ResponseDto> validationFailed() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.VALIDATION_FAILED, ResponseMessage.VALIDATION_FAILED);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
