package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.PostDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {

    public int insertPost(PostDTO postDTO);

    public List<PostDTO> selectMyPosts(int userId);

    public void updatePosts(PostDTO postDTO);

    public void deletePost(int postId);
}
