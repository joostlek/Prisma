package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.person.Student;

public class Group {
	private String groupCode;
	private String name;
	private ArrayList<Student> students = new ArrayList<>();
	private ArrayList<Lesson> lessons;

	public Group(String groupCode, String name) {
		this.groupCode = groupCode;
		this.name = name;
		this.lessons = new ArrayList<>();
	}

	public String getGroupCode() {
		return this.groupCode;
	}

	public String getName() {
		return this.name;
	}

	public List<Student> getStudents() {
		return Collections.unmodifiableList(students);
	}

	public boolean hasStudent(Student student) {
		return this.getStudents().contains(student);
	}

	public void addStudent(Student student) {
		if (!hasStudent(student)) {
			this.students.add(student);
		}
	}

	public ArrayList<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(ArrayList<Lesson> lessons) {
		this.lessons = lessons;
	}

	public void addLesson(Lesson lesson) {
		this.lessons.add(lesson);
	}
}
