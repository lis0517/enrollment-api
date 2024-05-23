package kr.sparta.enrollment.student.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Entity(name = "student")
@Getter
@Builder
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
}
