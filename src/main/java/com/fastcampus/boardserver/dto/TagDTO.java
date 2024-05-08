package com.fastcampus.boardserver.dto;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    private String name;
    private String url;
    private int postId;
}
