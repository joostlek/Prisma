package controller;

import com.google.gson.Gson;
import model.Group;
import model.Lesson;
import model.PrIS;
import model.person.Student;
import model.person.Teacher;
import responses.LessonResponse;
import server.Conversation;
import server.Handler;

import javax.json.JsonObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

public class AgendaController implements Handler {

    public static String ROUTE_AGENDA_LADEN = "/agenda/laad";


    private PrIS informatieSysteem;


    public AgendaController(PrIS prIS) {
        this.informatieSysteem = prIS;
    }


    @Override
    public void handle(Conversation conversation) {
        if (conversation.getRequestedURI().startsWith(ROUTE_AGENDA_LADEN)) {
            load(conversation);
        }
    }


    private void load(Conversation conversation) {
        JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
        Teacher teacher = informatieSysteem.getTeacher(lJsonObjectIn.getString("username"));

        Calendar cal = Calendar.getInstance();
        Calendar friday = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2018);
        friday.set(Calendar.YEAR, 2018);
        if (lJsonObjectIn.containsKey("weeknummer")) {
            cal.set(Calendar.WEEK_OF_YEAR, lJsonObjectIn.getInt("weeknummer"));
            friday.set(Calendar.WEEK_OF_YEAR, lJsonObjectIn.getInt("weeknummer"));
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        friday.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        ArrayList<Map<String, Object>> res = new ArrayList<>();
        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");


        for (cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY); cal.get(Calendar.DAY_OF_WEEK) < Calendar.SATURDAY; cal.add(Calendar.DAY_OF_MONTH, 1)) {
            HashMap<String, Object> r = new HashMap<>();
            r.put("datum", formatter.format(cal.getTime()));
            r.put("items", new ArrayList<LessonResponse>());
            res.add(r);
        }
        System.out.println(cal.toString());
        System.out.println(friday.toString());
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        for (Group group: informatieSysteem.getGroups()) {
            for (Lesson lesson: group.getLessons()) {
                if (lesson.getTeacher() == teacher) {
                    if (cal.getTimeInMillis() / 1000 < lesson.getFromTime().atZone(ZoneId.systemDefault()).toEpochSecond() && friday.getTimeInMillis() / 1000 > lesson.getToTime().toEpochSecond(ZoneOffset.UTC)) {
                        ArrayList<LessonResponse> lessonResponses = (ArrayList<LessonResponse>) res.get(lesson.getFromTime().getDayOfWeek().getValue() - 1).get("items");
                        lessonResponses.add(lesson.toLessonResponse());
                    }
                }
            }
        }

        HashMap<String, Object> ress = new HashMap<>();
        ress.put("rooster", res);
        ress.put("weeknummer", cal.get(Calendar.WEEK_OF_YEAR));
        Gson gson = new Gson();
        conversation.sendJSONMessage(gson.toJson(ress));
    }
}
