package com.example.Student.Dto;


import java.time.LocalDateTime;

import org.springframework.data.annotation.LastModifiedDate;

import com.example.Student.enums.Course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentResponseDto {

    private Integer studentId;

    private String studentName;

    private String email;

    private Integer age;

    private Course course;

    private String phoneNumber;
    
	private String createdBy;
	private LocalDateTime createdDate;

	private String updatedBy;
	private LocalDateTime updatedDate;

 }
