
package model.person;

import responses.StudentResponse;

public class Student extends Person {
	private int studentId;
	private String groupId;

	public Student(String firstName, String middleName, String lastName, String password, String username, int studentId, String groupId) {
		super(firstName, middleName, lastName, password, username);
		this.studentId = studentId;
		this.groupId = groupId;
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

	public StudentResponse toStudentResponse() {
		return new StudentResponse(this, true);
	}
}
