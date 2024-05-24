package kr.sparta.enrollment.domain.enrollment.model;

import lombok.Getter;

import java.util.Set;

@Getter
public class CreateEnrollmentRequest {
    private Set<Course> mandatoryList;
    private Set<Course> optionalList;
}
