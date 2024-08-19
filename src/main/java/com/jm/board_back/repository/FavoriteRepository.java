package com.jm.board_back.repository;

import com.jm.board_back.entity.FavoriteEntity;
import com.jm.board_back.entity.primaryKey.FavoritePk;
import com.jm.board_back.repository.resultSet.GetFavoriteListResultSet;
import jakarta.persistence.QueryHint;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<FavoriteEntity, FavoritePk> {
    @QueryHints({@QueryHint(name = "org.hibernate.comment", value = "findByBoardNumberAndUserEmail")})
    FavoriteEntity findByBoardNumberAndUserEmail(Integer boardNumber, String userEmail);

    @Query(value = "SELECT U.email AS email, U.nickname AS nickname, U.profile_image AS profileImage FROM favorite AS F INNER JOIN `user` AS U ON F.user_email = U.email WHERE F.board_number = ?1", nativeQuery = true)
    List<GetFavoriteListResultSet> getFavoriteList(Integer boardNumber);

    @Transactional
    @QueryHints({@QueryHint(name = "org.hibernate.comment", value = "deleteByBoardNumber")})
    void deleteByBoardNumber(Integer boardNumber);
}