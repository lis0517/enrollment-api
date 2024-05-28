package kr.sparta.enrollment.domain.student;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import kr.sparta.enrollment.domain.student.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students/{studentNo}")
    @Operation(summary = "수강생 정보 조회", description = "수강생의 정보를 조회하는 API입니다.")
    @ApiResponse(responseCode = "204", description = "수강생을 찾을 수 없음")
    public Student getStudent(@PathVariable long studentNo) {
        return studentService.getStudent(studentNo);
    }

    @PostMapping("/students")
    @Operation(summary = "수강생 정보 등록", description = "수강생의 정보를 등록하는 API입니다.")
    public ResponseEntity<?> addStudent(@RequestBody @Valid StudentAddRequest req) {
        studentService.addStudent(req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students")
    @Operation(summary = "수강생 목록 조회", description = "수강생 전체 목록을 조회하는 API입니다.")
    @ApiResponse(responseCode = "204", description = "수강생이 없음")
    public List<SimpleStudentDto> getStudents() {
        return studentService.getStudents();
    }

    @PutMapping("/students/{studentId}")
    @Operation(summary = "수강생 정보를 수정", description = "선택된 수강생 정보를 수정하는 API입니다.")
    @ApiResponse(responseCode = "204", description = "수강생을 찾을 수 없음")
    public ResponseEntity<?> updateStudent(@PathVariable Long studentId, @RequestBody StudentAddRequest request){
        studentService.updateStudent(studentId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}/students")
    @Operation(summary = "상태별 수강생 목록을 조회", description = "상태 별 수강생 목록을 조회하는 API입니다.")
    public ResponseEntity<StudentStatusResponse> getStudentsByStatus(@PathVariable StudentStatus status){
        StudentStatusResponse response = studentService.getStudentsByStatus(status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/students/{studentId}")
    @Operation(summary = "수강색 삭제하기", description = "수강생을 삭제하는 API입니다. 점수도 같이 삭제됩니다.")
    public ResponseEntity<?> deleteStudent(@PathVariable Long studentId){
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }
}
