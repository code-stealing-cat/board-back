package com.jm.board_back.controller;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.dto.request.board.PostBoardRequestDto;
import com.jm.board_back.dto.response.board.PostBoardResponseDto;
import com.jm.board_back.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@TimeTraceAnnotation
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * 게시물 등록
     *
     * @param requestBody 사용자가 게시물 등록을 위해 작성한 데이터
     * @param email       사용자 이메일
     * @return PostBoardResponseDto
     */
    @PostMapping("")
    public ResponseEntity<? super PostBoardResponseDto> postBoard(@RequestBody @Valid PostBoardRequestDto requestBody, @AuthenticationPrincipal String email) {
        return boardService.postBoard(requestBody, email);
    }
}
