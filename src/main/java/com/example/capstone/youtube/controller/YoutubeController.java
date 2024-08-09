package com.example.capstone.youtube.controller;


import com.example.capstone.youtube.vo.YoutubeResponse;
import com.example.capstone.youtube.service.YoutubeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "YOUTUBE", description = "YOUTUBE API")
@RestController
@RequestMapping("/api/youtube")
@Validated
@RequiredArgsConstructor
@Slf4j
public class YoutubeController {

    private final YoutubeService youtubeService;

    @GetMapping("/search")
    public ResponseEntity<YoutubeResponse> searchYoutube(@RequestParam @Pattern(regexp = "^[가-힣]+$", message = "레시피명은 한글로만 입력 가능합니다.") String keyword) throws IOException {

        YoutubeResponse response = youtubeService.searchVideo(keyword);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleNotMatchedKeywordFormat(ConstraintViolationException e){

        log.error("error -> "+e.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }
}
