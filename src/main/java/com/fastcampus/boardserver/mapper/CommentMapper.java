package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    int insertComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deleteComment(int commentId);
}
