package com.jm.board_back.service.implement;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.dto.response.ResponseDto;
import com.jm.board_back.dto.response.search.GetPopularListResponseDto;
import com.jm.board_back.repository.SearchLogRepository;
import com.jm.board_back.repository.resultSet.GetPopularListResultSet;
import com.jm.board_back.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@TimeTraceAnnotation
public class SearchServiceImplement implements SearchService {
    private final SearchLogRepository searchLogRepository;

    @Override
    public ResponseEntity<? super GetPopularListResponseDto> getPopularList() {
        List<GetPopularListResultSet> resultSets = new ArrayList<>();
        try {
            resultSets = searchLogRepository.getPopularList();
        } catch (Exception exception) {
            log.error("인기 게시물 오류", exception);
            return ResponseDto.databaseError();
        }
        return GetPopularListResponseDto.success(resultSets);
    }
}
