package com.zed.course.service;

import com.zed.course.model.Course;
import com.zed.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public Page<Course> list(Pageable pageable){
        return courseRepository.findAll(pageable);
    }
}
