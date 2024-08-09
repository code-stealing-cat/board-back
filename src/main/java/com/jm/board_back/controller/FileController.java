package com.jm.board_back.controller;

import com.jm.board_back.customAnnotation.TimeTraceAnnotation;
import com.jm.board_back.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@TimeTraceAnnotation
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    /**
     * 파일 업로드
     *
     * @param file 사용자가 업로드한 파일
     * @return 파일 url + 파일 이름
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        return fileService.upload(file);
    }

    /**
     * 파일 불러오기
     *
     * @param fileName 사용자가 선택한 파일 이름
     * @return 파일 이미지
     */
    @GetMapping(value = "{fileName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Resource getImage(@PathVariable("fileName") String fileName) {
        return fileService.getImage(fileName);
    }
}
