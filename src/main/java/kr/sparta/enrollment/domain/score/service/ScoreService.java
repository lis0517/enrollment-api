package kr.sparta.enrollment.domain.score.service;

import jakarta.transaction.Transactional;
import kr.sparta.enrollment.domain.enrollment.EnrollmentRepository;
import kr.sparta.enrollment.domain.enrollment.model.Enrollment;
import kr.sparta.enrollment.domain.score.model.Score;
import kr.sparta.enrollment.domain.score.model.ScoreRequestDto;
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

    public void addCourseScore(Long studentNo, Long courseNo, ScoreRequestDto scoreRequestDto) {
        Student student = studentRepository.findById(studentNo)
                .orElseThrow(() -> new NotFoundException(" for the given student and course"));

        Enrollment enrollment = student.getCourseList().stream()
                .filter(x->x.getId() == courseNo)
                .findFirst().orElseThrow(()-> new NotFoundException(("Enrollment not found")));

        if (scoreRepository.existsByEnrollmentAndRound(enrollment, scoreRequestDto.getRound())) {
            throw new DuplicateKeyException("Score already exists for the given enrollment and round");
        }

        System.out.println(enrollment.getCourse() + ", " + enrollment.getCourseType());

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
}
