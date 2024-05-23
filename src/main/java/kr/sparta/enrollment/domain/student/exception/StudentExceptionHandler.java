package kr.sparta.enrollment.domain.student.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StudentExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    ResponseEntity<?> onStudentNotFoundException(NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}