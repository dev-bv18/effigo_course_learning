package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.dto.FavouriteCourseDTO;
import com.example.learning_portal.learningportal.entity.FavouriteCourse;
import com.example.learning_portal.learningportal.mapper.FavouriteCoursePopulator;
import com.example.learning_portal.learningportal.repository.FavouriteCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouriteCourseService {

    private FavouriteCourseRepository favouriteCourseRepository;
    private FavouriteCoursePopulator favouriteCoursePopulator;

    public FavouriteCourseService(FavouriteCourseRepository favouriteCourseRepository,FavouriteCoursePopulator favouriteCoursePopulator){
        this.favouriteCoursePopulator=favouriteCoursePopulator;
        this.favouriteCourseRepository=favouriteCourseRepository;
    }
    private static Logger log = LoggerFactory.getLogger(FavouriteCourseService.class);
    public List<FavouriteCourseDTO> getAllFavouriteCourse(){
        return  favouriteCourseRepository.findAll().stream().map(favouriteCoursePopulator::favouriteCourseToDto).collect(Collectors.toList());
    }
    public FavouriteCourseDTO getOneFavouriteCourse(Long id)
    {   Optional<FavouriteCourse> oneFavouriteCourse=favouriteCourseRepository.findById(id);
        return favouriteCoursePopulator.optionalFavouriteCourseToDTO(oneFavouriteCourse);
    }

    public void deleteFavouriteCourse(long id){
        log.info("Service-deleting course"+ id);
        favouriteCourseRepository.deleteById(id);
    }
    public FavouriteCourseDTO addFavouriteCourse(FavouriteCourseDTO favouriteCourseDTO){
       log.info("FAVOURITE COURSE");
       log.info(favouriteCourseDTO.toString());
       FavouriteCourse favouriteCourse=favouriteCoursePopulator.dtoToFavouriteCourse(favouriteCourseDTO);
        return favouriteCoursePopulator.favouriteCourseToDto(favouriteCourseRepository.save(favouriteCourse));
    }
}
