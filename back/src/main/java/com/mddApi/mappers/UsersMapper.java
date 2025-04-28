package com.mddApi.mappers;

import com.mddApi.dtos.UserRequestDTO;
import com.mddApi.dtos.UserResponseDTO;
import com.mddApi.models.Users;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsersMapper {

    public UserResponseDTO toResponseDTO(Users user) {
        if (user == null) {
            return null;
        }

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setName(user.getUsername());
        dto.setCreated_at(user.getCreated_at());
        dto.setUpdated_at(user.getUpdated_at());

        return dto;
    }

    public Users toEntity(UserRequestDTO userRequestDTO) {
        if (userRequestDTO == null) {
            return null;
        }

        Users user = new Users();
        user.setEmail(userRequestDTO.getEmail());
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(userRequestDTO.getPassword());
        return user;
    }

    public List<UserResponseDTO> toResponseDTOList(List<Users> users) {
        if (users == null || users.isEmpty()) {
            return new ArrayList<>();
        }

        List<UserResponseDTO> responseDTOList = new ArrayList<>();
        for (Users user : users) {
            responseDTOList.add(toResponseDTO(user));
        }
        return responseDTOList;
    }

    public List<Users> toEntityList(List<UserRequestDTO> userRequestDTOs) {
        if (userRequestDTOs == null || userRequestDTOs.isEmpty()) {
            return new ArrayList<>();
        }

        List<Users> usersList = new ArrayList<>();
        for (UserRequestDTO dto : userRequestDTOs) {
            usersList.add(toEntity(dto));
        }
        return usersList;
    }
}
