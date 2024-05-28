package kr.sparta.enrollment.domain.student.model;

import jakarta.persistence.*;
import kr.sparta.enrollment.domain.enrollment.model.Enrollment;
import kr.sparta.enrollment.domain.score.model.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "student")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    @Enumerated(value = EnumType.STRING)
    private StudentStatus status;

    @OneToMany(mappedBy = "studentId")
    private List<Enrollment> courseList;

    @OneToMany(mappedBy = "student")
    private List<Score> scores;
}
