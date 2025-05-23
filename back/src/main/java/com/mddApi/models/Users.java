package com.mddApi.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USER", indexes = {@Index(name = "USER_index", columnList = "email", unique = true)})
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255, name = "username")
    private String username;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column()
    private LocalDateTime updated_at;



    @PrePersist
    protected void onCreate() {
        created_at = LocalDateTime.now();
    }


}
