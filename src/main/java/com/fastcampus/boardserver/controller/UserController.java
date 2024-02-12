package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.dto.request.UserDeleteId;
import com.fastcampus.boardserver.dto.request.UserLoginRequest;
import com.fastcampus.boardserver.dto.request.UserUpdatePasswordRequest;
import com.fastcampus.boardserver.dto.response.LoginResponse;
import com.fastcampus.boardserver.dto.response.UserInfoResponse;
import com.fastcampus.boardserver.service.impl.UserServiceImpl;
import com.fastcampus.boardserver.utils.SessionUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
@Log4j2
public class UserController {

    private final UserServiceImpl userService;
    private static LoginResponse loginResponse;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /*
     * @Title: 회원가입
     * @Status : SUCCESS
     */
    @PostMapping("/sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDTO userDTO) {
        if(userDTO.hasNullDataBeforeRegister(userDTO)) {
            throw new RuntimeException("회원가입 정보를 확인해주세요");
        }

        userService.register(userDTO);
    }

    /*
     * @Title: 로그인
     * @Status : SUCCESS
     */
    @PostMapping("/sign-in")
    public HttpStatus login(@RequestBody UserLoginRequest userLoginRequest,
                            HttpSession session
                            ) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = userLoginRequest.getUserId();
        String password = userLoginRequest.getPassword();
        UserDTO userInfo = userService.login(id, password);

        if (userInfo == null) {
            return HttpStatus.NOT_FOUND;
        } else if (userInfo != null) {
            loginResponse = LoginResponse.success(userInfo);

            if(userInfo.getStatus() == UserDTO.Status.ADMIN) {
                SessionUtil.setLoginAdminId(session, id);
            } else {
                SessionUtil.setLoginMemberId(session, id);
            }

            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } else {
            throw new RuntimeException("Login Error! :: 유저 정보가 없거나 사용할 수 없는 유저입니다.");
        }

        return HttpStatus.OK;
    }

    /*
     * @Title: 회원정보 조회
     * @Status : SUCCESS
     */
    @GetMapping("/my-info")
    public UserInfoResponse userInfo(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);

        if (id == null) {
            id = SessionUtil.getLoginAdminId(session);
        }

        UserDTO userInfo = userService.getUserInfo(id);

        return new UserInfoResponse(userInfo);
    }

    /*
     * @Title: 로그아웃
     * @Status : SUCCESS
     */
    @PutMapping("/logout")
    public void logout(String userId, HttpSession session) {
        SessionUtil.clear(session);
    }

    /*
     * @Title: 비밀번호 변경
     * @Status : SUCCESS
     */
    @PatchMapping("/password")
    public ResponseEntity<LoginResponse> updateUserPassword(
            @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
            HttpSession session
            ) {
        ResponseEntity<LoginResponse> responseEntity = null;

        String id = SessionUtil.getLoginMemberId(session);
        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        log.info("beforePassword :: {}", beforePassword);
        log.info("afterPassword :: {}", afterPassword);
        try {
            userService.updatePassword(id, beforePassword, afterPassword);

            ResponseEntity.ok(new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK));
        } catch (RuntimeException e) {
            log.error("updatePassword() FAIL :: {} {}", userUpdatePasswordRequest, id);
            responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    /*
     * @Title: 회원정보 변경
     * @Status : SUCCESS
     */
    @PostMapping("/profile")
    public ResponseEntity<LoginResponse> updateUserProfile(@RequestBody UserDTO userProfile,
                                                              HttpSession session
                                                              ) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = SessionUtil.getLoginMemberId(session);

        try {
            userService.updateUserProfile(id, userProfile);
            responseEntity = new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("updateUserProfile() FAIL :: {}", e);
            responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    /*
     * @Title: 회원 삭제
     * @Status : SUCCESS
     */
    @DeleteMapping("")
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId,
                                                  HttpSession session
                                                  ) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(id, userDeleteId.getPassword());
            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.error("deleteId() FAIL :: {}",e);
            responseEntity = new ResponseEntity<LoginResponse>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }
}
