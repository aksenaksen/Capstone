package com.example.capstone.youtube.service;

import com.example.capstone.youtube.vo.YoutubeResponse;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class YoutubeService {

    @Value("${api.key}")
    private String apiKey;
    private final JsonFactory jsonFactory = new JacksonFactory();
    private final YouTube youtube;

    public YoutubeService() {
        this.youtube = new YouTube.Builder(
                new com.google.api.client.http.javanet.NetHttpTransport(),
                jsonFactory,
                httpRequest -> {}
        ).build();
    }

    public YoutubeResponse searchVideo(String keyword) throws IOException {
        YouTube.Search.List search = youtube.search().list(Collections.singletonList("id,snippet"));
        search.setKey(apiKey);
        search.setQ(keyword + " 레시피");

        SearchListResponse searchListResponse = search.execute();
        List<SearchResult> searchResultList = searchListResponse.getItems();

        if (searchResultList.isEmpty()) {
            throw new IOException("No results found.");
        }

        SearchResult searchResult = searchResultList.get(0);
        String videoId = searchResult.getId().getVideoId();
        String videoTitle = searchResult.getSnippet().getTitle();

        return YoutubeResponse.builder()
                .url("https://www.youtube.com/watch?v=" + videoId)
                .title(videoTitle)
                .build();
    }
}
