package controller;

import com.google.gson.Gson;
import model.Group;
import model.Lesson;
import model.PrIS;
import model.person.Student;
import responses.LessonResponse;
import responses.StudentResponse;
import server.Conversation;
import server.Handler;

import javax.json.JsonObject;
import java.util.ArrayList;

public class StudentController implements Handler {
    private PrIS informatieSysteem;

    public static String ROUTE_PRESENT_FETCH = "/presentie/ophalen";
    public static String ROUTE_STUDENT_INFO = "/student/info";


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
            handlePresent(conversation);
        } else if (conversation.getRequestedURI().startsWith(ROUTE_STUDENT_INFO)) {
            handleStudentInfo(conversation);
        }
    }

    public void handlePresent(Conversation conversation) {
        ArrayList<LessonResponse> presentResponse = new ArrayList<>();


        JsonObject responseObject = (JsonObject) conversation.getRequestBodyAsJSON();
        String studentUsername = responseObject.getString("username");

        Student student = informatieSysteem.getStudent(studentUsername);
        Group group = informatieSysteem.getStudentGroup(student);
        System.out.println(student.getGroupId());
        for (Lesson lesson : group.getLessons()) {
            presentResponse.add(new LessonResponse(lesson));
        }

        Gson gson = new Gson();
        conversation.sendJSONMessage(gson.toJson(presentResponse));
    }

    public void handleStudentInfo(Conversation conversation) {
        JsonObject responseObject = (JsonObject) conversation.getRequestBodyAsJSON();
        String studentUsername = responseObject.getString("username");

        Student student = informatieSysteem.getStudent(studentUsername);

        StudentResponse studentResponse = new StudentResponse(student);
        studentResponse.setHoursPresent(informatieSysteem.getStats(student));
        studentResponse.setHoursAbsent(informatieSysteem.getAbsent(student));

        Gson gson = new Gson();
        conversation.sendJSONMessage(gson.toJson(studentResponse));
    }
}
