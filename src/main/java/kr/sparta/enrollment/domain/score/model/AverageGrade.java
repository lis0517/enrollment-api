package kr.sparta.enrollment.domain.score.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AverageGrade {
    private String courseName;
    private Grade averageGrade;

    public AverageGrade(String name, Grade grade) {
        this.courseName = name;
        this.averageGrade = grade;
    }
}
