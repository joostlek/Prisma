package controller;

import com.google.gson.Gson;
import model.Group;
import model.PrIS;
import model.person.Student;
import responses.SearchResponse;
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
        ArrayList<SearchResponse> searchResponses = new ArrayList<>();

        for (Student student : searchResults) {                                            // met daarin voor elke medestudent een JSON-object...
            searchResponses.add(new SearchResponse(String.valueOf(student.getStudentId()), student.getUsername()));
        }

        Gson gson = new Gson();
        return gson.toJson(searchResponses);
    }

    private String searchCursus(String keywords) {
        ArrayList<Group> searchResults = informatieSysteem.searchCursus(keywords);

        ArrayList<SearchResponse> searchResponses = new ArrayList<>();

        for (Group group : searchResults) {                                            // met daarin voor elke medestudent een JSON-object...
            searchResponses.add(new SearchResponse(group.getGroupCode(), group.getName()));
        }

        Gson gson = new Gson();
        return gson.toJson(searchResponses);
    }
}
