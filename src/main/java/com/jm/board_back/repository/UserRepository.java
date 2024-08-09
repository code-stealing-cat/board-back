package com.jm.board_back.repository;

import com.jm.board_back.entity.UserEntity;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

// JpaRepository 는 두개의 제네릭을 받는데 왼쪽에는 엔터티의 저장소를 지정하고, 오른쪽에는 엔터티의 PK 타입을 지정한다.
@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    @QueryHints({@QueryHint(name = "org.hibernate.comment", value = "existsByNickname")})
    boolean existsByNickname(String nickname);

    @QueryHints({@QueryHint(name = "org.hibernate.comment", value = "existsByEmail")})
    boolean existsByEmail(String email);

    // email 은 unique 이기 때문에 반드시 1개 또는 0개가 반환된다. 때문에 return 값을 List 로 받을 필요가 없다.
    @QueryHints({@QueryHint(name = "org.hibernate.comment", value = "findByEmail")})
    UserEntity findByEmail(String email);

    @QueryHints({@QueryHint(name = "org.hibernate.comment", value = "existsByTelNumber")})
    boolean existsByTelNumber(String telNumber);
}
