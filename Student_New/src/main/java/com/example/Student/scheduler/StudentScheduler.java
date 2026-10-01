package com.example.Student.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.Student.Service.StudentServiceimpl;

@Component
public class StudentScheduler {

    @Autowired
    private StudentServiceimpl studentService;

    @Scheduled(fixedRate = 60000)
    public void runtask() {

        int studentCount = studentService.getStudentCount();

        System.out.println("Total students: " + studentCount);
    }
}