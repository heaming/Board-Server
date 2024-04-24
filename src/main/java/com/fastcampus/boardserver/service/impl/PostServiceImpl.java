package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public void addPost(String userId, PostDTO postDTO) {
        UserDTO userDTO = userProfileMapper.getUserProfile(userId);
        postDTO.setUserId(userDTO.getId());
        postDTO.setCreateTime(new Date());

        if(userDTO != null) {
            postMapper.insertPost(postDTO);
        } else {
            log.error("addPost() ERROR! : {}", postDTO);
            throw new RuntimeException("addPost() ERROR! 게시글 등록 메서드를 확인하세요" + postDTO);
        }
    }

    @Override
    public List<PostDTO> getMyPosts(int userId) {
        List<PostDTO> posts = postMapper.selectMyPosts(userId);
        return posts;
    }

    @Override
    public void updatePost(PostDTO postDTO) {
        if(postDTO != null && postDTO.getId() != 0 && postDTO.getUserId() != 0) {
            postMapper.updatePosts(postDTO);
        } else {
            log.error("updatePost() ERROR! : {}", postDTO);
            throw new RuntimeException("updatePost() ERROR! 게시글 수정 메서드를 확인하세요" + postDTO);
        }
    }

    @Override
    public void deletePost(int userId, int postId) {
        if(userId != 0 && postId != 0) {
            postMapper.deletePost(postId);
        } else {
            log.error("deletePost() ERROR! : {}", postId);
            throw new RuntimeException("deletePost() ERROR! 게시글 삭제 메서드를 확인하세요" + postId);
        }
    }
}
