package kr.sparta.enrollment.domain.score.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class StudentGradeResponse {
    private Long id;
    private String name;
    private Long courseNo;
    private String courseName;
    private Map<Integer, String> gradeList;
}
