package com.workintech.spring17challenge.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CourseResult {
    private Course course;
    private Integer totalGpa;
}