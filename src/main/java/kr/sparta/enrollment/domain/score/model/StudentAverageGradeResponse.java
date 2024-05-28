package kr.sparta.enrollment.domain.score.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StudentAverageGradeResponse {
    private Long id;
    private String name;
    private List<AverageGrade> gradeList;
}
