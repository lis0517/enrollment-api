package kr.sparta.enrollment.domain.student;

import kr.sparta.enrollment.domain.student.exception.NotFoundException;
import kr.sparta.enrollment.domain.student.model.Student;
import kr.sparta.enrollment.domain.student.model.StudentAddRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student getStudent(@PathVariable long studentNo) {
        return studentRepository.findById(studentNo)
                .orElseThrow(() -> new NotFoundException("No student found"));
    }

    public Student addStudent(StudentAddRequest req) {
        final Student newStudent = Student.builder()
                .name(req.getName())
                .status(req.getStatus())
                .build();

        return studentRepository.save(newStudent);
    }
}
