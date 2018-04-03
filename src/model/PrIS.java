package model;

import model.person.Person;
import model.person.Student;
import model.person.Teacher;
import utils.CSVReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrIS {
    private ArrayList<Teacher> teachers;
    private ArrayList<Student> students;
    private ArrayList<Group> groups;
    private ArrayList<Person> people;
    private Study study;

    /**
     * De constructor maakt een set met standaard-data aan. Deze data
     * moet nog uitgebreidt worden met rooster gegevens die uit een bestand worden
     * ingelezen, maar dat is geen onderdeel van deze demo-applicatie!
     * <p>
     * De klasse PrIS (PresentieInformatieSysteem) heeft nu een meervoudige
     * associatie met de klassen Docent, Student, Vakken en Klassen Uiteraard kan dit nog veel
     * verder uitgebreid en aangepast worden!
     * <p>
     * De klasse fungeert min of meer als ingangspunt voor het domeinmodel. Op
     * dit moment zijn de volgende methoden aanroepbaar:
     * <p>
     * String login(String gebruikersnaam, String wachtwoord)
     * Docent getDocent(String gebruikersnaam)
     * Student getStudent(String gebruikersnaam)
     * ArrayList<Student> getStudentenVanKlas(String klasCode)
     * <p>
     * Methode login geeft de rol van de gebruiker die probeert in te loggen,
     * dat kan 'student', 'docent' of 'undefined' zijn! Die informatie kan gebruikt
     * worden om in de Polymer-GUI te bepalen wat het volgende scherm is dat getoond
     * moet worden.
     */
    public PrIS() {

        teachers = new ArrayList<>();
        students = new ArrayList<>();
        groups = new ArrayList<>();
        people = new ArrayList<>();

        fillGroup();
        fillStudents();
        fillTeachers();
//        study.setStudents(students);
//        study.setName("HBO-ICT");
//        study.setCourses(getCourses());

        fillSchedule();
    }

    public Teacher getTeacher(String username) {
        for (Teacher teacher : this.teachers) {
            if (teacher.getUsername().equals(username)) {
                return teacher;
            }
        }
        return null;
    }

    public Group getStudentGroup(Student student) {
        for (Group group : this.groups) {
            if (group.hasStudent(student)) {
                return (group);
            }
        }
        return null;
    }

    public Student getStudent(String username) {
        for (Student student : this.students) {
            if (student.getUsername().equals(username)) {
                return student;
            }
        }
        return null;
    }

    public Student getStudent(int studentId) {
        for (Student student : this.students) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }
        return null;
    }

    public ArrayList<Student> searchStudents(String keyword) {
        ArrayList<Student> studentSearch = new ArrayList<>();
        for (Student student : this.students) {
            if (student.getUsername().toLowerCase().contains(keyword.toLowerCase())) {
                studentSearch.add(student);
            }
        }

        return studentSearch;
    }

    public ArrayList<Group> searchCursus(String keyword) {
        ArrayList<Group> cursusSearch = new ArrayList<>();
        for (Group group : this.groups) {
            if (group.getGroupCode().contains(keyword)) {
                cursusSearch.add(group);
            }
        }
        return cursusSearch;
    }

    /**
     * @param username
     * @param password Geef een Rol terug zodat we die kunnen gebruiken in de front-end
     * @return
     */
    public String login(String username, String password) {
        for (Person person : people) {
            if (person.getUsername().equals(username)) {
                if (person.samePassword(password)) {
                    if (person instanceof Teacher) {
                        return Role.TEACHER.getRole();
                    } else if (person instanceof Student) {
                        return Role.STUDENT.getRole();
                    }
                }
            }
        }
        return Role.ERROR.getRole();
    }

    private ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        String csvFile = "./CSV/vakken.csv";
        CSVReader csvReader = new CSVReader();
        try {
            List<String[]> data = csvReader.read(csvFile);
            for (String[] element : data) {
                courses.add(new Course(study, element[0], element[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }

    private void fillTeachers() {
        String csvFile = "./CSV/docenten.csv";
        CSVReader csvReader = new CSVReader();
        try {
            List<String[]> data = csvReader.read(csvFile);
            for (String[] element : data) {
                String username = element[0].toLowerCase();
                String firstName = element[1];
                String middleName = element[2];
                String lastName = element[3];
                Teacher teacher = new Teacher(firstName, middleName, lastName, "geheim", username, this.teachers.size());
                this.teachers.add(teacher);
                this.people.add(teacher);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fillGroup() {
        Group k2 = new Group("TICT-SIE-V1B", "V1B");
        Group k3 = new Group("TICT-SIE-V1C", "V1C");
        Group k4 = new Group("TICT-SIE-V1D", "V1D");
        Group k5 = new Group("TICT-SIE-V1E", "V1E");
        Group k6 = new Group("TICT-SIE-V1F", "V1F");

        //pKlassen.add(k1);
        this.groups.add(k2);
        this.groups.add(k3);
        this.groups.add(k4);
        this.groups.add(k5);
        this.groups.add(k6);
    }

    private void fillStudents() {
        for (Group group : this.groups) {
            String csvFile = "./CSV/" + group.getName() + ".csv";
            CSVReader csvReader = new CSVReader();
            try {
                List<String[]> data = csvReader.read(csvFile);
                for (String[] element : data) {
                    String gebruikersnaam = (element[3] + "." + element[2] + element[1] + "@student.hu.nl").toLowerCase();
                    gebruikersnaam = gebruikersnaam.replace(" ", "");
                    Student lStudent = new Student(
                            element[3],
                            element[2],
                            element[1],
                            "geheim",
                            gebruikersnaam,
                            Integer.parseInt(element[0])
                    );
                    this.people.add(lStudent);
                    this.students.add(lStudent);
                    group.addStudent(lStudent);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void fillSchedule() {
        String csvFile = "./CSV/rooster.csv";
        CSVReader csvReader = new CSVReader();
        try {
            List<String[]> data = csvReader.read(csvFile);
            for (String[] element : data) {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
