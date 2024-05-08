package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import com.fastcampus.boardserver.service.impl.PostSearchServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchServiceImpl postSearchService;

    @PostMapping
    public PostSearchResponse search(@RequestBody PostSearchRequest request) {
        List<PostDTO> postDTOS = postSearchService.getPosts(request);
        return new PostSearchResponse(postDTOS);
    }

    @GetMapping
    public PostSearchResponse searchByTagName(String tagName) {
        List<PostDTO> postDTOS = postSearchService.getPostsByTagName(tagName);
        return new PostSearchResponse(postDTOS);
    }

    // response 객체
    @Getter
    @AllArgsConstructor
    private static class PostSearchResponse {
        private List<PostDTO> postDTOs;
    }

}
