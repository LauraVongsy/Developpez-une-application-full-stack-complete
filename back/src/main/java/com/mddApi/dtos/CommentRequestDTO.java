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
        @NotBlank(message = "Le commentaire doit avoir un contenu")
        private String content;

        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

        @NotNull(message = "Le commentaire doit correspondre à un article en particulier")
        private Integer articleId;


}
