package com.mddApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ArticleRequestDTO {
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title can not exceed 255 characters")
    private String title;

    @NotBlank(message = "The article must contain text.")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull(message = "Title is required.")
    private Integer themeId;

}
