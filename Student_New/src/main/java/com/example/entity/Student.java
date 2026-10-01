package com.example.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.Student.enums.Course;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "Std_table")
@NamedQuery(
	    name = "Student.findByEmail",
	    query = "SELECT s FROM Student s WHERE s.email = :email"
	)
public class Student extends Audit {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer studentId;

	    private String studentName;

	    private String email;

	    private Integer age;

	    @Enumerated(EnumType.STRING)
	    private Course course;


	    private String phoneNumber;
	    
	  
	    
}
