package com.example.capstone.diary.dto;

import com.example.capstone.diary.domain.MealType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class RequestCreateDiary {

    @Min(value = 0, message = "칼로리는 0 이상이어야 합니다.")
    @Max(value = 10000, message = "칼로리는 10,000 이하이어야 합니다.")
    @NotNull(message = "칼로리는 필수 입력 항목입니다.")
    private Integer kcal;

    @Pattern(regexp = "^[가-힣,]{1,20}$", message = "음식명은 한글과 쉼표만 허용되며, 1자에서 20자 사이여야 합니다.")
    private String food;

    @NotNull(message = "날짜는 필수 입력 항목입니다.")
    private LocalDate date;

    @NotNull(message = "식사 유형은 필수 입력 항목입니다.")
    private MealType mealType;

}
