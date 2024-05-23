package kr.sparta.enrollment.student;

import kr.sparta.enrollment.student.exception.NotFoundException;
import kr.sparta.enrollment.student.model.Student;
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
}
