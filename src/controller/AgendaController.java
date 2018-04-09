package controller;

import com.google.gson.Gson;
import model.Group;
import model.Lesson;
import model.PrIS;
import model.person.Student;
import responses.LessonResponse;
import server.Conversation;
import server.Handler;

import javax.json.JsonObject;
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
        Student student = informatieSysteem.getStudent(lJsonObjectIn.getString("username"));
        String groupCode = student.getGroupId();
        Group group = informatieSysteem.getGroup(groupCode);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2018);
        if (lJsonObjectIn.containsKey("weeknummer")) {
            cal.set(Calendar.WEEK_OF_YEAR, lJsonObjectIn.getInt("weeknummer"));
        } else {
            cal.set(Calendar.WEEK_OF_YEAR, cal.get(Calendar.WEEK_OF_YEAR));
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Calendar friday = (Calendar) cal.clone();
        ArrayList<Map<String, Object>> res = new ArrayList<>();
        for (cal.get(Calendar.DAY_OF_WEEK); cal.get(Calendar.DAY_OF_WEEK) < Calendar.SATURDAY; cal.add(Calendar.DAY_OF_WEEK, 1)) {
            HashMap<String, Object> r = new HashMap<>();
            HashMap<String, Object> date = new HashMap<>();
            date.put("day", cal.get(Calendar.DAY_OF_MONTH));
            date.put("month", cal.get(Calendar.MONTH));
            date.put("year", cal.get(Calendar.YEAR));
            r.put("datum", date);
            r.put("items", new ArrayList<LessonResponse>());
            res.add(r);
        }
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        System.out.println(groupCode);
        for (Lesson lesson: group.getLessons()) {
            System.out.println(lesson.getTeacher().getTeacherId());
            System.out.println(cal.getTimeInMillis() / 1000);
            System.out.println(lesson.getFromTime().atZone(ZoneId.systemDefault()).toEpochSecond());
            if (cal.getTimeInMillis() / 1000 < lesson.getFromTime().atZone(ZoneId.systemDefault()).toEpochSecond() && friday.getTimeInMillis() / 1000 < lesson.getToTime().toEpochSecond(ZoneOffset.UTC)) {
                ArrayList<LessonResponse> lessonResponses = (ArrayList<LessonResponse>) res.get(cal.get(Calendar.DAY_OF_WEEK) - 1).get("items");
                lessonResponses.add(lesson.toLessonResponse());
            }
        }
        HashMap<String, Object> ress = new HashMap<>();
        ress.put("rooster", res);
        ress.put("weeknummer", cal.get(Calendar.WEEK_OF_YEAR));
        Gson gson = new Gson();
        conversation.sendJSONMessage(gson.toJson(ress));
    }
}
