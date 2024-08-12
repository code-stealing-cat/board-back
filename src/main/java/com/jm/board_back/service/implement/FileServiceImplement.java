package com.jm.board_back.service.implement;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@TimeTraceAnnotation
public class FileServiceImplement implements FileService {

    @Value("${file.path}")
    private String filePath;

    @Value("${file.url}")
    private String fileUrl;

    /**
     * 파일 업로드
     *
     * @param file 사용자가 업로드한 파일
     * @return 파일 url + 파일 이름
     */
    @Override
    public String upload(MultipartFile file) {
        if (file.isEmpty()) return null;
        String originalFileName = file.getOriginalFilename();
        String extension = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf("."));
        String uuid = UUID.randomUUID().toString();
        String saveFileName = uuid + extension;
        String savePath = filePath + saveFileName;

        File dir = new File(filePath);
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                log.info("디렉토리 생성: {}", filePath);
            } else {
                log.info("디렉토리 생성 실패: {}", filePath);
                return null;
            }
        }

        try {
            file.transferTo(new File(savePath));
        } catch (Exception exception) {
            log.error("파일 업로드 오류", exception);
            return null;
        }

        return fileUrl + saveFileName;
    }

    /**
     * 파일 불러오기
     *
     * @param fileName 사용자가 선택한 파일 이름
     * @return 파일 이미지
     */
    @Override
    public Resource getImage(String fileName) {
        Resource resource = null;
        try {
            resource = new UrlResource("file:" + filePath + fileName);
        } catch (Exception exception) {
            log.error("이미지 불러오기 실패", exception);
            return null;
        }
        return resource;
    }
}
