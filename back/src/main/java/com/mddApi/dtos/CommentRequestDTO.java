package com.mddApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
public class CommentRequestDTO {
        @NotBlank(message = "Comment must contain text")
        private String content;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        @NotNull(message = "The comment must have an articleId")
        private Integer articleId;


}
