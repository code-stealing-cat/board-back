package com.jm.board_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "image")
@Table(name = "image")
public class ImageEntity {
    /* 이미지 번호 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sequence;
    /* 게시물 번호 */
    private int boardNumber;
    /* 이미지 URL */
    private String image;

    /**
     * 사용자로부터 받은 PostBoardRequestDto 타입의 데이터를  ImageEntity 타입을 변환하여 저장하기 위한 생성자
     *
     * @param boardNumber 게시물 저장 후 자동으로 생성된 게시물 번호
     * @param image       사용자가 등록한 image url
     */
    public ImageEntity(int boardNumber, String image) {
        this.boardNumber = boardNumber;
        this.image = image;
    }
}
