package kr.sparta.enrollment.domain.student;

import jakarta.validation.Valid;
import kr.sparta.enrollment.domain.student.model.Student;
import kr.sparta.enrollment.domain.student.model.StudentAddRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students/{studentNo}")
    public Student getStudent(@PathVariable long studentNo) {
        return studentService.getStudent(studentNo);
    }

    @PostMapping("/students")
    public ResponseEntity<?> addStudent(@RequestBody @Valid StudentAddRequest req) {
        studentService.addStudent(req);
        return ResponseEntity.ok().build();
    }
}
