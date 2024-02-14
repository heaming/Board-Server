package com.fastcampus.boardserver.service.impl;

import com.fastcampus.boardserver.dto.UserDTO;
import com.fastcampus.boardserver.exception.DuplicateIdException;
import com.fastcampus.boardserver.mapper.UserProfileMapper;
import com.fastcampus.boardserver.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.fastcampus.boardserver.utils.SHA256Util.encryptSHA256;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    @Autowired
    private UserProfileMapper userProfileMapper;

    @Override
    public void register(UserDTO userProfile) {
        boolean duplIdResult = isDuplicatedId(userProfile.getUserId());

        if(duplIdResult) {
            throw new DuplicateIdException("중복된 ID입니다.");
        }

        userProfile.setPassword(encryptSHA256(userProfile.getPassword()));

        int insertCount = userProfileMapper.insertUserProfile(userProfile);

        if(insertCount != 1) {
            log.error("insertMapper ERROR! : {}", userProfile);
            throw new RuntimeException(
                    "insertUser ERROR! register() 을 확인해주세요\n" + "Params : "+userProfile
            );
        }
    }

    @Override
    public UserDTO login(String userId, String password) {
        String cryptPassword = encryptSHA256(password);
        UserDTO userDTO = userProfileMapper.getUserByIdAndPassword(userId, cryptPassword);

        return userDTO;
    }

    @Override
    public boolean isDuplicatedId(String userId) {
        if(userProfileMapper.isDuplicatedId(userId) > 0) return true;

        return false;
    }

    @Override
    public UserDTO getUserInfo(String userId) {
        return userProfileMapper.getUserProfile(userId);
    }

    @Override
    public void updatePassword(String userId, String beforePassword, String afterPassword) {
        String cryptPassword = encryptSHA256(beforePassword);
        log.info("userID :: {}", userId);
        UserDTO userDTO = userProfileMapper.getUserByIdAndPassword(userId, cryptPassword);

        log.info("get userDTO :: {}", userDTO);

        if(userDTO != null) {
            userDTO.setPassword(encryptSHA256(afterPassword));
            log.info(encryptSHA256(afterPassword));
            log.info("after userDTO :: {}", userDTO);
            int insertCount = userProfileMapper.updatePassword(userDTO);
            log.info("changed password : {}", userProfileMapper.getUserByIdAndPassword(userId, encryptSHA256(afterPassword)));
        } else {
            log.error("updatePassword Error! : {}", userDTO);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
    }

    @Override
    public void deleteId(String userId, String password) {
        String cryptPassword = encryptSHA256(password);
        UserDTO userDTO = userProfileMapper.getUserByIdAndPassword(userId, cryptPassword);

        if(userDTO != null) {
            int deleteCount = userProfileMapper.deleteUserProfile(userId);
        } else {
            log.error("deleteId Error! : {}", userDTO);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

    }

    @Override
    public void updateUserProfile(String userId, UserDTO userProfile) {
        String cryptPassword = encryptSHA256(userProfile.getPassword());
        UserDTO userDTO = userProfileMapper.getUserByIdAndPassword(userId, cryptPassword);
        log.error("userDTO :: {}", userProfile);

        if(userDTO != null) {
            userDTO.setNickname(userProfile.getNickname());
            int updateCount = userProfileMapper.updateUserProfile(userDTO);
        } else {
            log.error("updateUserProfile Error! : {}", userDTO);
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

    }
}
