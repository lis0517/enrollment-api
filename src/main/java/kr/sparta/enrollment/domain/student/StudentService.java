package kr.sparta.enrollment.domain.student;

import jakarta.transaction.Transactional;
import kr.sparta.enrollment.domain.enrollment.EnrollmentRepository;
import kr.sparta.enrollment.domain.enrollment.model.Course;
import kr.sparta.enrollment.domain.enrollment.model.CourseType;
import kr.sparta.enrollment.domain.score.ScoreHelper;
import kr.sparta.enrollment.domain.score.ScoreRepository;
import kr.sparta.enrollment.domain.score.model.*;
import kr.sparta.enrollment.domain.student.exception.NotFoundException;
import kr.sparta.enrollment.domain.student.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final ScoreRepository scoreRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentService(StudentRepository studentRepository,
                          ScoreRepository scoreRepository,
                          EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.scoreRepository = scoreRepository;
    }

    public Student getStudent(@PathVariable long studentNo) {
        return studentRepository.findById(studentNo)
                .orElseThrow(() -> new NotFoundException("No student found"));
    }

    public Student addStudent(StudentAddRequest req) {
        final Student newStudent = Student.builder()
                .name(req.getName())
                .status(req.getStatus())
                .build();

        return studentRepository.save(newStudent);
    }

    public List<SimpleStudentDto> getStudents() {
        final List<Student> students = studentRepository.findAll();
        final List<SimpleStudentDto> dto = new ArrayList<>(students.size());

        students.forEach(s ->
                dto.add(SimpleStudentDto.builder()
                        .id(s.getId())
                        .name(s.getName())
                        .build())
        );

        return dto;
    }

    public void updateStudent(Long studentId, StudentAddRequest request) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student not found"));

        student.updateStudent(request.getName(), request.getStatus());
        studentRepository.save(student);
    }

    public StudentStatusResponse getStudentsByStatus(StudentStatus status) {
        List<Student> studentList = studentRepository.findAllByStatus(status);

        if (studentList.isEmpty()){
            throw new NotFoundException("Student list is empty");
        }
        System.out.println(studentList.size());

        StudentStatusResponse response = new StudentStatusResponse();
        response.setStatus(status);
        response.setStudentList(studentList);
        return response;
    }

    @Transactional
    public void deleteStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(
                () -> new NotFoundException("Student not found"));

        scoreRepository.deleteByStudentId(studentId);
        enrollmentRepository.deleteByStudentId(studentId);
        studentRepository.delete(student);
    }

    public StudentAverageGradeResponse getAverageGradesByStudent(Long studentId){

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));

        List<AverageGrade> averageGrades = getAverageGrade(scoreRepository.findMandatoryScoresByStudentId(studentId));

        StudentAverageGradeResponse response = new StudentAverageGradeResponse();
        response.setId(studentId);
        response.setName(student.getName());
        response.setGradeList(averageGrades);

        return response;
    }

    public StatusAverageGradeResponse getAverageGradesByStatus(StudentStatus status) {
        List<AverageGrade> averageGrades = getAverageGrade(scoreRepository.findMandatoryScoresByStudentStatus(status));

        StatusAverageGradeResponse response = new StatusAverageGradeResponse();
        response.setStatus(status);
        response.setGradeList(averageGrades);

        return response;
    }

    private List<AverageGrade> getAverageGrade(List<Score> scores){

        Map<Course, DoubleSummaryStatistics> summaryStatisticsMap = scores.stream()
                .collect(Collectors.groupingBy(
                        score -> score.getEnrollment().getCourse(),
                        Collectors.summarizingDouble(Score::getScore)
                ));

        return summaryStatisticsMap.entrySet().stream()
                .map(entry-> new AverageGrade(
                        entry.getKey().getName(),
                        ScoreHelper.calculateMandatoryCourseGrade((int)entry.getValue().getAverage())))
                .collect(Collectors.toList());
    }
}
