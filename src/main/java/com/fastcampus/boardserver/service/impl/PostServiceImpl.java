package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.mapper.CommentMapper;
import com.fastcampus.boardserver.mapper.PostMapper;
import com.fastcampus.boardserver.mapper.TagMapper;
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

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TagMapper tagMapper;

    @Override
    public void addPost(String userId, PostDTO postDTO) {
        UserDTO userDTO = userProfileMapper.getUserProfile(userId);
        postDTO.setUserId(userDTO.getId());
        postDTO.setCreateTime(new Date());

        if(userDTO != null) {
            postMapper.insertPost(postDTO);
            Integer postId = postDTO.getId();
            for(int i=0; i<postDTO.getTagDTOS().size(); i++) {
                TagDTO tagDTO = postDTO.getTagDTOS().get(i);
                tagMapper.insertTag(tagDTO);

                Integer tagId = tagDTO.getId();
                tagMapper.insertPostTag(tagId, postId);
            }
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
            postMapper.updatePost(postDTO);
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

    @Override
    public void addComment(CommentDTO commentDTO) {
        if(commentDTO.getPostId() != 0) {
            commentMapper.insertComment(commentDTO);
        } else {
            log.error("addComment {}", commentDTO);
            throw new RuntimeException("addComment" + commentDTO);
        }
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if(commentDTO != null) {
            commentMapper.updateComment(commentDTO);
        } else {
            log.error("updateComment error!, {}", commentDTO);
            throw new RuntimeException("updateComment is null" + commentDTO);
        }
    }

    @Override
    public void deleteComment(int userId, int commentId) {
        if(userId != 0 && commentId != 0) {
            commentMapper.deleteComment(commentId);
        } else {
            log.error("deleteComment error {}", commentId);
            throw new RuntimeException("deleteComment : " + commentId);
        }
    }

    @Override
    public void addTag(TagDTO tagDTO) {
        if(tagDTO != null) {
            tagMapper.insertTag(tagDTO);
        } else {
            log.error("addTag() : {}", tagDTO);
            throw new RuntimeException("addTag() : "+tagDTO);
        }
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if(tagDTO != null) {
            tagMapper.updateTag(tagDTO);
        } else {
            log.error("updateTag() : {}", tagDTO);
            throw new RuntimeException("updateTag() : "+tagDTO);
        }
    }

    @Override
    public void deleteTag(int userId, int tagId) {
        if(userId != 0 && tagId != 0) {
            tagMapper.deleteTag(tagId);
        } else {
            log.error("deleteTag error {}", tagId);
            throw new RuntimeException("deleteTag : " + tagId);
        }
    }
}
