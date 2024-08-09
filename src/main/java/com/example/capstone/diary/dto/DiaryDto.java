package com.example.capstone.diary.dto;

import com.example.capstone.diary.domain.MealType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class DiaryDto {

    private Long id;

    private Integer kcal;

    private String food;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private MealType mealType;

    public static DiaryDto of(RequestCreateDiary requestCreateDiary){
        return DiaryDto.builder()
                .food(requestCreateDiary.getFood())
                .date(requestCreateDiary.getDate())
                .kcal(requestCreateDiary.getKcal())
                .mealType(requestCreateDiary.getMealType())
                .build();
    }

}
