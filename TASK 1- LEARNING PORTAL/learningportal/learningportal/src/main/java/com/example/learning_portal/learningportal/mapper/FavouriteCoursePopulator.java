package com.example.learning_portal.learningportal.mapper;

import com.example.learning_portal.learningportal.dto.FavouriteCourseDTO;
import com.example.learning_portal.learningportal.entity.FavouriteCourse;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface FavouriteCoursePopulator {
    FavouriteCourse dtoToFavouriteCourse(FavouriteCourseDTO favouriteCourseDTO);
    FavouriteCourseDTO favouriteCourseToDto(FavouriteCourse favouriteCourse);
    default FavouriteCourseDTO optionalFavouriteCourseToDTO(Optional<FavouriteCourse> favouriteCourse){
        if(favouriteCourse.isPresent())
            return favouriteCourseToDto(favouriteCourse.get());
        else
            return null;
    }
}
