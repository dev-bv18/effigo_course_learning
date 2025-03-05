package com.example.spring_Security.SpringJWT.service;


import com.example.spring_Security.SpringJWT.entity.UserData;
import com.example.spring_Security.SpringJWT.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserDataRepository repository;
    @Autowired
    private PasswordEncoder encoder;


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserData> userDetail = repository.findByUsername(username);

        // Converting UserInfo to UserDetails
        return userDetail.map(UserDataDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    public String addUser(UserData userInfo) {
        // Encode password before saving the user
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }
}
