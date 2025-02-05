package com.example.learning_portal.learningportal.service;

import com.example.learning_portal.learningportal.entity.FavouriteCourse;
import com.example.learning_portal.learningportal.repository.FavouriteCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteCourseService {
    @Autowired
    private FavouriteCourseRepository favouriteCourseRepository;
    private static Logger log = LoggerFactory.getLogger(FavouriteCourseService.class);
    public List<FavouriteCourse> getAllFavouriteCourse(){
        return  favouriteCourseRepository.findAll();
    }
    public Optional<FavouriteCourse> getOneFavouriteCourse(Long id)
    {
        return favouriteCourseRepository.findById(id);
    }

    public void deleteFavouriteCourse(long id){
        log.info("Service-deleting course"+ id);
        favouriteCourseRepository.deleteById(id);
    }
    public FavouriteCourse addFavouriteCourse(FavouriteCourse favouriteCourse){
       log.info("FAVOURITE COURSE");
log.info(favouriteCourse.toString());
        return favouriteCourseRepository.save(favouriteCourse);
    }
}
