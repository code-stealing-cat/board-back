package com.jm.board_back.service.implement;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.dto.request.board.PatchBoardRequestDto;
import com.jm.board_back.dto.request.board.PostBoardRequestDto;
import com.jm.board_back.dto.request.board.PostCommentRequestDto;
import com.jm.board_back.dto.response.ResponseDto;
import com.jm.board_back.dto.response.board.*;
import com.jm.board_back.entity.*;
import com.jm.board_back.repository.*;
import com.jm.board_back.repository.resultSet.GetBoardResultSet;
import com.jm.board_back.repository.resultSet.GetCommentListResultSet;
import com.jm.board_back.repository.resultSet.GetFavoriteListResultSet;
import com.jm.board_back.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@TimeTraceAnnotation
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final CommentRepository commentRepository;
    private final BoardListViewRepository boardListViewRepository;

    /**
     * 게시물 상세
     *
     * @param boardNumber 게시물 번호
     * @return GetBoardResponseDto
     */
    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {

        GetBoardResultSet resultSet = null;

        List<ImageEntity> imageEntities = new ArrayList<>();
        try {
            resultSet = boardRepository.getBoard(boardNumber);
            if (resultSet == null) return GetBoardResponseDto.noExistBoard();

            imageEntities = imageRepository.findByBoardNumber(boardNumber);
        } catch (Exception exception) {
            log.error("게시물 상세 가져오기 오류", exception);
            return ResponseDto.databaseError();
        }
        return GetBoardResponseDto.success(resultSet, imageEntities);
    }

    /**
     * 좋아요 목록
     *
     * @param boardNumber 게시물 번호
     * @return FavoriteListIem 을 갖고 있는 GetFavoriteListResponseDto 객체
     */
    @Override
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {
        List<GetFavoriteListResultSet> resultSets = new ArrayList<>();
        try {
            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard) return GetFavoriteListResponseDto.noExistBoard();

            resultSets = favoriteRepository.getFavoriteList(boardNumber);
        } catch (Exception exception) {
            log.error("좋아요 목록 가져오기 오류", exception);
            return ResponseDto.databaseError();
        }
        return GetFavoriteListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super GetCommentListResponseDto> getCommentList(Integer boardNumber) {
        List<GetCommentListResultSet> resultSets = new ArrayList<>();
        try {
            boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
            if (!existedBoard) return GetCommentListResponseDto.noExistBoard();

            resultSets = commentRepository.getCommentList(boardNumber);
        } catch (Exception exception) {
            log.error("댓글 목록 불러오기 오류", exception);
            return ResponseDto.databaseError();
        }
        return GetCommentListResponseDto.success(resultSets);
    }

    @Override
    public ResponseEntity<? super GetLatestBoardListResponseDto> getLatestBoardList() {
        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
        try {
            boardListViewEntities = boardListViewRepository.findByOrderByWriteDatetimeDesc();
        } catch (Exception exception) {
            log.error("최근 게시물 가져오기 오류", exception);
            return ResponseDto.databaseError();
        }
        return GetLatestBoardListResponseDto.success(boardListViewEntities);
    }

    @Override
    public ResponseEntity<? super GetTop3BoardListResponseDto> getTop3BoardList() {
        List<BoardListViewEntity> boardListViewEntities = new ArrayList<>();
        try {
            Date beforweek = Date.from(Instant.now().minus(7, ChronoUnit.DAYS));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String sevenDaysAgo = simpleDateFormat.format(beforweek);
            boardListViewEntities = boardListViewRepository.findTop3ByWriteDatetimeGreaterThanOrderByFavoriteCountDescCommentCountDescViewCountDescWriteDatetimeDesc(sevenDaysAgo);
        } catch (Exception exception) {
            log.error("탑3 리스트 불러오기 오류", exception);
        }
        return GetTop3BoardListResponseDto.success(boardListViewEntities);
    }

    /**
     * 게시물 등록
     *
     * @param dto   사용자가 게시물 등록을 위해 작성한 데이터
     * @param email 사용자 이메일
     * @return PostBoardResponseDto
     */
    @Override
    public ResponseEntity<? super PostBoardResponseDto> postBoard(PostBoardRequestDto dto, String email) {
        try {
            // 회원만 게시물을 작성할 수 있기 때문에 인증 확인 후 일치하는 사용자가 없다면 존재하지 않는 사용자 반환
            boolean existedEmail = userRepository.existsByEmail(email);
            if (!existedEmail) return PostBoardResponseDto.noExistUser();

            BoardEntity boardEntity = new BoardEntity(dto, email);
            boardRepository.save(boardEntity);

            // JPA 를 사용하여 엔티티를 저장하면 JPA 는 내부적으로 해당 엔터티를 데이터베이스에 저장하고 자동으로 생성된 키 값을 엔티티 객체에 설정해준다.
            int boardNumber = boardEntity.getBoardNumber();

            // boardImageList 는 반드시 배열([])형태로 받기로 했기 떄문에 길이가 0일 수는 있어도 null 값이 올 수 없다.
            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for (String image : boardImageList) {
                ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }

            imageRepository.saveAll(imageEntities);

        } catch (Exception exception) {
            log.error("게시물 업로드 오류", exception);
            return ResponseDto.databaseError();
        }
        return PostBoardResponseDto.success();
    }

    /**
     * 댓글 작성
     *
     * @param dto         사용자가 작성한 댓글 내용
     * @param boardNumber 작성할 댓글에 대한 게시물
     * @param email       댓글 작성자 이메일
     * @return PostCommentResponseDto
     */
    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber, String email) {
        try {
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return PostCommentResponseDto.noExistBoard();

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) return PostCommentResponseDto.noExistUser();

            CommentEntity commentEntity = new CommentEntity(dto, boardNumber, email);
            commentRepository.save(commentEntity);

            boardEntity.increaseCommentCount();
            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            log.error("댓글 등록 오류", exception);
            return ResponseDto.databaseError();
        }
        return PostCommentResponseDto.success();
    }

    /**
     * 좋아요
     *
     * @param boardNumber 게시물 번호
     * @param email       사용자 이메일
     * @return PutFavoriteResponseDto
     */
    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {
        try {
            // 존재하는 유저인지 확인
            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) return PutFavoriteResponseDto.noExistUser();

            // 존재하는 게시물인지 확인
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return PutFavoriteResponseDto.noExistBoard();

            /*
            1. 해당 게시물에 대한 사용자의 좋아요 상태를 확인한다.
            2. favoriteEntity 가 null 일 경우는 좋아요 상태가 없기 때문에 객체를 새로 생성해 좋아요가 눌린 상태로 바꾸어 저장해주고 좋아요 카운트를 증가시킨다.
            3. 반대로 favoriteEntity 가 null 이 아닐 경우는 좋아요가 눌린 상태이기 때문에 좋아요 취소를 위해 객체를 삭제하고 좋아요 카운트를 감소시킨다.
             */
            FavoriteEntity favoriteEntity = favoriteRepository.findByBoardNumberAndUserEmail(boardNumber, email);
            if (favoriteEntity == null) {
                favoriteEntity = new FavoriteEntity(email, boardNumber);
                favoriteRepository.save(favoriteEntity);
                boardEntity.increaseFavoriteCount();
            } else {
                favoriteRepository.delete(favoriteEntity);
                boardEntity.decreaseFavoriteCount();
            }
            boardRepository.save(boardEntity);
        } catch (Exception exception) {
            log.error("좋아요 오류", exception);
            return ResponseDto.databaseError();
        }
        return PutFavoriteResponseDto.success();
    }

    @Override
    public ResponseEntity<? super PatchBoardResponseDto> patchBoard(PatchBoardRequestDto dto, Integer boardNumber, String email) {
        try {
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return PatchBoardResponseDto.noExistBoard();

            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) return PatchBoardResponseDto.noExistUser();

            String writerEmail = boardEntity.getWriterEmail();
            boolean isWriter = writerEmail.equals(email);
            if (!isWriter) return PatchBoardResponseDto.noPermission();

            boardEntity.patchBoard(dto);
            boardRepository.save(boardEntity);

            imageRepository.deleteByBoardNumber(boardNumber);
            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for (String image : boardImageList) {
                ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }
            imageRepository.saveAll(imageEntities);
        } catch (Exception exception) {
            log.error("게시글 수정 오류", exception);
            return ResponseDto.databaseError();
        }
        return PatchBoardResponseDto.success();
    }

    /**
     * 조회수 증가
     *
     * @param boardNumber 게시물 번호
     * @return IncreaseViewCountResponseDto
     */
    @Override
    public ResponseEntity<? super IncreaseViewCountResponseDto> increaseViewCount(Integer boardNumber) {
        try {
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return IncreaseViewCountResponseDto.noExistBoard();

            // 조회수 증가
            boardEntity.increaseViewCount();
            boardRepository.save(boardEntity);
        } catch (Exception exception) {
            log.error("좋아요 증가 오류", exception);
            return ResponseDto.databaseError();
        }
        return IncreaseViewCountResponseDto.success();
    }

    /**
     * 게시물 삭제
     *
     * @param boardNumber 게시물 번호
     * @param email       작성자 이메일
     * @return DeleteBoardResponseDto
     */
    @Override
    public ResponseEntity<? super DeleteBoardResponseDto> deleteBoard(Integer boardNumber, String email) {
        try {
            boolean existedUser = userRepository.existsByEmail(email);
            if (!existedUser) return DeleteBoardResponseDto.noExistUser();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return DeleteBoardResponseDto.noExistBoard();

            String writerEmail = boardEntity.getWriterEmail();
            boolean isWriter = writerEmail.equals(email);
            if (!isWriter) return DeleteBoardResponseDto.noPermission();

            // 게시물 이미지 삭제
            imageRepository.deleteByBoardNumber(boardNumber);
            // 댓글 삭제
            commentRepository.deleteByBoardNumber(boardNumber);
            // 좋아요 삭제
            favoriteRepository.deleteByBoardNumber(boardNumber);
            // 게시물 삭제
            boardRepository.delete(boardEntity);
        } catch (Exception exception) {
            log.error("게시물 삭제 오류", exception);
            return ResponseDto.databaseError();
        }
        return DeleteBoardResponseDto.success();
    }
}
