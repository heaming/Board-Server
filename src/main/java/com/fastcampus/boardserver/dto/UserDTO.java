package com.fastcampus.boardserver.dto;

import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;


@Entity
@Table(name="USER")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserDTO {


    public enum Status {
        DEFAULT, ADMIN, DELETED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;

    @Column(name="USER_ID")
    private String userId;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="NICKNAME")
    private String nickname;

    @Column(name="IS_ADMIN")
    private boolean isAdmin;

    @Column(name="CREATE_TIME")
    private Date createTime;

    @Column(name="IS_WITHDRAW")
    private boolean isWithdraw;

    @Enumerated(EnumType.STRING)
    @Column(name="STATUS")
    private Status status;

    @Column(name="UPDATE_TIME")
    private Date updateTime;

    public static boolean hasNullDataBeforeRegister(UserDTO userDTO) {
        return userDTO.getUserId() == null || userDTO.getPassword() == null || userDTO.getNickname() == null;
    }
}
