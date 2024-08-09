package com.jm.board_back.service.implement;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.dto.response.ResponseDto;
import com.jm.board_back.dto.response.user.GetSignInUserResponseDto;
import com.jm.board_back.entity.UserEntity;
import com.jm.board_back.repository.UserRepository;
import com.jm.board_back.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@TimeTraceAnnotation
@RequiredArgsConstructor
public class UserServiceImplement implements UserService {

    private final UserRepository userRepository;

    /**
     * 사용자가 로그인한 또는 로그아웃 후 User 객체를 갱신하기
     *
     * @param email 사용자 이메일
     * @return User 객체
     */
    @Override
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email) {
        UserEntity userEntity = null;
        try {
            userEntity = userRepository.findByEmail(email);
            if (userEntity == null) return GetSignInUserResponseDto.notExistUser();

        } catch (Exception exception) {
            log.error("사용자 인증 응답 오류", exception);
            return ResponseDto.databaseError();
        }
        return GetSignInUserResponseDto.success(userEntity);
    }
}
