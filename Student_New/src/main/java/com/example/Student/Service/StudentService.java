package com.example.Student.Service;

import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.Student.Dto.StudentRequestDto;
import com.example.Student.Dto.StudentResponseDto;
import com.example.Student.enums.Course;

public interface StudentService {

    // CREATE OR UPDATE
    ResponseEntity<StudentResponseDto> createOrUpdateStudent(
            StudentRequestDto studentRequestDto);

    // GET ALL STUDENTS
    ResponseEntity<List<StudentResponseDto>> getAllStudents();

    // GET STUDENT BY ID
    ResponseEntity<StudentResponseDto> getStudentById(
            Integer studentId);

    // DELETE STUDENT
    ResponseEntity<String> deleteStudent(
            Integer studentId);

    // GET STUDENTS BY COURSE
    ResponseEntity<List<StudentResponseDto>> getStudentsByCourse(
            String course);

    // PAGINATION
    Page<StudentResponseDto> getStudents(
            int pageNo,
            int pageSize);

    // SORTING + PAGINATION
    Page<StudentResponseDto> getStudentsSorted(
            int pageNo,
            int pageSize,
            String field);

    // COURSE + PAGINATION
    Page<StudentResponseDto> getByCourse(
            Course course,
            int pageNo,
            int pageSize);

    // GET STUDENT BY AGE
    List<StudentResponseDto> getStudentByAge(
            Integer age);

    // NATIVE QUERY - COURSE
    List<StudentResponseDto> getSudentsByCourseNative(
            String course);

    // GET STUDENT BY EMAIL
    StudentResponseDto getStudentByEmail(
            String email);

    // GET STUDENT COUNT
    int getStudentCount();

    // FILE UPLOAD
    String uploadFile(
            MultipartFile file);

    // FILE DOWNLOAD
    Resource downloadFile(
            String fileName);
}