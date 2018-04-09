package controller;

import com.google.gson.Gson;
import model.Group;
import model.Lesson;
import model.PrIS;
import model.person.Student;
import server.Conversation;
import server.Handler;

import javax.json.JsonObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
//        Student student = informatieSysteem.getStudent(lJsonObjectIn.getString("username"));
//        String groupCode = student.getGroupId();
//        Group group = informatieSysteem.getGroup(groupCode);

        Calendar cal = Calendar.getInstance();
    //        if (!lJsonObjectIn.isNull("weeknummer")) {
    //            cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(lJsonObjectIn.getString("weeknummer")));
    //        } else {
    //            cal.set(Calendar.WEEK_OF_YEAR, cal.getWeekYear());
    //        }

        Gson gson = new Gson();
        conversation.sendJSONMessage("[{\"datum\":{\"day\":6,\"month\":4,\"year\":2016},\"items\":[{\"naam\":\"ICT\",\"leraar\":\"jan van den bakker\"},{\"naam\":\"ENGELS\",\"leraar\":\"jan van den bakker\"}]},{\"datum\":{\"day\":6,\"month\":4,\"year\":2016},\"items\":[{\"naam\":\"ICT\",\"leraar\":\"jan van den bakker\"}]},{\"datum\":{\"day\":6,\"month\":4,\"year\":2016},\"items\":[{\"naam\":\"ICT\",\"leraar\":\"jan van den bakker\"}]},{\"datum\":{\"day\":6,\"month\":4,\"year\":2016},\"items\":[{\"naam\":\"ICT\",\"leraar\":\"jan van den bakker\"}]},{\"datum\":{\"day\":6,\"month\":4,\"year\":2016},\"items\":[{\"naam\":\"ICT\",\"leraar\":\"jan van den bakker\"}]}]");
    }
}
