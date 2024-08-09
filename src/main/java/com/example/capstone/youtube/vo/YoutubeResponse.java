package com.example.capstone.youtube.vo;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class YoutubeResponse {
    private String title;
    private String url;
}
