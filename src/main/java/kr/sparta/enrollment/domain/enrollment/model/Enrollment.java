package kr.sparta.enrollment.domain.enrollment.model;

import jakarta.persistence.*;
import kr.sparta.enrollment.domain.score.model.Score;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "enrollment")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long studentId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Course course;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CourseType courseType;

    @OneToMany(mappedBy = "enrollment")
    private List<Score> scores;
}
