package com.jm.board_back.entity;

import com.jm.board_back.dto.request.board.PostBoardRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "board") // 엔터티 이름
@Table(name = "board") // board 테이블과 매핑
public class BoardEntity {
    /* 게시물 번호 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // autoincrement
    private int boardNumber;
    /* 게시물 제목 */
    private String title;
    /* 게시물 내용 */
    private String content;
    /* 게시물 작성 날짜 */
    private String writeDatetime;
    /* 게시물 좋아요 수 */
    private int favoriteCount;
    /* 게시물 댓글 수 */
    private int commentCount;
    /* 게시물 조회수 */
    private int viewCount;
    /* 게시물 작성자 이메일 */
    private String writerEmail;

    /**
     * 사용자로부터 받은 PostBoardRequestDto 타입의 데이터를  BoardEntity 타입을 변환하여 저장하기 위한 생성자
     *
     * @param dto   사용자가 입력한 게시물 제목과 내용 이미지리스트가 들어있으며 BoardEntity 에는 제목과 내용만 사용한다.
     * @param email 작성자 email
     */
    public BoardEntity(PostBoardRequestDto dto, String email) {
        Date now = Date.from(Instant.now());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String writeDatetime = simpleDateFormat.format(now);

        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.writeDatetime = writeDatetime;
        this.favoriteCount = 0;
        this.commentCount = 0;
        this.viewCount = 0;
        this.writerEmail = email;
    }

    public void increaseViewCount() {
        this.viewCount++;
    }

    public void increaseFavoriteCount() {
        this.favoriteCount++;
    }

    public void decreaseFavoriteCount() {
        this.favoriteCount--;
    }
}
