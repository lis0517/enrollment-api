package kr.sparta.enrollment.domain.student.model;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class StudentAddRequest {
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @Nullable
    private StudentStatus status;
}
