package model;

import model.person.Student;
import responses.PresentionResponse;

public class Presention {
    private Student student;
    private Lesson lesson;
    private boolean present;

    public Presention(Student student, Lesson lesson, boolean present) {
        this.student = student;
        this.lesson = lesson;
        this.present = present;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public PresentionResponse toPresentionResponse() {
        return new PresentionResponse(this);
    }
}
