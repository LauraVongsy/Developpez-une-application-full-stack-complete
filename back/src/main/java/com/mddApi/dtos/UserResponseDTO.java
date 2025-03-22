package com.mddApi.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class UserResponseDTO {
    private Integer id;
    private String email;
    private String name;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @JsonIgnore
    private String password;
}
