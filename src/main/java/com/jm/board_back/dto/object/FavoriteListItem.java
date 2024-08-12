package com.jm.board_back.dto.object;

import com.jm.board_back.repository.resultSet.GetFavoriteListResultSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteListItem {
    /* 사용자 이메일 */
    private String email;
    /* 사용자 닉네임 */
    private String nickname;
    /* 사용자 프로필 사진 URL */
    private String profileImage;

    /**
     * DB 에서 값을 가져올 때 GetFavoriteListResultSet 타입으로 받아 FavoriteListItem 타입으로 만드는 생성자
     * DB 에서 조회한 값을 GetFavoriteListResponseDto 즉 응답객체로 만들기 위한 ListItem
     *
     * @param resultSet DB 에서 조회한 좋아요 목록
     */
    public FavoriteListItem(GetFavoriteListResultSet resultSet) {
        this.email = resultSet.getEmail();
        this.nickname = resultSet.getNickname();
        this.profileImage = resultSet.getProfileImage();
    }

    /**
     * DB 에서 GetFavoriteListResultSet 타입으로 가져온 값을 FavoriteListItem 형태로 바꾸어 GetFavoriteListResponseDto 타입으로 반환하기 위해 사용
     *
     * @param resultSets DB 에서 가져온 좋아요 목록
     * @return FavoriteListItem 타입의 좋아요 목록
     */
    public static List<FavoriteListItem> copyList(List<GetFavoriteListResultSet> resultSets) {
        List<FavoriteListItem> list = new ArrayList<>();
        for (GetFavoriteListResultSet resultSet : resultSets) {
            FavoriteListItem favoriteListItem = new FavoriteListItem(resultSet);
            list.add(favoriteListItem);
        }
        return list;
    }
}
