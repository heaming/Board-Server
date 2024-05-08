package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.CommentDTO;
import com.fastcampus.boardserver.dto.PostDTO;
import com.fastcampus.boardserver.dto.TagDTO;
import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.response.CommonResponse;
import com.fastcampus.boardserver.service.impl.PostServiceImpl;
import com.fastcampus.boardserver.service.impl.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
@Log4j2
public class PostController {

    private final PostServiceImpl postService;
    private final UserServiceImpl userService;

    public PostController(UserServiceImpl userService, PostServiceImpl postService) {
        this.userService = userService;
        this.postService = postService;
    }

    // response -> inner class로 캡슐화
    @Getter
    @AllArgsConstructor
    private static class PostResponse {
        private List<PostDTO> postDTOS;
    }

    // request -> inner class
    @Setter
    @Getter
    private static class PostRequest {
        private String name;
        private String contents;
        private int views;
        private int categoryId;
        private int userId;
        private int fileId;
        private Date updateTime;
    }

    @Getter
    @Setter
    private static class PostDeleteRequest {
        private int id;
        private int userId;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type= LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> addPost(String userId,
                                                           @RequestBody PostDTO postDTO) {
        postService.addPost(userId, postDTO);
        CommonResponse response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "addPost()" , postDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-posts")
    @LoginCheck(type= LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> getMyPosts(String userId) {
        UserDTO userDTO = userService.getUserInfo(userId);
        List<PostDTO> posts = postService.getMyPosts(userDTO.getId());
        CommonResponse response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "addPost()" , posts);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostResponse>> updatePosts(String userId,
                                                                    @PathVariable int postId,
                                                                    @RequestBody PostRequest request) {
        UserDTO userDTO = userService.getUserInfo(userId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(request.getName())
                .contents(request.getContents())
                .views(request.getViews())
                .categoryId(request.getCategoryId())
                .userId(userDTO.getId())
                .fileId(request.getFileId())
                .updateTime(new Date())
                .build();

        postService.updatePost(postDTO);
        CommonResponse response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePost()", request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deletePost(String userId,
                                                                         @PathVariable int postId,
                                                                         @RequestBody PostDeleteRequest request) {
        UserDTO userDTO = userService.getUserInfo(userId);
        postService.deletePost(userDTO.getId(), postId);
        CommonResponse response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deletePost()", request);

        return ResponseEntity.ok(response);
    }

    /* comments */
    @PostMapping("comments")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> addPostComment(String userId,
                                                                     @RequestBody CommentDTO commentDTO){

        postService.addComment(commentDTO);
        CommonResponse response = new CommonResponse(HttpStatus.OK, "SUCCESS", "addPostComment", commentDTO);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> updatePostComment(String userId,
                                                                     @PathVariable(name="commentId") int commentId,
                                                                     @RequestBody CommentDTO commentDTO){

        UserDTO userDTO = userService.getUserInfo(userId);

        if(userDTO != null) {
            postService.updateComment(commentDTO);
        }

        CommonResponse response = new CommonResponse(HttpStatus.OK, "SUCCESS", "updatePostComment", commentDTO);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> deletePostComment(String userId,
                                                                     @PathVariable(name="commentId") int commentId){

        UserDTO userDTO = userService.getUserInfo(userId);

        if(userDTO != null) {
            postService.deleteComment(userDTO.getId(), commentId);
        }

        CommonResponse response = new CommonResponse(HttpStatus.OK, "SUCCESS", "deletePostComment", commentId);

        return ResponseEntity.ok(response);
    }

    /* tag */
    @PostMapping("tags")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> addPostTag(String userId,
                                                         @RequestBody TagDTO tagDTO) {
        postService.addTag(tagDTO);
        CommonResponse response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "addPostTag",tagDTO);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> updatePostTag(String userId,
                                                @PathVariable("tagId") int tagId,
                                                @RequestBody TagDTO tagDTO) {

        UserDTO userDTO = userService.getUserInfo(userId);

        if(userDTO != null) {
            postService.updateTag(tagDTO);
        }

        CommonResponse response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePostTag", tagDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> deletePostTag(String userId,
                                                                @PathVariable(name="tagId") int tagId) {

        UserDTO userDTO = userService.getUserInfo(userId);

        if(userDTO != null) {
            postService.deleteTag(userDTO.getId(), tagId);
        }

        CommonResponse response = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deletePostTag", tagId);
        return ResponseEntity.ok(response);
    }
}
