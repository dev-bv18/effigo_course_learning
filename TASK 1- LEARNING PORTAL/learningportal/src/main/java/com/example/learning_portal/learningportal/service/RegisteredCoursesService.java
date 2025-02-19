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
    private RegisteredCoursesRepository registeredCoursesRepository;
    public RegisteredCoursesPopulator registeredCoursesPopulator;
    public RegisteredCoursesService(RegisteredCoursesRepository registeredCoursesRepository,RegisteredCoursesPopulator registeredCoursesPopulator){
        this.registeredCoursesPopulator=registeredCoursesPopulator;
        this.registeredCoursesRepository=registeredCoursesRepository;
    }
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

    public RegisteredCoursesDTO createNewRegisteredCourses(RegisteredCoursesDTO registeredCoursesDTO) {
        Long userId = registeredCoursesDTO.getUser().getId();
        Long courseId = registeredCoursesDTO.getCourse().getId();


        boolean alreadyRegistered = registeredCoursesRepository.existsByUserIdAndCourseId(userId, courseId);
        if (alreadyRegistered) {
            throw new RuntimeException("User is already registered for this course.");
        }


        RegisteredCourses registeredCourses = registeredCoursesPopulator.dtoToRegisteredCourse(registeredCoursesDTO);
        RegisteredCourses savedCourse = registeredCoursesRepository.save(registeredCourses);

        return registeredCoursesPopulator.registeredCoursetoDTO(savedCourse);
    }
}
