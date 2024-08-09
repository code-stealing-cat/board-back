package com.jm.board_back.service.implement;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.dto.request.board.PostBoardRequestDto;
import com.jm.board_back.dto.response.ResponseDto;
import com.jm.board_back.dto.response.board.GetBoardResponseDto;
import com.jm.board_back.dto.response.board.PostBoardResponseDto;
import com.jm.board_back.entity.BoardEntity;
import com.jm.board_back.entity.ImageEntity;
import com.jm.board_back.repository.BoardRepository;
import com.jm.board_back.repository.ImageRepository;
import com.jm.board_back.repository.UserRepository;
import com.jm.board_back.repository.resultSet.GetBoardResultSet;
import com.jm.board_back.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@TimeTraceAnnotation
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {

        GetBoardResultSet resultSet = null;
        List<ImageEntity> imageEntities = new ArrayList<>();
        try {
            resultSet = boardRepository.getBoard(boardNumber);
            if (resultSet == null) return GetBoardResponseDto.notExistBoard();

            imageEntities = imageRepository.findByBoardNumber(boardNumber);

            // 조회수 증가
            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            boardEntity.increaseViewCount();
            boardRepository.save(boardEntity);
        } catch (Exception exception) {
            log.error("게시물 상세 가져오기 오류", exception);
            return ResponseDto.databaseError();
        }
        return GetBoardResponseDto.success(resultSet, imageEntities);
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
            if (!existedEmail) return PostBoardResponseDto.notExistUser();

            BoardEntity boardEntity = new BoardEntity(dto, email);
            boardRepository.save(boardEntity);

            // JPA 를 사용하여 엔티티를 저장하면 JPA 는 내부적으로 해당 엔터티를 데이터베이스에 저장하고 자동으로 생성된 키 값을 엔티티 객체에 설정해준다.
            int boardNumber = boardEntity.getBoardNumber();

            // boardImageList 는 반드시 배열([])형태로 받기 떄문에 길이가 0일 수는 있어도 null 값이 올 수 없다.
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
}
