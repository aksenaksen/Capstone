package com.example.capstone.diary.service;

import com.example.capstone.diary.dto.DiaryDto;
import com.example.capstone.diary.dto.RequestCreateDiary;
import com.example.capstone.diary.dto.RequestUpdateDiary;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DiaryService {
    public DiaryDto createDiary(String header,RequestCreateDiary diary) {
        return null;
    }

    public List<DiaryDto> findDiaryByDate(LocalDate date, String header) {
        return null;
    }

    public DiaryDto updateDiary(String header, RequestUpdateDiary diary) {
        return null;
    }

    public DiaryDto deleteDiary(String header,Long id) {
        return null;
    }
}
