package com.jm.board_back.repository;

import com.jm.board_back.entity.BoardListViewEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardListViewRepository extends JpaRepository<BoardListViewEntity, Integer> {
    @QueryHints({@QueryHint(name = "org.hibernate.comment", value = "findByOrderByWriteDatetimeDesc")})
    List<BoardListViewEntity> findByOrderByWriteDatetimeDesc();
}