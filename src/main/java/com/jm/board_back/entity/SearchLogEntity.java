package com.jm.board_back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "search_log")
@Table(name = "search_log")
public class SearchLogEntity {
    /* 시퀀스 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sequence;
    /* 검색어 */
    private String searchWord;
    /* 관련 검색어 */
    private String relationWord;
    /* 관련 검색어 여부 */
    private boolean relation;
}
