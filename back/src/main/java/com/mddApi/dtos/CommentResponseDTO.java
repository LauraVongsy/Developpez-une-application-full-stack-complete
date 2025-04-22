package com.mddApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentResponseDTO {

        private Integer id;
        private String content;
        private String authorName;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer articleId;

}
