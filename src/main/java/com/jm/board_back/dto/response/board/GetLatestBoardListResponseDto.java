package com.jm.board_back.dto.response.board;

import com.jm.board_back.common.ResponseCode;
import com.jm.board_back.common.ResponseMessage;
import com.jm.board_back.dto.object.BoardListItem;
import com.jm.board_back.dto.response.ResponseDto;
import com.jm.board_back.entity.BoardListViewEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Getter
public class GetLatestBoardListResponseDto extends ResponseDto {
    private List<BoardListItem> latestList;

    private GetLatestBoardListResponseDto(List<BoardListViewEntity> boardEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.latestList = BoardListItem.getList(boardEntities);
    }

    public static ResponseEntity<GetLatestBoardListResponseDto> success(List<BoardListViewEntity> boardListViewEntities) {
        GetLatestBoardListResponseDto result = new GetLatestBoardListResponseDto(boardListViewEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
