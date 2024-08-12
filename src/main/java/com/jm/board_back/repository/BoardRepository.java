package com.jm.board_back.repository;

import com.jm.board_back.entity.BoardEntity;
import com.jm.board_back.repository.resultSet.GetBoardResultSet;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    @QueryHints({@QueryHint(name = "org.hibernate.comment", value = "existsByBoardNumber")})
    boolean existsByBoardNumber(Integer boardNumber);

    @QueryHints({@QueryHint(name = "org.hibernate.comment", value = "getBoard")})
    @Query(value = "SELECT B.board_number AS boardNumber, B.title AS title, B.content AS content, B.write_datetime AS writeDateTime, B.writer_email AS writerEmail, U.nickname AS writerNickname, U.profile_image AS writerProfileImage FROM board as B INNER JOIN `user` as U ON B.writer_email = U.email WHERE board_number = ?1 ", nativeQuery = true)
    GetBoardResultSet getBoard(Integer boardNumber);

    @QueryHints({@QueryHint(name = "org.hibernate.comment", value = "findByBoardNumber")})
    BoardEntity findByBoardNumber(Integer boardNumber);

}
