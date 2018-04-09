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
import java.util.ArrayList;

public class StudentController implements Handler {
    private PrIS informatieSysteem;

    public static String ROUTE_PRESENT_FETCH = "/presentie/ophalen";


    /**
     * De StudentController klasse moet alle student-gerelateerde aanvragen
     * afhandelen. Methode handle() kijkt welke URI is opgevraagd en laat
     * dan de juiste methode het werk doen. Je kunt voor elke nieuwe URI
     * een nieuwe methode schrijven.
     *
     * @param infoSys - het toegangspunt tot het domeinmodel
     */

    public StudentController(PrIS infoSys) {
        informatieSysteem = infoSys;
    }

    public void handle(Conversation conversation) {
        if (conversation.getRequestedURI().startsWith(ROUTE_PRESENT_FETCH)) {
            ArrayList<LessonResponse> presentResponse = new ArrayList<>();


            JsonObject responseObject = (JsonObject) conversation.getRequestBodyAsJSON();
            int studentId = Integer.parseInt(responseObject.getString("student_id"));
            Student student = informatieSysteem.getStudent(studentId);
            Group group = informatieSysteem.getStudentGroup(student);
            System.out.println(student.getGroupId());
            for (Lesson lesson : group.getLessons()) {
                presentResponse.add(new LessonResponse(lesson));
            }

            Gson gson = new Gson();
            conversation.sendJSONMessage(gson.toJson(presentResponse));
        }

    }

}
