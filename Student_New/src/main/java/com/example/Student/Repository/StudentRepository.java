package com.example.Student.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.entity.Student;
import com.example.Student.enums.Course;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
        

    Page<Student> findByCourse(Course course,   Pageable pageable);

    @Query("SELECT s FROM Student s WHERE s.age > :age")
    List<Student> findStudentsByAge(@Param("age") Integer age);
    
    @Query(value = "SELECT * FROM Std_table WHERE course = :course",
    	       nativeQuery = true)
    	List<Student> findStudentsByCourseNative(@Param("course") String course);
    
    @Query(name = "Student.findByEmail")
    List<Student> findByEmail(@Param("email") String email); 
}
