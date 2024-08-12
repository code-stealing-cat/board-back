package com.jm.board_back.service;

import com.jm.board_back.dto.request.board.PostBoardRequestDto;
import com.jm.board_back.dto.request.board.PostCommentRequestDto;
import com.jm.board_back.dto.response.board.*;
import org.springframework.http.ResponseEntity;

public interface BoardService {
    // 게시물 상세
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);

    // 좋아요 목록
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);

    // 댓글 목록
    ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber);

    // 게시물 등록
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);

    // 댓글 등록
    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email);

    // 좋아요
    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);
}
