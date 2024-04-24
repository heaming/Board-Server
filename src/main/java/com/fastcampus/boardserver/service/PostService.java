package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.PostDTO;

import java.util.List;

public interface PostService {

    public void addPost(String id, PostDTO postDTO);

    public List<PostDTO> getMyPosts(int userId);

    public void updatePost(PostDTO postDTO);

    public void deletePost(int userId, int postId);
}
