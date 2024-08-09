package com.jm.board_back.repository;

import com.jm.board_back.entity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {
    List<ImageEntity> findByBoardNumber(Integer boardNumber);
}
