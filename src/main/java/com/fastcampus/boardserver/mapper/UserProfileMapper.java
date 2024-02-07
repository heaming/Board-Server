package com.fastcampus.boardserver.mapper;

import com.fastcampus.boardserver.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface UserProfileMapper {
    public UserDTO getUserProfile(@Param("id") String id);

    int insertUserProfile(@Param("id") String userId,
                          @Param("password") String password,
                          String nickname,
                          boolean isAdmin,
                          boolean isWithDraw,
                          UserDTO.Status status
    );

    int deleteUserProfile(@Param("id") String userId);

    public UserDTO getUserByIdAndPassword(@Param("id") String userId,String password);

    int isDuplicatedId(@Param("id") String userId);

    public int updatePassword(UserDTO user);

    public int updateUserProfile(UserDTO user);
}
