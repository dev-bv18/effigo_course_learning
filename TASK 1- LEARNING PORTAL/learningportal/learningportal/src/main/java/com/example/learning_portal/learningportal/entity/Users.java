package com.example.learning_portal.learningportal.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users",schema = "public")
public class Users {
  public enum Role{
    ADMIN,LEARNER,AUTHOR
  }
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="password",nullable = false)
    private String passWord;

    @Column(name = "registration_date_time")
    private LocalDateTime registrationDateTime=LocalDateTime.now();

    @Column(name="user_role",nullable = false)
    private Role userRole;

    @Column(name="username",nullable = false,unique = true)
    private String userName;

}
