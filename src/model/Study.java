package model;

import model.person.Decaan;
import model.person.Logistic;
import model.person.Student;

import java.util.ArrayList;

public class Study {
    private String name;
    private ArrayList<Course> courses;
    private ArrayList<Student> students;
    private ArrayList<Logistic> logistics;
    private Decaan decaan;

    public Study(String name, ArrayList<Course> courses, ArrayList<Student> students, ArrayList<Logistic> logistics, Decaan decaan) {
        this.name = name;
        this.courses = courses;
        this.students = students;
        this.logistics = logistics;
        this.decaan = decaan;
    }

    public Study(String name, ArrayList<Course> courses, ArrayList<Student> students, ArrayList<Logistic> logistics) {
        this.name = name;
        this.courses = courses;
        this.students = students;
        this.logistics = logistics;
    }

    public Study(String name) {
        new Study(name, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    public ArrayList<Logistic> getLogistics() {
        return logistics;
    }

    public void setLogistics(ArrayList<Logistic> logistics) {
        this.logistics = logistics;
    }

    public Decaan getDecaan() {
        return decaan;
    }

    public void setDecaan(Decaan decaan) {
        this.decaan = decaan;
    }
}
