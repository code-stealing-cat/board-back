package com.jm.board_back.repository.resultSet;

public interface GetFavoriteListResultSet {
    /* 사용자 이메일 */
    String getEmail();

    /* 사용자 닉네임 */
    String getNickname();

    /* 사용자 프로필 사진 URL */
    String getProfileImage();
}
