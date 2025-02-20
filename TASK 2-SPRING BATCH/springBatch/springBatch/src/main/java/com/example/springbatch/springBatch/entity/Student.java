package com.example.springbatch.springBatch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "studentData")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lId;

    private String id;

    private String name;
    private String language;

    @Column(length=1000)
    private String bio;

    private Double version;
}

