package controller;

import model.Group;
import model.PrIS;
import model.person.Student;
import server.Conversation;
import server.Handler;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.List;

public class CursusController implements Handler {
    private PrIS informatieSysteem;

    public static String ROUTE_CURSUS = "/cursus";


    /**
     * De StudentController klasse moet alle student-gerelateerde aanvragen
     * afhandelen. Methode handle() kijkt welke URI is opgevraagd en laat
     * dan de juiste methode het werk doen. Je kunt voor elke nieuwe URI
     * een nieuwe methode schrijven.
     *
     * @param infoSys - het toegangspunt tot het domeinmodel
     */

    public CursusController(PrIS infoSys) {
        informatieSysteem = infoSys;
    }

    public void handle(Conversation conversation) {
        String response = Json.createArrayBuilder().toString();
        if (conversation.getRequestedURI().startsWith(ROUTE_CURSUS)) {
            JsonObject responseObject = (JsonObject) conversation.getRequestBodyAsJSON();
            String cursus = responseObject.getString("cursus");
            List<Student> studentList = informatieSysteem.getStudentsByGroupCode(cursus);

            JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();
            for (Student student : studentList) {                                            // met daarin voor elke medestudent een JSON-object...
                JsonObjectBuilder jsonObjectBuilderStudent = Json.createObjectBuilder(); // maak het JsonObject voor een student
                jsonObjectBuilderStudent
                        .add("id", student.getStudentId())                                                                    //vul het JsonObject
                        .add("title", student.getUsername());

                lJsonArrayBuilder.add(jsonObjectBuilderStudent);                                                    //voeg het JsonObject aan het array toe
            }

            conversation.sendJSONMessage(lJsonArrayBuilder.build().toString());
        }

    }

}
