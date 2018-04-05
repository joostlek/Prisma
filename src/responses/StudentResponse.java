package responses;

import java.util.ArrayList;

public class StudentResponse {
    public  String naam;
    public ArrayList<LessonResponse> courses;

    public StudentResponse(String naam) {
        this.naam = naam;
        this.courses = new ArrayList<LessonResponse>();
    }
}
