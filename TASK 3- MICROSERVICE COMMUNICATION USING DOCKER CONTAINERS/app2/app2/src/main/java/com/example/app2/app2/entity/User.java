package com.example.app2.app2.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users", schema="public")
public class User {

    @Id
    private Long id;
    private String name;
    private String email;

}
