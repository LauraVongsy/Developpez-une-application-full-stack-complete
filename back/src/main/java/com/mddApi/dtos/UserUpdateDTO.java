package com.mddApi.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDTO {
    private String email;
    private String username;
    private String password;
    private Integer id;
}
