package com.mddApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ArticleRequestDTO {
    @NotBlank(message = "Le titre est obligatoire.")
    @Size(max = 255, message = "Le titre ne peut pas dépasser 255 caractères.")
    private String title;

    @NotBlank(message = "L'article doit contenir un texte'.")
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull(message = "Le titre est obligatoire.")
    private Integer themeId;

}
