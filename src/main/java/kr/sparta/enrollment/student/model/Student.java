package kr.sparta.enrollment.student.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity(name = "student")
@Getter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String status;

    @OneToMany(mappedBy = "studentId")
    private List<Enrollment> courseList;
}
