package com.example.learning_portal.learningportal.mapper;

import com.example.learning_portal.learningportal.dto.UsersDTO;
import com.example.learning_portal.learningportal.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface UsersPopulator {

    @Mapping(target="id",source = "id")
    @Mapping(target="userName",source="userName")
    @Mapping(target = "passWord",source = "passWord")
    @Mapping(target="userRole",source="userRole")
    @Mapping(target = "registrationDateTime",source = "registrationDateTime")
    Users dtoToUsers(UsersDTO usersDTO);

    UsersDTO usersToDto(Users users);
    default UsersDTO optionalUserToDTO(Optional<Users> users){
       if(users.isPresent()){
           return usersToDto(users.get());
       }
       else
           return null;
    }
}
