package kr.sparta.enrollment.domain.score;

import io.swagger.v3.oas.annotations.Operation;
import kr.sparta.enrollment.domain.score.model.ScoreRequestDto;
import kr.sparta.enrollment.domain.score.model.ScoreSimpleRequest;
import kr.sparta.enrollment.domain.score.model.StudentGradeResponse;
import kr.sparta.enrollment.domain.student.exception.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/students/{studentId}/courses/{courseId}/rounds/{round}")
    @Operation(summary = "수강생 과목 점수 수정", description = "수강생의 특정 과목 회차 점수를 수정할 수있는 API입니다.")
    public ResponseEntity<?> updateCourseScore(@PathVariable Long studentId, @PathVariable Long courseId, @PathVariable int round, @RequestBody ScoreSimpleRequest scoreSimpleRequest){
        scoreService.updateCourseScore(studentId, courseId, round, scoreSimpleRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/students/{studentId}/courses/{courseId}/grades")
    @Operation(summary = "수강생의 특정 과목 회차별 등급을 조회", description = "수강생의 특정 과목 회차별 등급을 조회할 수있는 API입니다.")
    public ResponseEntity<StudentGradeResponse> getGradesByStudentIdAndCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId ){
        try{
            StudentGradeResponse response = scoreService.getGradesByStudentIdAndCourse(studentId, courseId);
            return ResponseEntity.ok(response);
        }catch (NotFoundException e){
            return ResponseEntity.noContent().build();
        }
    }
}
