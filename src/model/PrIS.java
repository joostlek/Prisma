package model;

import java.util.ArrayList;

import model.person.Person;
import model.person.Teacher;
import model.person.Student;

import java.io.BufferedReader;
import java.io.FileReader;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PrIS {
	private ArrayList<Teacher> teachers;
	private ArrayList<Student> students;
	private ArrayList<Group> groups;
	private ArrayList<Person> people;

	/**
	 * De constructor maakt een set met standaard-data aan. Deze data
	 * moet nog uitgebreidt worden met rooster gegevens die uit een bestand worden
	 * ingelezen, maar dat is geen onderdeel van deze demo-applicatie!
	 *
	 * De klasse PrIS (PresentieInformatieSysteem) heeft nu een meervoudige
	 * associatie met de klassen Docent, Student, Vakken en Klassen Uiteraard kan dit nog veel
	 * verder uitgebreid en aangepast worden! 
	 *
	 * De klasse fungeert min of meer als ingangspunt voor het domeinmodel. Op
	 * dit moment zijn de volgende methoden aanroepbaar:
	 *
	 * String login(String gebruikersnaam, String wachtwoord)
	 * Docent getDocent(String gebruikersnaam)
	 * Student getStudent(String gebruikersnaam)
	 * ArrayList<Student> getStudentenVanKlas(String klasCode)
	 *
	 * Methode login geeft de rol van de gebruiker die probeert in te loggen,
	 * dat kan 'student', 'docent' of 'undefined' zijn! Die informatie kan gebruikt 
	 * worden om in de Polymer-GUI te bepalen wat het volgende scherm is dat getoond 
	 * moet worden.
	 *
	 */
	public PrIS() {
		teachers = new ArrayList<>();
		students = new ArrayList<>();
		groups = new ArrayList<>();
		people = new ArrayList<>();

		fillGroup();
		fillStudents();
		fillTeachers();

//		TODO: ADD BETTER LOADING
		people.addAll(students);
		people.addAll(teachers);
	}

	//deze method is static onderdeel van PrIS omdat hij als hulp methode 
	//in veel controllers gebruikt wordt
	//een standaardDatumString heeft formaat YYYY-MM-DD
	public static Calendar standaardDatumStringToCal(String pStadaardDatumString) {
		Calendar lCal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			lCal.setTime(sdf.parse(pStadaardDatumString));
		}  catch (ParseException e) {
			e.printStackTrace();
			lCal=null;
		}
		return lCal;
	}
	//deze method is static onderdeel van PrIS omdat hij als hulp methode 
	//in veel controllers gebruikt wordt
	//een standaardDatumString heeft formaat YYYY-MM-DD
	public static String calToStandaardDatumString (Calendar pCalendar) {
		int lJaar = pCalendar.get(Calendar.YEAR);
		int lMaand= pCalendar.get(Calendar.MONTH) + 1;
		int lDag  = pCalendar.get(Calendar.DAY_OF_MONTH);

		String lMaandStr = Integer.toString(lMaand);
		if (lMaandStr.length() == 1) {
			lMaandStr = "0"+ lMaandStr;
		}
		String lDagStr = Integer.toString(lDag);
		if (lDagStr.length() == 1) {
			lDagStr = "0"+ lDagStr;
		}
		return Integer.toString(lJaar) + "-" + lMaandStr + "-" + lDagStr;
	}

	public Teacher getTeacher(String username) {
		for (Teacher teacher: this.teachers) {
			if (teacher.getUsername().equals(username)) {
				return teacher;
			}
		}
		return null;
	}

	public Group getStudentGroup(Student student) {
		for (Group group: this.groups) {
			if (group.hasStudent(student)){
				return (group);
			}
		}
		return null;
	}

	public Student getStudent(String username) {
		for (Student student: this.students) {
			if (student.getUsername().equals(username)) {
				return student;
			}
		}
		return null;
	}

	public Student getStudent(int studentId) {
		for (Student student: this.students) {
			if (student.getStudentId() == studentId) {
				return student;
			}
		}
		return null;
	}

	public String login(String username, String password) {
		for (Person person: people) {
			if (person.getUsername().equals(username)) {
				if (person.samePassword(password)) {
					if (person instanceof Teacher) {
						return "docent";
					} else if (person instanceof Student) {
						return "student";
					}
				}
				return "incorrect password";
			}
		}
		return "username not found";
	}

	private void fillTeachers() {
		String csvFile = "././CSV/docenten.csv";
		BufferedReader br = null;
		String line;
		String cvsSplitBy = ",";
		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				String[] element = line.split(cvsSplitBy);
				String username = element[0].toLowerCase();
				String firstName = element[1];
				String middleName = element[2];
				String lastName = element[3];
				this.teachers.add(new Teacher(firstName, middleName, lastName, "geheim", username, 1));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
		Student lStudent;
		for (Group group: this.groups) {
			String csvFile = "././CSV/" + group.getName() + ".csv";
			BufferedReader br = null;
			String line;
			String cvsSplitBy = ",";
			try {
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null) {
					//line = line.replace(",,", ", ,");
					// use comma as separator
					String[] element = line.split(cvsSplitBy);
					String gebruikersnaam = (element[3] + "." + element[2] + element[1] + "@student.hu.nl").toLowerCase();
					// verwijder spaties tussen  dubbele voornamen en tussen bv van der 
					gebruikersnaam = gebruikersnaam.replace(" ","");
					String lStudentNrString  = element[0];
					int lStudentNr = Integer.parseInt(lStudentNrString);
					lStudent = new Student(element[3], element[2], element[1], "geheim", gebruikersnaam, lStudentNr); //Volgorde 3-2-1 = voornaam, tussenvoegsel en achternaam
					this.students.add(lStudent);
					group.addStudent(lStudent);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}
}
