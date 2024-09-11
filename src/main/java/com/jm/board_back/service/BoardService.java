package com.jm.board_back.service;

import com.jm.board_back.dto.request.board.PatchBoardRequestDto;
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

    // 최근 게시물 가져오기
    ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList();

    // top3
    ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList();

    // 게시물 등록
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);

    // 댓글 등록
    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email);

    // 좋아요
    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);

    // 게시글 수정
    ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email);

    // 조회수 증가
    ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber);

    // 게시물 삭제
    ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email);
}
