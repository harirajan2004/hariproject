package com.example.Student.Service;

import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.Student.Dto.StudentRequestDto;
import com.example.Student.Dto.StudentResponseDto;
import com.example.Student.Exception.StudentNotFoundException;
import com.example.Student.Repository.StudentRepository;
import com.example.Student.enums.Course;
import com.example.Student.mapper.StudentMapper;
import com.example.constants.Constants;
import com.example.entity.Student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StudentServiceimpl implements StudentService {

    private final String UPLOAD_DIR = "uploads/";

    private static final Logger logger =
            LoggerFactory.getLogger(StudentServiceimpl.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentMapper studentMapper;


    @Override
    @Transactional
    public ResponseEntity<StudentResponseDto> createOrUpdateStudent(
            StudentRequestDto studentRequestDto) {

        logger.info("Created or updated Student request received");

        Student student;

        if (studentRequestDto.getStudentId() == null) {

            logger.info("Created New Student");

            student = studentMapper.toEntity(studentRequestDto);

        } else {

            logger.info("Updated Student with id");

            student = studentRepository.findById(
                    studentRequestDto.getStudentId())
                    .orElseThrow(() ->
                            new StudentNotFoundException(
                                    Constants.STUDENT_NOT_FOUND));

            student.setStudentName(
                    studentRequestDto.getStudentName());

            student.setEmail(
                    studentRequestDto.getEmail());

            student.setAge(
                    studentRequestDto.getAge());

            student.setCourse(
                    studentRequestDto.getCourse());

            student.setPhoneNumber(
                    studentRequestDto.getPhoneNumber());
        }

        Student savedStudent =
                studentRepository.save(student);

        StudentResponseDto response =
                studentMapper.toResponseDto(savedStudent);

        return ResponseEntity.ok(response);
    }


    // GET ALL STUDENTS
    @Override
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {

        logger.info("Fetching all Student");

        List<StudentResponseDto> students =
                studentRepository.findAll()
                        .stream()
                        .map(studentMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(students);
    }


    // GET STUDENT BY ID
    @Cacheable(value = "students", key = "#studentId")
    @Override
    public ResponseEntity<StudentResponseDto> getStudentById(
            Integer studentId) {

        logger.info("Fetching Student with id{}", studentId);

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        Constants.STUDENT_NOT_FOUND));

        StudentResponseDto response =
                studentMapper.toResponseDto(student);

        return ResponseEntity.ok(response);
    }


    // DELETE STUDENT
    @CacheEvict(value = "students", key = "#studentId")
    @Override
    public ResponseEntity<String> deleteStudent(
            Integer studentId) {

        Student student =
                studentRepository.findById(studentId)
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        Constants.STUDENT_NOT_FOUND));

        studentRepository.delete(student);

        return ResponseEntity.ok(
                "Student deleted successfully");
    }


    // GET STUDENTS BY COURSE
    @Override
    public ResponseEntity<List<StudentResponseDto>> getStudentsByCourse(
            String course) {

        List<StudentResponseDto> students =
                studentRepository.findAll()
                        .stream()
                        .filter(student ->
                                student.getCourse()
                                        .name()
                                        .equalsIgnoreCase(course))
                        .map(studentMapper::toResponseDto)
                        .toList();

        return ResponseEntity.ok(students);
    }


    // PAGINATION
    @Override
    public Page<StudentResponseDto> getStudents(
            int pageNo,
            int pageSize) {

        Pageable pageable =
                PageRequest.of(pageNo, pageSize);

        return studentRepository.findAll(pageable)
                .map(student ->
                        studentMapper.toResponseDto(student));
    }


    // SORTING + PAGINATION
    @Override
    public Page<StudentResponseDto> getStudentsSorted(
            int pageNo,
            int pageSize,
            String field) {

        Pageable pageable =
                PageRequest.of(
                        pageNo,
                        pageSize,
                        Sort.by(field));

        return studentRepository.findAll(pageable)
                .map(student ->
                        studentMapper.toResponseDto(student));
    }


    // COURSE + PAGINATION
    @Override
    public Page<StudentResponseDto> getByCourse(
            Course course,
            int pageNo,
            int pageSize) {

        Pageable pageable =
                PageRequest.of(pageNo, pageSize);

        return studentRepository
                .findByCourse(course, pageable)
                .map(student ->
                        studentMapper.toResponseDto(student));
    }


    @Override
    public List<StudentResponseDto> getStudentByAge(Integer age) {

        return studentRepository.findStudentsByAge(age)
                .stream()
                .map(studentMapper::toResponseDto)
                .toList();
    }


    @Override
    public List<StudentResponseDto> getSudentsByCourseNative(
            String course) {

        return studentRepository.findStudentsByCourseNative(course)
                .stream()
                .map(studentMapper::toResponseDto)
                .toList();
    }


    @Override
    public StudentResponseDto getStudentByEmail(String email) {

        Student student =
                studentRepository.findByEmail(email)
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new StudentNotFoundException(
                                        Constants.STUDENT_NOT_FOUND));

        return studentMapper.toResponseDto(student);
    }


    @Override
    public int getStudentCount() {

        return (int) studentRepository.count();
    }


    // FILE UPLOAD
    @Override
    public String uploadFile(MultipartFile file) {

        try {

            Path path = Paths.get(UPLOAD_DIR);

            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            Files.copy(
                    file.getInputStream(),
                    path.resolve(file.getOriginalFilename())
            );

            return "File Uploaded Successfully : "
                    + file.getOriginalFilename();

        } catch (IOException e) {

            throw new RuntimeException("Unable to upload file");
        }
    }



    // FILE DOWNLOAD
    @Override
    public Resource downloadFile(String fileName) {

        try {

            Path filePath = Paths.get(UPLOAD_DIR)
                    .resolve(fileName)
                    .normalize();

            Resource resource =
                    new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("File not found");
            }

            return resource;

        } catch (Exception e) {

            throw new RuntimeException("Error dowloading file");
            
            
        
            
        }
    }
}