package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.request.PostSearchRequest;
import com.fastcampus.boardserver.mapper.PostSearchMapper;
import com.fastcampus.boardserver.service.PostSearchService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class PostSearchServiceImpl implements PostSearchService {

    @Autowired
    private PostSearchMapper mapper;

    @Async
    @Cacheable(value="getPosts", key="'getPosts' + #request.getName() + #request.getCategoryId()")
    @Override
    public List<PostDTO> getPosts(PostSearchRequest request) {
        List<PostDTO> postDTOS = null;

        try {
            postDTOS = mapper.selectPosts(request);
        } catch (RuntimeException e) {
            log.error("getPosts() 실패:",e.getMessage());
        }

        return postDTOS;
    }

    @Async
    @Override
    public List<PostDTO> getPostsByTagName(String tagName) {
        List<PostDTO> postDTOS = null;

        try {
            postDTOS = mapper.selectPostsByTagName(tagName);
        } catch (RuntimeException e) {
            log.error("getPostsByTagName() 실패:",e.getMessage());
        }

        return postDTOS;
    }
}
