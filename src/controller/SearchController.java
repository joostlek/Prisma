package controller;

import model.Group;
import model.PrIS;
import model.person.Student;
import server.Conversation;
import server.Handler;

import javax.json.*;
import java.util.ArrayList;

public class SearchController implements Handler {
    private PrIS informatieSysteem;

    public static String ROUTE_SEARCH = "/search";
    public static String SEARCH_TYPE_STUDENT = "student";
    public static String SEARCH_TYPE_CURSUS = "cursus";


    /**
     * De StudentController klasse moet alle student-gerelateerde aanvragen
     * afhandelen. Methode handle() kijkt welke URI is opgevraagd en laat
     * dan de juiste methode het werk doen. Je kunt voor elke nieuwe URI
     * een nieuwe methode schrijven.
     *
     * @param infoSys - het toegangspunt tot het domeinmodel
     */

    public SearchController(PrIS infoSys) {
        informatieSysteem = infoSys;
    }

    public void handle(Conversation conversation) {
        String response = Json.createArrayBuilder().toString();
        if (conversation.getRequestedURI().startsWith(ROUTE_SEARCH)) {
            JsonObject responseObject = (JsonObject) conversation.getRequestBodyAsJSON();
            String keywords = responseObject.getString("keywords");
            String searchType = responseObject.getString("type");

            if (searchType.equals(SEARCH_TYPE_STUDENT)) {
                response = searchStudent(keywords);
            } else if (searchType.equals(SEARCH_TYPE_CURSUS)) {
                response = searchCursus(keywords);
            }

            conversation.sendJSONMessage(response);
        }

    }

    private String searchStudent(String keywords) {
        ArrayList<Student> searchResults = informatieSysteem.searchStudents(keywords);

        JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();                        // Uiteindelijk gaat er een array...

        for (Student student : searchResults) {                                            // met daarin voor elke medestudent een JSON-object...
            JsonObjectBuilder jsonObjectBuilderStudent = Json.createObjectBuilder(); // maak het JsonObject voor een student
            jsonObjectBuilderStudent
                    .add("id", student.getStudentId())                                                                    //vul het JsonObject
                    .add("title", student.getUsername());

            lJsonArrayBuilder.add(jsonObjectBuilderStudent);                                                    //voeg het JsonObject aan het array toe
        }
        return lJsonArrayBuilder.build().toString();                                                // maak er een string van
    }

    private String searchCursus(String keywords) {
        ArrayList<Group> searchResults = informatieSysteem.searchCursus(keywords);

        JsonArrayBuilder lJsonArrayBuilder = Json.createArrayBuilder();                        // Uiteindelijk gaat er een array...

        for (Group group: searchResults) {                                            // met daarin voor elke medestudent een JSON-object...
            JsonObjectBuilder jsonObjectBuilderStudent = Json.createObjectBuilder(); // maak het JsonObject voor een student
            jsonObjectBuilderStudent
                    .add("id", group.getGroupCode())                                                                    //vul het JsonObject
                    .add("title", group.getName());

            lJsonArrayBuilder.add(jsonObjectBuilderStudent);                                                    //voeg het JsonObject aan het array toe
        }
        return lJsonArrayBuilder.build().toString();                                                // maak er een string van
    }
}
