package com.workintech.spring17challenge.entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Course {
    private int id;
    private String name;
    private int credit;
    private Grade grade;
}