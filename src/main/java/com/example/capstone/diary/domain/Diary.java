package com.example.capstone.diary.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "diary")
public class Diary {

    @Id
    private Long id;

    private Integer kcal;

    private String food;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private MealType mealType;
}
