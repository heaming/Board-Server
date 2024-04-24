package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.aop.LoginCheck;
import com.fastcampus.boardserver.dto.PostDTO;
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
                .userId(request.getUserId())
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
}
