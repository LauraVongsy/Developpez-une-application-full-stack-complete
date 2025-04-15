package com.mddApi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @Column(name = "theme_id",  insertable = false, updatable = false)
    private Integer themeId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "author_id",  insertable = false, updatable = false)
    private Integer authorId;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private  LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    private Users author;

    @ManyToOne
    @JoinColumn(name = "theme_id", referencedColumnName = "id")
    private Themes theme;

}
