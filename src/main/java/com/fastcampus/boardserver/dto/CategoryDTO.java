package com.fastcampus.boardserver.dto;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="CATEGORY")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    public enum SortStatus {
        CATEGORIES, NEWEST, OLDEST
    }

    @Id
    @GeneratedValue
    @Column(name="ID")
    private int id;

    @Column(name="NAME")
    private String name;
    private SortStatus sortStatus;
    private int searchCount;
    private int pagingStartOffset;
}
