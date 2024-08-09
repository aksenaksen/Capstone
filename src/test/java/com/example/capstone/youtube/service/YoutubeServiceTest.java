package com.example.capstone.youtube.service;

import com.example.capstone.youtube.vo.YoutubeResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@Slf4j
class YoutubeServiceTest {
    @InjectMocks
    private YoutubeService youtubeService;

    @BeforeEach
    public void setUp(){
        ReflectionTestUtils.setField(youtubeService,"apiKey","12345");
    }

    @Test
    @DisplayName("youtube 조회시 성공적으로 url을 받아온다.")
    void searchKeyword() throws IOException {
        String keyword = "라면 레시피";
        YoutubeResponse response = youtubeService.searchVideo(keyword);

        log.info(response.toString());

        assertThat(response)
                .isInstanceOf(YoutubeResponse.class);

    }
}
