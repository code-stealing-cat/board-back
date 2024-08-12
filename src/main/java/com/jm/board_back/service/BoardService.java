package com.jm.board_back.service;

import com.jm.board_back.dto.request.board.PostBoardRequestDto;
import com.jm.board_back.dto.response.board.GetBoardResponseDto;
import com.jm.board_back.dto.response.board.GetFavoriteListResponseDto;
import com.jm.board_back.dto.response.board.PostBoardResponseDto;
import com.jm.board_back.dto.response.board.PutFavoriteResponseDto;
import org.springframework.http.ResponseEntity;

public interface BoardService {
    // 게시물 상세
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);

    // 좋아요 목록
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);

    // 게시물 등록
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);

    // 좋아요
    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);
}
