package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.dto.RegisteredCoursesDTO;
import com.example.learning_portal.learningportal.entity.RegisteredCourses;
import com.example.learning_portal.learningportal.mapper.RegisteredCoursesPopulator;
import com.example.learning_portal.learningportal.repository.RegisteredCoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegisteredCoursesService {
    @Autowired
    private RegisteredCoursesRepository registeredCoursesRepository;
    @Autowired
    public RegisteredCoursesPopulator registeredCoursesPopulator;
    public List<RegisteredCourses> getAllRegisteredCourses(){
        return registeredCoursesRepository.findAll();
    }
    public Optional<RegisteredCourses> getOneRegisteredCourse(Long id){
        return registeredCoursesRepository.findById(id);
    }
    public void deleteRegisteredCourse(Long id){
        registeredCoursesRepository.deleteById(id);
    }

    public RegisteredCourses createNewRegisteredCourses(RegisteredCoursesDTO registeredCoursesDTO){
        RegisteredCourses registeredCourses=registeredCoursesPopulator.dtoToRegisteredCourse(registeredCoursesDTO);
        RegisteredCourses savedCourse=registeredCoursesRepository.save(registeredCourses);
        return savedCourse;
    }
}
