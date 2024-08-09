package com.jm.board_back.entity.primaryKey;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

// 복합키 형태의 테이블의 PK 지정을 위한 클래스
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoritePk implements Serializable {
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "board_number")
    private int boardNumber;

    // Hibernate 에서 복합키로 사용하기 위한 설정
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FavoritePk that = (FavoritePk) o;
        return Objects.equals(userEmail, that.userEmail) && Objects.equals(boardNumber, that.boardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, boardNumber);
    }
}
