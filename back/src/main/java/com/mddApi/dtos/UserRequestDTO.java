package com.mddApi.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "L'utilisateur doit avoir un email")
    private String email;
    @NotBlank(message = "L'utilisateur doit avoir un username")
    private String username;
    @NotBlank(message = "L'utilisateur doit avoir un mot de passe")
    private String password;

}
