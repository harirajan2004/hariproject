package com.example.Student.Dto;


import com.example.Student.enums.Course;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequestDto {

	
	 private Integer studentId;
	 
    @NotBlank(message = "Student name is required")
    private String studentName;

    @NotBlank(message = "Email is required")
    @Email(message = "Enter valid email")
    private String email;

    @Min(value = 18, message = "Age must be 18 or above")
    private Integer age;

    @NotNull(message = "Course is required")
    private Course course;

    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
}