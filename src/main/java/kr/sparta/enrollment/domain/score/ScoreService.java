package kr.sparta.enrollment.domain.score;

import kr.sparta.enrollment.domain.enrollment.EnrollmentRepository;
import kr.sparta.enrollment.domain.enrollment.model.Enrollment;
import kr.sparta.enrollment.domain.score.model.*;
import kr.sparta.enrollment.domain.student.StudentRepository;
import kr.sparta.enrollment.domain.student.exception.NotFoundException;
import kr.sparta.enrollment.domain.student.model.Student;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;

    public ScoreService(
            ScoreRepository scoreRepository,
            StudentRepository studentRepository,
            EnrollmentRepository enrollmentRepository) {
        this.scoreRepository = scoreRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public void addCourseScore(Long studentId, Long enrollmentId, ScoreRequestDto scoreRequestDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        Enrollment enrollment = student.getCourseList().stream()
                .filter(x->x.getId() == enrollmentId)
                .findFirst().orElseThrow(()-> new NotFoundException(("Enrollment not found")));

        if (scoreRepository.existsByEnrollmentAndRound(enrollment, scoreRequestDto.getRound())) {
            throw new DuplicateKeyException("Score already exists for the given enrollment and round");
        }

        Score score = Score.builder()
                .student(student)
                .enrollment(enrollment)
                .course(enrollment.getCourse())
                .round(scoreRequestDto.getRound())
                .score(scoreRequestDto.getScore())
                .build();
        score.calculateGrade();

        scoreRepository.save(score);
    }

    public void updateCourseScore(
            Long studentId,
            Long enrollmentId,
            int round,
            ScoreSimpleRequest scoreSimpleRequest) {
        Score score = scoreRepository.findByStudentIdAndEnrollmentIdAndRound(studentId, enrollmentId, round)
                .orElseThrow(()-> new NotFoundException("Score not found"));

        score.updateScore(scoreSimpleRequest.getScore());
        scoreRepository.save(score);
    }

    public StudentGradeResponse getGradesByStudentIdAndCourse(
            Long studentId,
            Long enrollmentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(() -> new NotFoundException("Enroll not found"));

        List<Score> scores = scoreRepository.findByStudentIdAndEnrollmentId(studentId, enrollmentId);

        if (scores.isEmpty()){
            throw new NotFoundException("Scores not found");
        }

        Map<Integer, Grade> grades = new HashMap<>();
        for(Score score : scores){
            grades.put(score.getRound(), score.getGrade());
        }

        StudentGradeResponse response  = new StudentGradeResponse();
        response.setId(studentId);
        response.setName(student.getName());
        response.setCourseNo(enrollmentId);
        response.setCourseName(enrollment.getCourse().toString());
        response.setGradeList(grades);

        return response;
    }
}
