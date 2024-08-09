package com.example.capstone.youtube.controller;

import com.example.capstone.youtube.vo.YoutubeResponse;
import com.example.capstone.youtube.service.YoutubeService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@WebMvcTest(YoutubeController.class)
class YoutubeApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private YoutubeService youtubeService;

    @Test
    @WithMockUser
    @DisplayName("정상적인 파라미터로 YOUTUBEAPI 요청을 보낸다 :200")
    void successGetSearch() throws Exception {
//        when
        String param = "라면";
//        given then
        given(youtubeService.searchVideo(any())).willReturn(YoutubeResponse.builder()
                .title("test")
                .url("youtube.com/라면")
                .build());
//
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/youtube/search")
                        .param("keyword",param)

        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("youtube.com/라면"))
                .andDo(print());
        verify(youtubeService).searchVideo(param);
    }

    @ParameterizedTest
    @WithMockUser
    @ValueSource(strings = {"SELECT ", "라면*", "", " "})
    @DisplayName("허용되지않은 파라미터로 keyword가 들어온 경우 예외를 발생시킨다")
    void failGetSearch(String param) throws Exception {
//
//
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/youtube/search")
                        .param("keyword",param)
        )
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.BAD_REQUEST.value()));
        verify(youtubeService, times(0)).searchVideo(param);
    }


}
