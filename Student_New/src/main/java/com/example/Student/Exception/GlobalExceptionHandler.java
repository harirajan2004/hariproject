package com.example.Student.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.constants.Constants;

import tools.jackson.databind.exc.UnrecognizedPropertyException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleStudentNotFoundException(
            StudentNotFoundException ex) {

        return ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationException(
            MethodArgumentNotValidException ex) {

        return Constants.VALIDATION_FAILED;
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidJson(HttpMessageNotReadableException ex) {

        if (ex.getMessage().contains("studentId")) {

            StudentidNotAllowedException exception =new StudentidNotAllowedException( Constants.STUDENT_ID_NOT_ALLOWED);
                  

            return exception.getMessage();
        }

        return Constants.SOMETHING_WENT_WRONG;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception ex) {

        return Constants.SOMETHING_WENT_WRONG;
    }
}