package com.jm.board_back.dto.response.board;

import com.jm.board_back.common.ResponseCode;
import com.jm.board_back.common.ResponseMessage;
import com.jm.board_back.dto.object.FavoriteListItem;
import com.jm.board_back.dto.response.ResponseDto;
import com.jm.board_back.repository.resultSet.GetFavoriteListResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * 좋아요 누른 게시물 리스트를 불러오기 위한 응답 Dto 객체
 */
@Getter
public class GetFavoriteListResponseDto extends ResponseDto {
    private List<FavoriteListItem> favoriteList;

    /**
     * DB 에서 가져온 GetFavoriteListResultSet 타입의 데이터를 GetFavoriteListResponseDto 인 응답객체로 변경하기
     *
     * @param resultSets GetFavoriteListResponseDto
     */
    private GetFavoriteListResponseDto(List<GetFavoriteListResultSet> resultSets) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.favoriteList = FavoriteListItem.copyList(resultSets);
    }

    /**
     * 좋아요 목록 불러오기 성공했을 경우 응답을 위해 GetFavoriteListResponseDto 로 변환하기
     *
     * @param resultSets 좋아요 목록
     */
    public static ResponseEntity<GetFavoriteListResponseDto> success(List<GetFavoriteListResultSet> resultSets) {
        GetFavoriteListResponseDto result = new GetFavoriteListResponseDto(resultSets);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 존재하지 않는 게시물
    public static ResponseEntity<ResponseDto> noExistBoard() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BOARD, ResponseMessage.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
