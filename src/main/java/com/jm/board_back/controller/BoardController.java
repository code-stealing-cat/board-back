package com.jm.board_back.controller;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.dto.request.board.PatchBoardRequestDto;
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

    @GetMapping("/{boardNumber}/increase-view-count")
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(@PathVariable("boardNumber") Integer boardNumber) {
        return boardService.increaseViewCount(boardNumber);
    }

    @GetMapping("/latest-list")
    public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {
        return boardService.getLatestBoardList();
    }

    @GetMapping("/top-3")
    public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {
        return boardService.getTop3BoardList();
    }

    /**
     * 게시물 등록
     *
     * @param requestBody 사용자가 게시물 등록을 위해 작성한 데이터
     * @param email       사용자 이메일
     */
    @PostMapping("")
    public ResponseEntity<? super PostBoardResponseDto> postBoard(@RequestBody @Valid PostBoardRequestDto requestBody, @AuthenticationPrincipal String email) {
        return boardService.postBoard(requestBody, email);
    }

    /**
     * 댓글 작성
     *
     * @param requestBody 사용자가 작성한 댓글 내용
     * @param boardNumber 작성할 댓글에 대한 게시물
     * @param email       댓글 작성자 이메일
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
     */
    @PutMapping("/{boardNumber}/favorite")
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(@PathVariable("boardNumber") Integer boardNumber, @AuthenticationPrincipal String email) {
        return boardService.putFavorite(boardNumber, email);
    }

    @PatchMapping("/{boardNumber}")
    public ResponseEntity<? super PatchBoardResponseDto> patchBoard(@RequestBody @Valid PatchBoardRequestDto requestBody, @PathVariable("boardNumber") Integer boardNumber, @AuthenticationPrincipal String email) {
        return boardService.patchBoard(requestBody, boardNumber, email);
    }

    @DeleteMapping("/{boardNumber}")
    public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(@PathVariable("boardNumber") Integer boardNumber, @AuthenticationPrincipal String email) {
        return boardService.deleteBoard(boardNumber, email);
    }

}
