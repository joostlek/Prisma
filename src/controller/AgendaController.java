package controller;

import model.PrIS;
import model.person.Student;
import server.Conversation;
import server.Handler;

import javax.json.JsonObject;

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
        String Group = student.getGroupId();

    }
}
