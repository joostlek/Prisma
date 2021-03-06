
package model;

import model.person.Student;
import model.person.Teacher;
import responses.LessonResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Lesson {
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private Course course;
    private Teacher teacher;
    private String room;
    private Group group;
    private ArrayList<Presention> presentions;
    private int lessonId;

    public Lesson(LocalDateTime fromTime, LocalDateTime toTime, Course course, Teacher teacher, String room, Group group, int lessonId) {
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.course = course;
        this.teacher = teacher;
        this.room = room;
        this.group = group;
        this.lessonId = lessonId;
        this.presentions = new ArrayList<>();
    }

    public LocalDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalDateTime fromTime) {
        this.fromTime = fromTime;
    }

    public LocalDateTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalDateTime toTime) {
        this.toTime = toTime;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public ArrayList<Presention> getPresentions() {
        return presentions;
    }

    public void setPresentions(ArrayList<Presention> presentions) {
        this.presentions = presentions;
    }

    public LessonResponse toLessonResponse() {
        return new LessonResponse(this);
    }

    public LessonResponse toLessonResponse(Student student) {
        return new LessonResponse(this, student);
    }

    public void addPresention(Presention presention) {
        this.presentions.add(presention);
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public boolean getPresent (Student student) {
        for (Presention presention: this.presentions) {
            if (presention.getStudent().equals(student)) {
                return presention.isPresent();
            }
        }
        return false;
    }
}
