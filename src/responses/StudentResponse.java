
package responses;

import model.person.Student;

import java.util.ArrayList;

public class StudentResponse {
    private int studentId;
    private String userName;
    private String fullName;
    private String firstName;
    private String lastName;
    private Boolean sameGroup;
    private ArrayList<LessonResponse> lessons;
    private int hoursPresent;
    private int hoursAbsent;

    public StudentResponse(Student student) {
        this.fullName = student.getFullName();
        this.userName = student.getUsername();
        this.lessons = new ArrayList<>();
    }

    public StudentResponse(Student student, Boolean sameGroup) {
        this.studentId = student.getStudentId();
        this.fullName = student.getFullName();
        this.firstName = student.getFirstName();
        this.lastName = student.getFullLastName();
        this.sameGroup = sameGroup;
    }

    public void calculatePresent() {
        this.hoursPresent = 90;
        this.hoursAbsent = 10;
    }

    public int getHoursPresent() {
        return hoursPresent;
    }

    public void setHoursPresent(int hoursPresent) {
        this.hoursPresent = hoursPresent;
    }

    public int getHoursAbsent() {
        return hoursAbsent;
    }

    public void setHoursAbsent(int hoursAbsent) {
        this.hoursAbsent = hoursAbsent;
    }

    public void setLessons(ArrayList<LessonResponse> lessons) {
        this.lessons = lessons;
    }
}
