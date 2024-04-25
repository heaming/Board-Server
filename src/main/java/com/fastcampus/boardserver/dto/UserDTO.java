package com.fastcampus.boardserver.dto;

import jakarta.persistence.*;
import lombok.*;

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

    @Column(name="user_id")
    private String userId;

    private String password;

    private String nickname;

    @Column(name="IS_ADMIN", columnDefinition = "TINYINT(1)")
    private boolean isAdmin;

    private Date createTime;

    @Column(name="IS_WITHDRAW", columnDefinition = "TINYINT(1)")
    private boolean isWithdraw;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Date updateTime;

    public static boolean hasNullDataBeforeRegister(UserDTO userDTO) {
        return userDTO.getId() == 0 || userDTO.getPassword() == null || userDTO.getNickname() == null;
    }
}
