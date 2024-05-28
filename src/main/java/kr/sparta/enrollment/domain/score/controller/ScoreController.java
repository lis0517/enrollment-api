package kr.sparta.enrollment.domain.score.controller;

import io.swagger.v3.oas.annotations.Operation;
import kr.sparta.enrollment.domain.score.model.ScoreRequestDto;
import kr.sparta.enrollment.domain.score.service.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ScoreController {

    private final ScoreService scoreService;

    public ScoreController(ScoreService scoreService){
        this.scoreService = scoreService;
    }

    @PostMapping("/students/{studentNo}/courses/{courseNo}")
    @Operation(summary = "수강생의 과목별 시험 회차 및 점수 등록", description = "수강생의 과목별 시험 회차 및 점수 등록 API입니다.")
    public ResponseEntity<?> addCourseScore(@PathVariable Long studentNo, @PathVariable Long courseNo, @RequestBody ScoreRequestDto scoreRequestDto){
        scoreService.addCourseScore(studentNo, courseNo, scoreRequestDto);
        return ResponseEntity.ok().build();
    }

}
