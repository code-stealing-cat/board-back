package com.jm.board_back.controller;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.dto.request.board.PostBoardRequestDto;
import com.jm.board_back.dto.request.board.PostCommentRequestDto;
import com.jm.board_back.dto.response.board.*;
import com.jm.board_back.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@TimeTraceAnnotation
@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    /**
     * 게시물 상세
     *
     * @param boardNumber 게시물 번호
     */
    @GetMapping("/{boardNumber}")
    public ResponseEntity<? super GetBoardResponseDto> getBoard(@PathVariable("boardNumber") Integer boardNumber) {
        return boardService.getBoard(boardNumber);
    }

    /**
     * 좋아요 목록
     *
     * @param boardNumber 게시물 번호
     */
    @GetMapping("/{boardNumber}/favorite-list")
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(@PathVariable("boardNumber") Integer boardNumber) {
        return boardService.getFavoriteList(boardNumber);
    }

    @GetMapping("/{boardNumber}/comment-list")
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(@PathVariable("boardNumber") Integer boardNumber) {
        return boardService.getCommentList(boardNumber);
    }

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

    /**
     * 댓글 작성
     *
     * @param requestBody
     * @param boardNumber
     * @param email
     * @return
     */
    @PostMapping("/{boardNumber}/comment")
    public ResponseEntity<? super PostCommentResponseDto> postComment(@RequestBody @Valid PostCommentRequestDto requestBody, @PathVariable("boardNumber") Integer boardNumber, @AuthenticationPrincipal String email) {
        return boardService.postComment(requestBody, boardNumber, email);
    }

    /**
     * 좋아요
     *
     * @param boardNumber 게시물 번호
     * @param email       사용자 이메일
     * @return PutFavoriteResponseDto
     */
    @PutMapping("/{boardNumber}/favorite")
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(@PathVariable("boardNumber") Integer boardNumber, @AuthenticationPrincipal String email) {
        return boardService.putFavorite(boardNumber, email);
    }
}
