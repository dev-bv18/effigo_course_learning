package com.example.spring_Security.SpringJWT.repository;

import com.example.spring_Security.SpringJWT.entity.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDataRepository extends JpaRepository<UserData,Integer> {
    Optional<UserData> findByUsername(String username);
}
