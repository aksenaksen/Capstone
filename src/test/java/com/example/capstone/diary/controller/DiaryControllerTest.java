package com.example.capstone.diary.controller;


import com.example.capstone.diary.domain.MealType;
import com.example.capstone.diary.dto.DiaryDto;
import com.example.capstone.diary.dto.RequestCreateDiary;
import com.example.capstone.diary.dto.RequestUpdateDiary;
import com.example.capstone.diary.exception.UserNotFoundException;
import com.example.capstone.diary.service.DiaryService;
import com.example.capstone.dto.ErrorResponseDto;
import com.example.capstone.dto.ResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DiaryController.class)
@ActiveProfiles("test")
class DiaryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DiaryService diaryService;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @WithMockUser
    @DisplayName("성공적으로 일지를 생성한다 : 201 CREATED")
    void successCreateDiary() throws Exception {
//     given
        RequestCreateDiary diary = RequestCreateDiary.builder()
                .food("라면")
                .kcal(450)
                .date(LocalDate.now())
                .mealType(MealType.DINNER)
                .build();

        given(diaryService.createDiary(any(), any())).willReturn(DiaryDto.of(diary));
//     when, then
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/api/diary")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(diary))
                        .header("Authorization","Bearer asdf2123")
        )
                .andExpect(status().is(HttpStatus.CREATED.value()))
                .andExpect(content().string(ResponseCode.DIARY_CREATE_SUCCESS.getMessage()))
                .andDo(print());
        verify(diaryService,times(1)).createDiary(any(),any());

    }
    @Test
    @WithMockUser
    @DisplayName("사용자 일지 조회시 성공적으로 일지를 가져온다.: 200")
    void successFindByDate() throws Exception {
//        given
        List<DiaryDto> dtoList = new ArrayList<>(Collections.nCopies(2, DiaryDto.builder()
                .food("라면")
                .kcal(450)
                .date(LocalDate.now())
                .mealType(MealType.DINNER)
                .build()));


        given(diaryService.findDiaryByDate(any(),any())).willReturn(dtoList);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/diary")
                        .param("date",LocalDate.now().toString())
                        .header("Authorization", "Bearer asdf1234")
        )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dtoList)))
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("성공적으로 일지를 수정한다 : 200")
    void successUpdateDiary() throws Exception {

        DiaryDto diary = DiaryDto.builder()
                .food("라면")
                .kcal(460)
                .date(LocalDate.now())
                .mealType(MealType.DINNER)
                .build();

        RequestUpdateDiary requestDiary = new RequestUpdateDiary();
        requestDiary.setKcal(460);
        requestDiary.setMealType(MealType.LUNCH);
        requestDiary.setId(1L);
        requestDiary.setFood("산");
        requestDiary.setDate(LocalDate.now());

        given(diaryService.updateDiary(any(),any())).willReturn(diary);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/diary")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(requestDiary))
                                .header("Authorization", "Bearer asdf1234")
                )
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(diary)))
                .andDo(print());
    }

    @Test
    @WithMockUser
    @DisplayName("성공적으로 일지를 삭제한다 : 200")
    void successDeleteDiary() throws Exception {
        DiaryDto diary = DiaryDto.builder()
                .id(1L)
                .food("라면")
                .kcal(450)
                .date(LocalDate.now())
                .mealType(MealType.DINNER)
                .build();

        given(diaryService.deleteDiary(any(),any())).willReturn(diary);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/api/diary/1")
                                .with(csrf())
                                .header("Authorization", "Bearer asdf1234")
                )
                .andExpect(status().is(HttpStatus.NO_CONTENT.value()))
                .andExpect(content().json(objectMapper.writeValueAsString(diary)))
                .andDo(print());

    }

    @Test
    @WithMockUser
    @DisplayName("일지 조회,생성, 삭제시 유저를 찾을 수 없음. UserNotFoundException : 400")
    void failCreateDiaryWithInvalidUser() throws Exception {

        // 정확한 예외 인스턴스를 던지도록 설정
        given(diaryService.findDiaryByDate(any(), any())).willThrow(new UserNotFoundException());

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/diary")
                                .param("date", LocalDate.now().toString())
                                .header("Authorization", "Bearer asdf1234")
                )
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(
                        result -> {
                            // 응답을 JSON으로 파싱하여 ErrorResponseDto로 변환
                            ErrorResponseDto errorResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), ErrorResponseDto.class);
                            assertThat(errorResponseDto.getCode()).isEqualTo(ResponseCode.USER_NOT_FOUND.getHttpStatusCode());
                            assertThat(errorResponseDto.getMessage()).isEqualTo(ResponseCode.USER_NOT_FOUND.getMessage());
                        }
                )
                .andDo(print());
    }

    @ParameterizedTest
    @WithMockUser
    @ValueSource(strings = {"1234", "20191104","1234","asdf"})
    @DisplayName("일지조회시 잘못된 날짜 형식으로 조회했을때 : 400")
    void failModifyDiaryWithInvalidUser(String date) throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/diary")
                                .param("date", date)
                                .header("Authorization", "Bearer asdf1234")
                )
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(
                        result -> {
                            // 응답을 JSON으로 파싱하여 ErrorResponseDto로 변환
                            ErrorResponseDto errorResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), ErrorResponseDto.class);
                            assertThat(errorResponseDto.getCode()).isEqualTo(ResponseCode.DIARY_PARAM_NOT_ALLOWED_FORMAT.getCode());
                            assertThat(errorResponseDto.getMessage()).isEqualTo(ResponseCode.DIARY_PARAM_NOT_ALLOWED_FORMAT.getMessage());
                        }
                )
                .andDo(print());


    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"food", "SELECT *" , "라면 *!@#", " "})
    @WithMockUser
    @DisplayName("일지생성시 허용되지 않은 값을 넣어서 보내면 예외 발생 : 400")
    void failCreateDiaryWithInvalidBody(String food) throws Exception {

        RequestCreateDiary diary = RequestCreateDiary.builder()
                .food(food)
                .kcal(45000)
                .date(LocalDate.now())
                .mealType(MealType.DINNER)
                .build();

//     when, then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/diary")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(diary))
                                .header("Authorization","Bearer asdf2123")
                )
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(
                        result -> {
                            // 응답을 JSON으로 파싱하여 ErrorResponseDto로 변환
                            ErrorResponseDto errorResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), ErrorResponseDto.class);
                            assertThat(errorResponseDto.getCode()).isEqualTo(ResponseCode.DIARY_REQUEST_NOT_ALLOWED_FORMAT.getCode());
                        }
                )
                .andDo(print());
        verify(diaryService,times(0)).createDiary(any(),any());
    }



    @ParameterizedTest
    @ValueSource(strings = {"-5000","-10", "40000", "8000000"})
    @WithMockUser
    @DisplayName("일지생성시 허용되지 않은 값을 넣어서 보내면 예외 발생 : 400")
    void failCreateDiaryWithInvalidBody2(String kcal) throws Exception {



        RequestCreateDiary diary = RequestCreateDiary.builder()
                .food("라면")
                .kcal(Integer.parseInt(kcal))
                .date(LocalDate.now())
                .mealType(MealType.DINNER)
                .build();

//     when, then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/diary")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(diary))
                                .header("Authorization","Bearer asdf2123")
                )
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(
                        result -> {
                            // 응답을 JSON으로 파싱하여 ErrorResponseDto로 변환
                            ErrorResponseDto errorResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), ErrorResponseDto.class);
                            assertThat(errorResponseDto.getCode()).isEqualTo(ResponseCode.DIARY_REQUEST_NOT_ALLOWED_FORMAT.getCode());
                        }
                )
                .andDo(print());
        verify(diaryService,times(0)).createDiary(any(),any());
    }


    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"food", "SELECT *" , "라면 *!@#", " "})
    @WithMockUser
    @DisplayName("일지 수정시 허용되지 않은 값을 넣어서 보내면 예외 발생 : 400")
    void failCreateDiaryWithInvalidBody3(String food) throws Exception {

        RequestUpdateDiary diary = RequestUpdateDiary.builder()
                .food(food)
                .kcal(null)
                .date(LocalDate.now())
                .mealType(MealType.DINNER)
                .build();

//     when, then
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch("/api/diary")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(diary))
                                .header("Authorization", "Bearer asdf1234")
                )
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(
                        result -> {
                            // 응답을 JSON으로 파싱하여 ErrorResponseDto로 변환
                            ErrorResponseDto errorResponseDto = objectMapper.readValue(result.getResponse().getContentAsString(StandardCharsets.UTF_8), ErrorResponseDto.class);
                            assertThat(errorResponseDto.getCode()).isEqualTo(ResponseCode.DIARY_REQUEST_NOT_ALLOWED_FORMAT.getCode());
                        }
                )
                .andDo(print());
        verify(diaryService,times(0)).createDiary(any(),any());
    }




}
