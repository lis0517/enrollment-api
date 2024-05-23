package kr.sparta.enrollment.domain.enrollment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import kr.sparta.enrollment.domain.student.model.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@RestController
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/students/{studentNo}/enrollments")
    public ResponseEntity<?> addEnrollments(
            @PathVariable @NotNull(message = "수강생 번호를 입력해주세요") Long studentNo,

            @RequestParam @Valid @NotNull(message = "필수 과목을 입력해주세요.")
            @Size(min = 3, message = "필수 과목은 최소 3개 이상 입력 해주세요") Set<Course> mandatoryList,

            @RequestParam @Valid @NotNull(message = "선택 과목을 입력해주세요.")
            @Size(min = 2, message = "선택 과목은 최소 2개 이상 입력 해주세요") Set<Course> optionalList) {

        final List<Course> joinedList = Stream.of(mandatoryList.stream(), optionalList.stream())
                .flatMap(c -> c)
                .toList();

        enrollmentService.addEnrollments(studentNo, joinedList);
        return ResponseEntity.ok().build();
    }
}