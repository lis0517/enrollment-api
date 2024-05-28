package kr.sparta.enrollment.domain.student;

import kr.sparta.enrollment.domain.student.model.Student;
import kr.sparta.enrollment.domain.student.model.StudentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findAllByStatus(StudentStatus status);
}
