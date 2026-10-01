package com.example.Student.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Student.Dto.StudentRequestDto;
import com.example.Student.Dto.StudentResponseDto;
import com.example.Student.Service.StudentService;
import com.example.Student.enums.Course;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;

@RestController

public class StudentController {

    @Autowired
    private StudentService studentService;


    // CREATE OR UPDATE
    @PostMapping("/students/save")
    @Operation(summary ="create Or UpdateStudent",description = "create or update the employee" )
    
    public ResponseEntity<StudentResponseDto> createOrUpdateStudent(
            @Valid @RequestBody StudentRequestDto studentRequestDto) {

        return studentService.createOrUpdateStudent(
                studentRequestDto);
    }


    // GET ALL STUDENTS
    @GetMapping("/students")
    @Operation(summary ="Get All Student ",description = "Display The Student Details" )
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {

        return studentService.getAllStudents();
    }


    // GET STUDENT BY ID
    @GetMapping("/students/{studentId}")
    @Operation(summary ="Get By ID ",description = "Display The Student Details By Id" )
    @ApiResponse(responseCode = "200",description = "Student Found")
    public ResponseEntity<StudentResponseDto> getStudentById(
            @PathVariable Integer studentId) {

        return studentService.getStudentById(studentId);
    }


    // DELETE STUDENT
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable Integer studentId) {

        return studentService.deleteStudent(studentId);
    }


    @GetMapping("/students/course")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByCourse(
            @RequestParam String course) {

        return studentService.getStudentsByCourse(course);
    }


    @GetMapping("/students/page")
    public ResponseEntity<Page<StudentResponseDto>> getStudents(
            @RequestParam int pageNo,
            @RequestParam int pageSize) {

        Page<StudentResponseDto> students =
                studentService.getStudents(pageNo, pageSize);

        return ResponseEntity.ok()
                .header("X-Total-Students",
                        String.valueOf(students.getTotalElements()))
                .body(students);
                        
    }


    @GetMapping("/students/sorted")
    public Page<StudentResponseDto> getStudentsSorted(
            @RequestParam int pageNo,
            @RequestParam int pageSize,
            @RequestParam String field) {

        return studentService.getStudentsSorted(
                pageNo, pageSize, field);
    }


    @GetMapping("/students/course/page")
    public Page<StudentResponseDto> getByCourse(
            @RequestParam Course course,
            @RequestParam int pageNo,
            @RequestParam int pageSize) {

        return studentService.getByCourse(
                course, pageNo, pageSize);
    }
    
    @GetMapping("/students/age")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByAge(
            @RequestParam Integer age) {

        return ResponseEntity.ok(
                studentService.getStudentByAge(age));
    }
    
    @GetMapping("/students/native/course")
    public ResponseEntity<List<StudentResponseDto>> getStudentsByCourseNative(
            @RequestParam String course) {

        return ResponseEntity.ok(
                studentService.getSudentsByCourseNative(course));
    }
    
    @GetMapping("/students/email")
    public ResponseEntity<StudentResponseDto> getStudentByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(
                studentService.getStudentByEmail(email));
    }
    
 // FILE UPLOAD
    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("file") MultipartFile file) {

        return studentService.uploadFile(file);
    }


    // FILE DOWNLOAD
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(
            @PathVariable String fileName) {

        Resource resource =
                studentService.downloadFile(fileName);

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\""
                                + resource.getFilename()
                                + "\"")
                .body(resource);
    }
    
}