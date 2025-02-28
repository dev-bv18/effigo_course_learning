package com.example.app1.app1.service;

import com.example.app1.app1.entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class UserFileReaderService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<User> readUsersFromFile() throws IOException {
        ClassPathResource resource = new ClassPathResource("users.json");
        try (InputStream inputStream = resource.getInputStream()) {
            return objectMapper.readValue(inputStream, new TypeReference<List<User>>() {});
        }
    }
}
