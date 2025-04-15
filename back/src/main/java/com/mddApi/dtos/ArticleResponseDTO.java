package com.mddApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ArticleResponseDTO {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String authorName;
    private String themeName;
}

