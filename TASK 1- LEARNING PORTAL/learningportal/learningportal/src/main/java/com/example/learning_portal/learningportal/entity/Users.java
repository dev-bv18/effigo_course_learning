package com.example.learning_portal.learningportal.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class Users {
  public enum Role{
    ADMIN,LEARNER,AUTHOR
  }
  @Id
    @Column(name="user_id", nullable = false,unique = true)
    private long id;

    @Column(name="password",nullable = false)
    private long passWord;

    @Column(name = "registration_date_time")
    private LocalDateTime registrationDateTime=LocalDateTime.now();

    @Column(name="user_role",nullable = false)
    private Role userRole;

    @Column(name="username",nullable = false,unique = true)
    private long userName;

}
