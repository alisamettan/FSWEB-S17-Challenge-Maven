package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.dto.CalculationGpa;
import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.entity.CourseGpa;
import com.workintech.spring17challenge.entity.CourseResult;
import com.workintech.spring17challenge.entity.Grade;
import com.workintech.spring17challenge.exceptions.ApiException;
import com.workintech.spring17challenge.validation.CourseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private List<Course> courses;
    private CourseGpa lowCourseGpa;
    private CourseGpa mediumCourseGpa;
    private CourseGpa highCourseGpa;

    @PostConstruct
    public void init(){
        courses=new ArrayList<>();
        courses.add(new Course(1,"Java",4,new Grade(2,"56")));
    }

    @Autowired
    public CourseController(@Qualifier("lowCourseGpa") CourseGpa lowCourseGpa,
                            @Qualifier("mediumCourseGpa") CourseGpa mediumCourseGpa,
                            @Qualifier("highCourseGpa") CourseGpa highCourseGpa) {
        this.lowCourseGpa = lowCourseGpa;
        this.mediumCourseGpa = mediumCourseGpa;
        this.highCourseGpa = highCourseGpa;
    }

    @GetMapping
    public List<Course> findAll(){
        return courses;

    }

    @GetMapping("/{name}")
    public Course getCourse(@PathVariable String name){
        CourseValidation.checkName(name);

        return courses.stream().filter(course->course.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(()->new ApiException("course not found with name: "+name,HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<CourseResult> save(@RequestBody Course course){
       CourseValidation.checkCredit(course.getCredit());
       CourseValidation.checkName(course.getName());
       courses.add(course);
       int totalGpa=CalculationGpa.calculateTotalGpa(course);
       CourseResult courseResult=new CourseResult(course,totalGpa);
        return new ResponseEntity<>(courseResult,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResult> update(@RequestBody Course course,@PathVariable int id){
        CourseValidation.checkId(id);
        CourseValidation.checkCredit(course.getCredit());
        CourseValidation.checkName(course.getName());
        course.setId(id);
        Course existingCourse=getExistingCourseById(id);
        int indexOfExisting=courses.indexOf(existingCourse);
        courses.set(indexOfExisting,course);
        Integer totalGpa=CalculationGpa.calculateTotalGpa(course);
        CourseResult courseResult=new CourseResult(courses.get(indexOfExisting),totalGpa);
        return new ResponseEntity<>(courseResult,HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public Course delete(@PathVariable int id){
        Course existingCourse=getExistingCourseById(id);
        courses.remove(existingCourse);
        return existingCourse;
    }

    private Course getExistingCourseById(Integer id) {
        return courses.stream()
                .filter(cStream -> cStream.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ApiException("course not found with id: " + id, HttpStatus.NOT_FOUND));
    }

}



















