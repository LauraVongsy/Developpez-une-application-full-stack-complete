package com.mddApi.models;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Articles {
    @Id
    private Integer Id;

    @Column(name = "theme_id")
    private Integer themeId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "author_id")
    private Integer authorId;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "updated_at")
    private  String updatedAt;
}
