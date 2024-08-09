package com.jm.board_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "comment")
@Table(name = "comment")
public class CommentEntity {
    /* 댓글 번호 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentNumber;
    /* 댓글 내용 */
    private String content;
    /* 작성 날짜 및 시간 */
    private String writeDatetime;
    /* 사용자 이메일*/
    private String userEmail;
    /* 게시물 번호 */
    private int boardNumber;
}
