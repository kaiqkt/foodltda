package com.zed.course.controller;

import com.zed.course.model.Course;
import com.zed.course.repository.CourseRepository;
import com.zed.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/admin/course")
public class CourseController {

    @Autowired
    CourseService courseService;

    @Autowired
    CourseRepository a;

    @GetMapping
    public ResponseEntity<Page<Course>> list(Pageable pageable) {
        return ResponseEntity.ok().body(courseService.list(pageable));
    }
}
