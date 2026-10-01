package com.example.Student.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.example.Student.Dto.StudentRequestDto;
import com.example.Student.Dto.StudentResponseDto;
import com.example.entity.Student;

@Mapper(componentModel = "spring")
public interface StudentMapper {

	@Mapping(target = "studentId", ignore = true)
    Student toEntity(StudentRequestDto studentRequestDto);
	
	@Mapping(target = "studentId", ignore = true)
    void updateEntity(StudentRequestDto dto, @MappingTarget Student student);

    StudentResponseDto toResponseDto(Student student);
}
