package com.mddApi.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "User must have an email")
    private String email;
    @NotBlank(message = "User must have a username")
    private String username;
    @NotBlank(message = "User must have a password")
    private String password;

}
