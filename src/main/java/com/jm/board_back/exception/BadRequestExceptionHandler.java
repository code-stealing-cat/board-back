package com.jm.board_back.exception;

import com.jm.board_back.dto.response.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // 전역적으로 ExceptionHandler 를 적용해준다.
public class BadRequestExceptionHandler {

    /**
     * 컨트롤러에서 @Valid 오류(MethodArgumentNotValidException), 잘못된 타입(HttpMessageNotReadableException) 오류를 보낼 경우
     * ExceptionHandler 가 받아서 처리하여  validationFailed()를 내보낸다.
     *
     * @param exception 오류 내용
     * @return 검증실패
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<ResponseDto> validationExceptionHandler(Exception exception) {
        return ResponseDto.validationFailed();
    }
}
