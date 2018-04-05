package responses;

import model.person.Student;

import java.util.ArrayList;

public class StudentResponse {
    private int studentId;
    private String fullName;
    private String firstName;
    private String lastName;
    private Boolean sameGroup;
    private ArrayList<LessonResponse> lessons;

    public StudentResponse(Student student) {
        this.fullName = student.getFullName();
        this.lessons = new ArrayList<>();
    }

    public StudentResponse(Student student, Boolean sameGroup) {
        this.studentId = student.getStudentId();
        this.firstName = student.getFirstName();
        this.lastName = student.getFullLastName();
        this.sameGroup = sameGroup;
    }
}
