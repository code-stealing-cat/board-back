package com.jm.board_back.repository;

import com.jm.board_back.entity.CommentEntity;
import com.jm.board_back.repository.resultSet.GetCommentListResultSet;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    @QueryHints({@QueryHint(name = "org.hibernate.comment", value = "getCommentList")})
    @Query(value = "SELECT U.nickname AS nickname, U.nickname AS nickname, C.content AS content, C.write_datetime writeDatetime, C.write_datetime AS writeDateTime FROM comment AS C INNER JOIN `user` AS U ON C.user_email = U.email WHERE C.board_number = ?1 ORDER BY C.write_datetime DESC", nativeQuery = true)
    List<GetCommentListResultSet> getCommentList(Integer boardNumber);
}
