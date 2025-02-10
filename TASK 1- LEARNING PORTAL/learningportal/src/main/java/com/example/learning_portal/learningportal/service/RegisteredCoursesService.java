package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.dto.RegisteredCoursesDTO;
import com.example.learning_portal.learningportal.entity.RegisteredCourses;
import com.example.learning_portal.learningportal.mapper.RegisteredCoursesPopulator;
import com.example.learning_portal.learningportal.repository.RegisteredCoursesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegisteredCoursesService {
    @Autowired
    private RegisteredCoursesRepository registeredCoursesRepository;
    @Autowired
    public RegisteredCoursesPopulator registeredCoursesPopulator;
    public List<RegisteredCoursesDTO> getAllRegisteredCourses(){
        return registeredCoursesRepository.findAll().stream().map(registeredCoursesPopulator::registeredCoursetoDTO).collect(Collectors.toList());
    }
    public RegisteredCoursesDTO getOneRegisteredCourse(Long id){
        Optional<RegisteredCourses> registeredCourses= registeredCoursesRepository.findById(id);
        return registeredCoursesPopulator.optionalRegisteredCourseToDTO(registeredCourses);
    }
    public void deleteRegisteredCourse(Long id){
        registeredCoursesRepository.deleteById(id);
    }

    public RegisteredCoursesDTO createNewRegisteredCourses(RegisteredCoursesDTO registeredCoursesDTO){
        RegisteredCourses registeredCourses=registeredCoursesPopulator.dtoToRegisteredCourse(registeredCoursesDTO);
        RegisteredCourses savedCourse=registeredCoursesRepository.save(registeredCourses);
        return registeredCoursesPopulator.registeredCoursetoDTO(savedCourse);
    }
}
