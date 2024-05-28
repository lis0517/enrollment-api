package kr.sparta.enrollment.domain.student;

import kr.sparta.enrollment.domain.enrollment.model.Enrollment;
import kr.sparta.enrollment.domain.score.model.Score;
import kr.sparta.enrollment.domain.student.exception.NotFoundException;
import kr.sparta.enrollment.domain.student.model.SimpleStudentDto;
import kr.sparta.enrollment.domain.student.model.Student;
import kr.sparta.enrollment.domain.student.model.StudentAddRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

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

    public List<SimpleStudentDto> getStudents() {
        final List<Student> students = studentRepository.findAll();
        final List<SimpleStudentDto> dto = new ArrayList<>(students.size());

        students.forEach(s ->
                dto.add(SimpleStudentDto.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .build())
        );

        return dto;
    }
}
