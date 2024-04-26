package com.workintech.spring17challenge.controller;

import com.workintech.spring17challenge.dto.CalculationGpa;
import com.workintech.spring17challenge.entity.Course;
import com.workintech.spring17challenge.entity.CourseGpa;
import com.workintech.spring17challenge.entity.CourseResult;
import com.workintech.spring17challenge.entity.Grade;
import com.workintech.spring17challenge.validation.CourseValidation;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

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
        for(Course course:courses){
            if(course.getName().equalsIgnoreCase(name)){
                return course;
            }
        }

        return null;
    }

    @PostMapping
    public CourseResult save(@RequestBody Course course){
        courses.add(course);
        double totalGpa= CalculationGpa.calculateTotalGpa(course);
        return new CourseResult(course,totalGpa);
    }

    @PutMapping("/{id}")
    public CourseResult update(@RequestBody Course course,@PathVariable int id){
        courses.set(id,course);
        double totalGpa=CalculationGpa.calculateTotalGpa(course);
        return new CourseResult(course,totalGpa);
    }

    @DeleteMapping("/{id}")
    public Course delete(@PathVariable int id){
        Course course=courses.get(id);
        courses.remove(course);
        return course;
    }

}



















