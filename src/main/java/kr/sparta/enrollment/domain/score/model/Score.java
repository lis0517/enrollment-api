package kr.sparta.enrollment.domain.score.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import kr.sparta.enrollment.domain.enrollment.model.Course;
import kr.sparta.enrollment.domain.enrollment.model.CourseType;
import kr.sparta.enrollment.domain.enrollment.model.Enrollment;
import kr.sparta.enrollment.domain.score.ScoreHelper;
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
    @Enumerated(EnumType.STRING)
    private Grade grade;

    public void updateScore(int newScore){
        this.score = newScore;
        calculateGrade();
    }

    @PrePersist
    public void calculateGrade() {

        if ( enrollment.getCourse().getType() == CourseType.MANDATORY ){
            grade = ScoreHelper.calculateMandatoryCourseGrade(score);
        }else{
            grade = ScoreHelper.calculateOptionalCourseGrade(score);
        }
    }
}
