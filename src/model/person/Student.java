//checked
package model.person;

public class Student extends Person {
    private int studentId;
    private String groupId;

    public Student(String firstName, String middleName, String lastName, String password, String username, int studentId) {
        super(firstName, middleName, lastName, password, username);
        this.studentId = studentId;
        this.setGroupId("");
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj) && obj instanceof Student) {
            Student s = (Student) obj;
            return this.studentId == s.studentId;
        } else {
            return false;
        }
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getStudentId() {
        return this.studentId;
    }

    @Override
    public String getEmail() {
        if (super.getMiddleName() != null) {
            return String.format("%s.%s.%s@hu.nl", this.getFirstName(), this.getMiddleName(), this.getLastName());
        }
        return String.format("%s.%s@hu.nl", this.getFirstName(), this.getLastName());
    }
}
