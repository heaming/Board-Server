package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.TagDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {
    int insertTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deleteTag(int tagId);

    void insertPostTag(Integer tagId, Integer postId);
}
