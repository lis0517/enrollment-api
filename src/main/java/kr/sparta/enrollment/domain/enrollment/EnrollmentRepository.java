package kr.sparta.enrollment.domain.enrollment;

import kr.sparta.enrollment.domain.enrollment.model.Course;
import kr.sparta.enrollment.domain.enrollment.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByIdAndStudentId(Long enrollId, Long studentId);
}
