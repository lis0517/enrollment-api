package kr.sparta.enrollment.domain.score.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScoreRequestDto {
    @NotBlank(message = "회차를 입력해주세요.")
    private int round;
    @NotBlank(message = "점수를 입력해주세요.")
    private int score;
}
