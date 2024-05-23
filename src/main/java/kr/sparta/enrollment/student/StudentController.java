package kr.sparta.enrollment.student;

import kr.sparta.enrollment.student.model.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class StudentController {
    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students/{studentNo}")
    public Student getStudent(@PathVariable long studentNo) {
        return studentService.getStudent(studentNo);
    }
}
