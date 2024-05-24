package kr.sparta.enrollment.domain.enrollment;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.sparta.enrollment.domain.enrollment.model.Course;
import kr.sparta.enrollment.domain.enrollment.model.CreateEnrollmentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Stream;

@RestController
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/students/{studentNo}/enrollments")
    @ApiResponse(responseCode = "416", description = "필수 과목 최소 3개 또는 선택 과목 최소 2개 조건을 충족하지 않음")
    public ResponseEntity<?> addEnrollments(
            @PathVariable @NotNull(message = "수강생 번호를 입력해주세요") Long studentNo,
            @Valid @RequestBody CreateEnrollmentRequest request) {
        valid(request);

        final List<Course> joinedList = Stream.of(request.getMandatoryList().stream(), request.getOptionalList().stream())
                .flatMap(c -> c)
                .toList();

        enrollmentService.addEnrollments(studentNo, joinedList);
        return ResponseEntity.ok().build();
    }

    private void valid(CreateEnrollmentRequest request) {
        if (request.getMandatoryList().size() < 3) {
            throw new IllegalArgumentException();
        }

        if (request.getOptionalList().size() < 2) {
            throw new IllegalArgumentException();
        }
    }
}