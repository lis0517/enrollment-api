package kr.sparta.enrollment.domain.student.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
}
