package com.university.model.student;

import com.university.model.cli.Entity;

import java.util.ArrayList;
import java.util.List;

public class Student implements Recordable, Entity {
    private String name;
    private List<String> courses;
    private int id;

    public Student() {
        this.name = "";
        this.courses = new ArrayList<>();
    }
    public Student(String name, List<String> courses) {
        this.name = name;
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getCourseCount() {
        return courses.size();
    }


    public void addCourse(String course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    @Override
    public String toParsedString() {
        // Only output the student name and the number of courses
        return name + "," + getCourseCount();
    }


    public String getName() {
        return name;
    }

    public List<String> getCourses() {
        return courses;
    }
}

