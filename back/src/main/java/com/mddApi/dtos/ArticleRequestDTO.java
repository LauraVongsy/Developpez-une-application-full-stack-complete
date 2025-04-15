package com.mddApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ArticleRequestDTO {
    private String title;
    private String content;
    private Integer authorId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer themeId;

}
