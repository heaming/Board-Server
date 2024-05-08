package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;

import java.util.List;

public interface PostService {

    void addPost(String id, PostDTO postDTO);

    List<PostDTO> getMyPosts(int userId);

    void updatePost(PostDTO postDTO);

    void deletePost(int userId, int postId);

    void addComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deleteComment(int userId, int commentId);

    void addTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deleteTag(int userId, int tagId);


}
