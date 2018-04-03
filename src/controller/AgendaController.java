package controller;

import model.PrIS;
import model.person.Student;
import server.Conversation;
import server.Handler;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;

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
        String group = student.getGroupId();

        ArrayList<String[]> schedule = informatieSysteem.getSchedule(group);
        JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

        for (String[] data : schedule) {
            /*
                data index
                0 = Datum
                1 = Start Time
                2 = End Time
                3 = Curses Code
                4 = Email / Name
                5 = Location
                6 = Class Code
                 */
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
            objectBuilder.add("datum", data[0]);
            objectBuilder.add("startTime", data[1]);
            objectBuilder.add("endTime", data[2]);
            objectBuilder.add("cursesCode", data[3]);
            objectBuilder.add("email", data[4]);
            objectBuilder.add("location", data[5]);
            objectBuilder.add("classCode", data[6]);

            jsonArrayBuilder.add(objectBuilder);
        }

        String JSONoutString = jsonArrayBuilder.build().toString();
        conversation.sendJSONMessage(JSONoutString);

    }
}
