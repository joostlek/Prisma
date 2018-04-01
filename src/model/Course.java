package model;

public class Course {
    private Study study;
    private String courseCode;
    private String name;

    public Course(Study study, String courseCode, String name) {
        this.study = study;
        this.courseCode = courseCode;
        this.name = name;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
