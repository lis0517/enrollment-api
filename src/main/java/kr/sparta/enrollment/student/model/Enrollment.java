package kr.sparta.enrollment.student.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "enrollment")
@Getter
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
