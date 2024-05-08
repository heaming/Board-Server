package com.fastcampus.boardserver.dto;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="COMMENT")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private int id;
    private int postId;
    private String contents;
    private int subCommentId;

}
