package com.jm.board_back.entity;

import com.jm.board_back.entity.primaryKey.FavoritePk;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "favorite")
@Table(name = "favorite")
@IdClass(FavoritePk.class) // 복합키 표시
public class FavoriteEntity {
    /* 사용자 이메일 */
    @Id
    private String userEmail;
    /* 게시물 번호 */
    @Id
    private int boardNumber;
}
