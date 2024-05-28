package kr.sparta.enrollment.domain.score.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import kr.sparta.enrollment.domain.enrollment.model.Course;
import kr.sparta.enrollment.domain.enrollment.model.CourseType;
import kr.sparta.enrollment.domain.enrollment.model.Enrollment;
import kr.sparta.enrollment.domain.student.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "score")
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"enrollment_id", "round"})
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @Column(name="course", nullable = false)
    @Enumerated(EnumType.STRING)
    private Course course;

    @Column(name = "round", nullable = false)
    @Min(value = 1, message = "회차는 1보다 크거나 같아야합니다.")
    @Max(value = 10, message = "회차는 10보다 작거나 같아야합니다.")
    private int round;

    @Column(name = "score", nullable = false)
    @Min(value = 0, message = "점수는 0보다 크거나 같아야합니다.")
    @Max(value = 100, message = "점수는 100보다 작거나 같아야합니다.")
    private int score;

    @Column(nullable = false)
    private String grade;

    public void updateScore(int newScore){
        this.score = newScore;
        calculateGrade();
    }

    @PrePersist
    public void calculateGrade() {

        if ( enrollment.getCourse().getType() == CourseType.MANDATORY ){
            if (score >= 95 && score <= 100) {
                grade = "A";
            } else if (score >= 90 && score <= 94) {
                grade = "B";
            } else if (score >= 80 && score <= 89) {
                grade = "C";
            } else if (score >= 70 && score <= 79) {
                grade = "D";
            } else if (score >= 60 && score <= 69) {
                grade = "F";
            } else {
                grade = "N";
            }
        }else{
            if (score >= 90 && score <= 100) {
                grade = "A";
            } else if (score >= 80 && score <= 89) {
                grade = "B";
            } else if (score >= 70 && score <= 79) {
                grade = "C";
            } else if (score >= 60 && score <= 69) {
                grade = "D";
            } else if (score >= 50 && score <= 59) {
                grade = "F";
            } else {
                grade = "N";
            }
        }
    }
}
