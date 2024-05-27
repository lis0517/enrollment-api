package kr.sparta.enrollment.domain.student.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SimpleStudentDto {
    private Long id;
    private String name;
}
