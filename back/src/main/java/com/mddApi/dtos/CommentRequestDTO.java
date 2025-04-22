package com.mddApi.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class CommentRequestDTO {

        private String content;
        private Integer authorId;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Integer articleId;


}
