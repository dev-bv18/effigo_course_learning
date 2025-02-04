package com.example.learning_portal.learningportal.dto;

import com.example.learning_portal.learningportal.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private String username;
    private String password;
    private Users.Role role;
}
