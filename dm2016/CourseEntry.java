package dm2016;

import java.util.stream.Stream;

class CourseEntry {

    private String yearMonth;
    private String code;
    private String name;

    private double credits;
    private int grade;

    public CourseEntry(String yearMonth, String code, String name, double credits, int grade) {
        this.yearMonth = yearMonth;
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.grade = grade;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public int getGrade() {
        return this.grade;
    }

}
