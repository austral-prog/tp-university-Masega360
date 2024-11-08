package com.university.taskcoursetest.courselogic;

import com.university.model.evaluation.Evaluation;
import com.university.model.evaluation.FinalExam;
import com.university.model.evaluation.OralExam;
import com.university.model.criteria.StudentCourse;
import com.university.service.StudentCourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class StudentCourseServiceTest {

    private StudentCourseService studentCourseService;
    private StudentCourse studentCourse;



    @Test
    void testWriteEvaluationsReport(){
        StudentCourseService StudentCourseUniversityService = new StudentCourseService();
        StudentCourseUniversityService.loadAndParseStudents();// This loads and parses the students into the empty list
        StudentCourseUniversityService.sortStudents();
        StudentCourseUniversityService.loadAndParseEvaluated();
        StudentCourseUniversityService.writeStudentReport();
        assertDoesNotThrow(() -> StudentCourseUniversityService.writeStudentReport());
    }
}
