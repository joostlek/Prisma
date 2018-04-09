package model.person;

public class Teacher extends Person {
	private int teacherId;

	public Teacher(String firstName, String middleName, String lastName, String password, String username, int teacherId) {
		super(firstName, middleName, lastName, password, username);
		this.teacherId = teacherId;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
}
