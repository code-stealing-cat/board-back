package com.jm.board_back.dto.response.board;

import com.jm.board_back.common.ResponseCode;
import com.jm.board_back.common.ResponseMessage;
import com.jm.board_back.dto.response.ResponseDto;
import com.jm.board_back.entity.ImageEntity;
import com.jm.board_back.repository.resultSet.GetBoardResultSet;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시물 상세 내용을 불러오기 위한 응답 Dto 객체
 */
@Getter
public class GetBoardResponseDto extends ResponseDto {
    /* 게시물 번호 */
    private int boardNumber;
    /* 게시물 제목 */
    private String title;
    /* 게시물 내용 */
    private String content;
    /* 게시물 이미지 URL List */
    private List<String> boardImageList;
    /* 게시물 작성 날짜 */
    private String writeDatetime;
    /* 게시물 작성자 이메일 */
    private String writerEmail;
    /* 게시물 작성자 닉네임 */
    private String writerNickname;
    /* 게시물 작성자 프로필 이미지 */
    private String writerProfileImage;

    /**
     * @param resultSet     DB 로 부터 게시물을 조회하여 얻은 게시물 데이터
     * @param imageEntities DB 로 부터 게시물을 조회하여 얻은 게시물 이미지 데이터
     */
    private GetBoardResponseDto(GetBoardResultSet resultSet, List<ImageEntity> imageEntities) {
        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);

        // 이미지 URL 꺼내서 GetBoardResponseDto 의 boardImageList 에 담아주기
        List<String> boardImageList = new ArrayList<>();
        for (ImageEntity imageEntity : imageEntities) {
            String boardImage = imageEntity.getImage();
            boardImageList.add(boardImage);
        }

        this.boardNumber = resultSet.getBoardNumber();
        this.title = resultSet.getTitle();
        this.content = resultSet.getContent();
        this.boardImageList = boardImageList;
        this.writeDatetime = resultSet.getWriteDateTime();
        this.writerEmail = resultSet.getWriterEmail();
        this.writerNickname = resultSet.getWriterNickname();
        this.writerProfileImage = resultSet.getWriterProfileImage();
    }

    // 성공
    public static ResponseEntity<GetBoardResponseDto> success(GetBoardResultSet resultSet, List<ImageEntity> imageEntities) {
        GetBoardResponseDto result = new GetBoardResponseDto(resultSet, imageEntities);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 존재하지 않는 게시물
    public static ResponseEntity<ResponseDto> notExistBoard() {
        ResponseDto result = new ResponseDto(ResponseCode.NOT_EXISTED_BOARD, ResponseMessage.NOT_EXISTED_BOARD);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
    }
}
