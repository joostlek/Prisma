package model.person;

import model.Group;

public class Teacher extends Person {
	private int teacherId;
	private Group SLB;

	public Teacher(String firstName, String middleName, String lastName, String password, String username, int teacherId) {
		super(firstName, middleName, lastName, password, username);
		this.teacherId = teacherId;
	}

	public Teacher(String firstName, String middleName, String lastName, String password, String username, int teacherId, Group SLB) {
		super(firstName, middleName, lastName, password, username);
		this.teacherId = teacherId;
		this.SLB = SLB;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	public Group getSLB() {
		return SLB;
	}

	public boolean isSLB() {
		return SLB != null;
	}
}
