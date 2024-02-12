package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface UserProfileMapper {
    public UserDTO getUserProfile(String userId);

    public int insertUserProfile(UserDTO userDTO);

    public int deleteUserProfile(String userId);

    public UserDTO getUserByIdAndPassword(String userId, String password);

    public int isDuplicatedId(String userId);

    public int updatePassword(UserDTO user);

    public int updateUserProfile(UserDTO user);
}
