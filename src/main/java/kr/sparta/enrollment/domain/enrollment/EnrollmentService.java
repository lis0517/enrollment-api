package kr.sparta.enrollment.domain.enrollment;

import kr.sparta.enrollment.domain.student.StudentRepository;
import kr.sparta.enrollment.domain.student.exception.NotFoundException;
import kr.sparta.enrollment.domain.student.model.Course;
import kr.sparta.enrollment.domain.student.model.Enrollment;
import kr.sparta.enrollment.domain.student.model.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
    }

    public Enrollment getEnrollment() {
        return enrollmentRepository.findById(1L).orElseThrow(() -> new NotFoundException("No enrollment"));
    }

    public List<Enrollment> addEnrollments(Long studentNo, List<Course> courseList) {
        // 존재하는 회원인지 검증을 위해 한 번 조회한다
        studentRepository.findById(studentNo)
                .orElseThrow(() -> new NotFoundException("No student found with no " + studentNo));

        final List<Enrollment> enrollmentList = new ArrayList<>(courseList.size());
        for (Course course : courseList) {
            final Enrollment newEnrollment = Enrollment.builder()
                    .studentId(studentNo)
                    .course(course)
                    .courseType(course.getType())
                    .build();

            enrollmentList.add(newEnrollment);
        }
        enrollmentRepository.saveAll(enrollmentList);

        return enrollmentList;
    }
}
