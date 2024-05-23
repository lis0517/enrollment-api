package kr.sparta.enrollment.student.model;

public enum Course {
    JAVA            ("Java",             CourseType.MANDATORY)
    ,OOP             ("객체지향",            CourseType.MANDATORY)
    ,SPRING          ("Spring",          CourseType.MANDATORY)
    ,JPA             ("JPA",             CourseType.MANDATORY)
    ,MYSQL           ("MySQL",           CourseType.MANDATORY)
    ,DESIGN_PATTERN  ("디자인 패턴",          CourseType.OPTIONAL)
    ,SPRING_SECURITY ("Spring Security", CourseType.OPTIONAL)
    ,REDIS           ("Redis",           CourseType.OPTIONAL)
    ,MONGODB         ("MongoDB",         CourseType.OPTIONAL)
    ;

    private final String name;
    private final CourseType type;

    Course(String name, CourseType type) {
        this.name = name;
        this.type = type;
    }
}
