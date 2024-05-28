package kr.sparta.enrollment.domain.score.model;

import kr.sparta.enrollment.domain.student.model.StudentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class StatusAverageGradeResponse {
    private StudentStatus status;
    private List<AverageGrade> gradeList;
}
