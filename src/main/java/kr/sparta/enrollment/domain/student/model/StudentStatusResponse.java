package kr.sparta.enrollment.domain.student.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentStatusResponse {
    private StudentStatus status;
    private List<Student> studentList;
}
