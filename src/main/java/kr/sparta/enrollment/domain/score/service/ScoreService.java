package kr.sparta.enrollment.domain.score.service;

import jakarta.transaction.Transactional;
import kr.sparta.enrollment.domain.enrollment.EnrollmentRepository;
import kr.sparta.enrollment.domain.enrollment.model.Enrollment;
import kr.sparta.enrollment.domain.score.model.Score;
import kr.sparta.enrollment.domain.score.model.ScoreRequestDto;
import kr.sparta.enrollment.domain.score.model.ScoreSimpleRequest;
import kr.sparta.enrollment.domain.score.repository.ScoreRepository;
import kr.sparta.enrollment.domain.student.StudentRepository;
import kr.sparta.enrollment.domain.student.exception.NotFoundException;
import kr.sparta.enrollment.domain.student.model.Student;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {
    private final ScoreRepository scoreRepository;
    private final StudentRepository studentRepository;

    public ScoreService(ScoreRepository scoreRepository, StudentRepository studentRepository) {
        this.scoreRepository = scoreRepository;
        this.studentRepository = studentRepository;
    }

    public void addCourseScore(Long studentId, Long courseId, ScoreRequestDto scoreRequestDto) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        Enrollment enrollment = student.getCourseList().stream()
                .filter(x->x.getId() == courseId)
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

    public void updateCourseScore(Long studentId, Long courseId, int round, ScoreSimpleRequest scoreSimpleRequest) {
        Score score = scoreRepository.findByStudentIdAndEnrollmentIdAndRound(studentId, courseId, round)
                .orElseThrow(()-> new NotFoundException("해당되는 회차 점수를 찾을 수 없습니다."));

        score.updateScore(scoreSimpleRequest.getScore());
        scoreRepository.save(score);
    }
}
