package kr.sparta.enrollment.domain.score;

import kr.sparta.enrollment.domain.score.model.Grade;

public class ScoreHelper {

    public static Grade calculateMandatoryCourseGrade(int score){
        if (score >= 95 && score <= 100) {
            return Grade.A;
        } else if (score >= 90 && score <= 94) {
            return Grade.B;
        } else if (score >= 80 && score <= 89) {
            return Grade.C;
        } else if (score >= 70 && score <= 79) {
            return Grade.D;
        } else if (score >= 60 && score <= 69) {
            return Grade.F;
        } else {
            return Grade.N;
        }
    }

    public static Grade calculateOptionalCourseGrade(int score) {
        if (score >= 90 && score <= 100) {
            return Grade.A;
        } else if (score >= 80 && score <= 89) {
            return Grade.B;
        } else if (score >= 70 && score <= 79) {
            return Grade.C;
        } else if (score >= 60 && score <= 69) {
            return Grade.D;
        } else if (score >= 50 && score <= 59) {
            return Grade.F;
        } else {
            return Grade.N;
        }
    }
}
