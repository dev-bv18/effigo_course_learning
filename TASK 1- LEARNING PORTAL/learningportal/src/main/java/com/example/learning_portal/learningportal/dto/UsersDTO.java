package com.example.learning_portal.learningportal.dto;

import com.example.learning_portal.learningportal.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private Long id;
    private String userName;
    private String passWord;
    private Users.Role userRole;
    private LocalDateTime registrationDateTime=LocalDateTime.now();
}
