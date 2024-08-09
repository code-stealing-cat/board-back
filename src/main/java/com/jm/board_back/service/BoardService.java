package com.jm.board_back.service;

import com.jm.board_back.dto.request.board.PostBoardRequestDto;
import com.jm.board_back.dto.response.board.PostBoardResponseDto;
import org.springframework.http.ResponseEntity;

public interface BoardService {
    // 게시물 등록
    ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email);
}
