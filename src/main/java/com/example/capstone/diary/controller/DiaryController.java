package com.example.capstone.diary.controller;

import com.example.capstone.diary.dto.DiaryDto;
import com.example.capstone.diary.dto.RequestCreateDiary;
import com.example.capstone.diary.dto.RequestUpdateDiary;
import com.example.capstone.diary.service.DiaryService;
import com.example.capstone.dto.ResponseCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryController {

    private final DiaryService diaryService;
    @GetMapping("")
    public ResponseEntity<List<DiaryDto>> findDiaryByDate(@RequestParam(name = "date", defaultValue = "#{T(java.time.LocalDate).now()}")
                                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestHeader("Authorization") String header){

        List<DiaryDto> result = diaryService.findDiaryByDate(date, header);
        log.info("{} 조회 성공", result.toString());

        return ResponseEntity
                .status(ResponseCode.DIARY_FIND_BY_DATE_SUCCESS.getHttpStatus())
                .body(result);
    }

    @PostMapping()
    public ResponseEntity<String> createDiary(@Valid @RequestBody RequestCreateDiary diary ,@RequestHeader("Authorization") String header){

        DiaryDto result = diaryService.createDiary(header, diary);
        log.info("{} 생성 성공",result.toString());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseCode.DIARY_CREATE_SUCCESS.getMessage());
    }

    @PatchMapping()
    public ResponseEntity<DiaryDto> updateDiary(@Valid @RequestBody RequestUpdateDiary diary, @RequestHeader("Authorization") String header){

        DiaryDto result = diaryService.updateDiary(header, diary);
        log.info("{} 수정 성공" ,result.toString());

        return ResponseEntity
                .status(ResponseCode.DIARY_FIND_BY_DATE_SUCCESS.getHttpStatus())
                .body(result);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<DiaryDto> deleteDiary(@PathVariable(name = "diaryId") Long id ,@RequestHeader("Authorization") String header){

        DiaryDto result = diaryService.deleteDiary(header,id);
        log.info("{} 삭제 성공", result.toString());

        return ResponseEntity
                .status(ResponseCode.DIARY_DELETE_SUCCESS.getHttpStatusCode())
                .body(result);
    }

}

