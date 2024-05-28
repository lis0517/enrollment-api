package kr.sparta.enrollment.domain.score;

import kr.sparta.enrollment.domain.enrollment.model.Enrollment;
import kr.sparta.enrollment.domain.score.model.AverageGrade;
import kr.sparta.enrollment.domain.score.model.Score;
import kr.sparta.enrollment.domain.student.model.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    boolean existsByEnrollmentAndRound(Enrollment enrollment, int round);

    Optional<Score> findByStudentIdAndEnrollmentIdAndRound(Long studentId, Long enrollmentId, int round);

    List<Score> findByStudentIdAndEnrollmentId(Long studentId, Long enrollmentId);

    void deleteByStudentId(Long studentId);

    List<Score> findMandatoryScoresByStudentId(Long studentId);

    List<Score> findMandatoryScoresByStudentStatus(StudentStatus status);
}
