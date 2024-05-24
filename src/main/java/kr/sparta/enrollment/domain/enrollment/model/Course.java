package kr.sparta.enrollment.domain.enrollment.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public enum Course {
    JAVA            ("Java",             CourseType.MANDATORY),
    OOP             ("객체지향",            CourseType.MANDATORY),
    SPRING          ("Spring",          CourseType.MANDATORY),
    JPA             ("JPA",             CourseType.MANDATORY),
    MYSQL           ("MySQL",           CourseType.MANDATORY),
    DESIGN_PATTERN  ("디자인 패턴",          CourseType.OPTIONAL),
    SPRING_SECURITY ("Spring Security", CourseType.OPTIONAL),
    REDIS           ("Redis",           CourseType.OPTIONAL),
    MONGODB         ("MongoDB",         CourseType.OPTIONAL)
    ;

    private static final Map<String, Course> cacheMap = Arrays.stream(values()).collect(Collectors.toMap(key -> key.name(), key -> key));

    private final String name;
    private final CourseType type;

    Course(String name, CourseType type) {
        this.name = name;
        this.type = type;
    }

    @JsonValue
    public String getName() {
        return name;
    }

    @JsonCreator
    public static Course fromValue(String value) {
        return Optional.of(cacheMap.get(value.toUpperCase()))
                .orElseThrow(() -> new IllegalAccessError("Unkown course type: " + value));
    }
}
