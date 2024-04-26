package com.workintech.spring17challenge.dto;

import com.workintech.spring17challenge.entity.*;

public class CalculationGpa {
    private static CourseGpa lowCourseGpa=new LowCourseGpa();
    private static CourseGpa mediumCourseGpa=new MediumCourseGpa();
    private static CourseGpa highCourseGpa=new HighCourseGpa();

    public static double calculateTotalGpa(Course course){
        if(course.getCredit()<=2){
            return course.getGrade().getCoefficient() * course.getCredit() * lowCourseGpa.getGpa();
            

        } else if (course.getCredit()==3) {
            return course.getGrade().getCoefficient() * course.getCredit() * mediumCourseGpa.getGpa();
            
        }
        else if (course.getCredit()<=4) {
            return course.getGrade().getCoefficient() * course.getCredit() * highCourseGpa.getGpa();
            
        }
        return 0;
    }
}
