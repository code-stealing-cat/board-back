package com.jm.board_back.repository.resultSet;

/**
 * JPA 는 리포지토리에서 @Query 어노테이션을 사용하거나 JPQL, 네이티브쿼리 등을 실행하면
 * 데이터베이스로부터 결과가 반환된다 이게 일반적인 ResultSet 의 형태 데이터이다.
 * JPA 는 리플렉션을 사용해 해당 인터페이스나 DTO 클래서의 메서드 이름을 검사한다.
 * 메서드 이름이 getTitle() 이라면 JPA 는 이 메서드가 매핑될지 결정한다.
 * 일반적으로 메서드 이름에서 get 을 제외한 부분을 컬럼 이름으로 매핑한다.
 */
public interface GetBoardResultSet {
    /* 게시물 번호 */
    Integer getBoardNumber();

    /* 게시물 제목 */
    String getTitle();

    /* 게시물 내용 */
    String getContent();

    /* 게시물 작성 날짜 */
    String getWriteDateTime();

    /* 게시물 작성자 이메일 */
    String getWriterEmail();

    /* 게시물 작성자 닉네임 */
    String getWriterNickname();

    /* 게시물 작성자 프로필 이미지 */
    String getWriterProfileImage();
}
