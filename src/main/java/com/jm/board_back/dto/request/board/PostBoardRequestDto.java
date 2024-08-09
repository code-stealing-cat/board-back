package com.jm.board_back.dto.request.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 사용자가 게시물을 등록할 때 받을 데이터 객체
 */
@Getter
@Setter
@NoArgsConstructor
public class PostBoardRequestDto {
    /* 게시물 제목 */
    @NotBlank
    private String title;
    /* 게시물 내용 */
    @NotBlank
    private String content;
    /* 게시물 이미지 리스트 */
    @NotNull // 빈배열이 올 수 있지만 해당 필드는 반드시 있어야 한다는 뜻
    private List<String> boardImageList;
}
