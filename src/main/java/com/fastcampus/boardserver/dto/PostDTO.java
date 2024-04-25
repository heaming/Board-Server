package com.fastcampus.boardserver.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="POST")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;

    private String name;

    @Column(columnDefinition = "TINYINT(1)")
    private int isAdmin;

    private String contents;

    private Date createTime;

    private int views;

    private int categoryId;

    private int userId;

    private int fileId;

    private Date updateTime;
}
