package responses;

import model.Lesson;
import model.Presention;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LessonResponse {

    public String naam;
    public String starttijd;
    public String eindtijd;
    public String leraar;
    public String lokaal;
    public String klas;
    public ArrayList<PresentionResponse> presentionResponses;

    public LessonResponse(Lesson lesson) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        this.naam = lesson.getCourse().getName();
        this.starttijd = lesson.getFromTime().format(dtf);
        this.eindtijd = lesson.getToTime().format(dtf);
        this.leraar = lesson.getTeacher().getFullName();
        this.lokaal = lesson.getRoom();
        this.klas = lesson.getGroup().getGroupCode();
        for (Presention presention: lesson.getPresentions()) {
            this.presentionResponses.add(presention.toPresentionResponse());
        }
    }
}
