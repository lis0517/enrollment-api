package kr.sparta.enrollment.domain.score.repository;

import kr.sparta.enrollment.domain.enrollment.model.Course;
import kr.sparta.enrollment.domain.enrollment.model.Enrollment;
import kr.sparta.enrollment.domain.score.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    boolean existsByEnrollmentAndRound(Enrollment enrollment, int round);

    Optional<Score> findByStudentIdAndEnrollmentIdAndRound(Long studentId, Long enrollmentId, int round);

    List<Score> findByStudentIdAndEnrollmentId(Long studentId, Long enrollmentId);
}
