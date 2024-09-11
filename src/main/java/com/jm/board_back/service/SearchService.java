package com.jm.board_back.service;

import com.jm.board_back.dto.response.search.GetPopularListResponseDto;
import org.springframework.http.ResponseEntity;

public interface SearchService {
    ResponseEntity<? super GetPopularListResponseDto> getPopularList();
}
