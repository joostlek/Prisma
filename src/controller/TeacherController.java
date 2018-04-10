package controller;

import com.google.gson.Gson;
import model.Lesson;
import model.PrIS;
import model.Presention;
import model.person.Student;
import server.Conversation;
import server.Handler;

import javax.json.JsonObject;

public class TeacherController implements Handler {
    private PrIS informatieSysteem;

    public static String ROUTE_PRESENTION = "/presentie";
    public static String ROUTE_PRESENTION_SAVE = "/presentie/opslaan";

    public TeacherController(PrIS informatieSysteem) {
        this.informatieSysteem = informatieSysteem;
    }
    @Override
    public void handle(Conversation conversation) {
        if (conversation.getRequestedURI().startsWith(ROUTE_PRESENTION_SAVE)) {
            handleSave(conversation);
        } else if (conversation.getRequestedURI().startsWith(ROUTE_PRESENTION)) {
            handlePresent(conversation);
        }
    }

    public void handlePresent(Conversation conversation) {
        JsonObject responseObject = (JsonObject) conversation.getRequestBodyAsJSON();
        Lesson lesson = informatieSysteem.getLesson(responseObject.getInt("lessonId"));
        if (lesson.getPresentions().size() == 0) {
            for (Student student: lesson.getGroup().getStudents()) {
                lesson.addPresention(new Presention(student, lesson, false));
            }
        }
        Gson gson = new Gson();
        conversation.sendJSONMessage(gson.toJson(lesson.toLessonResponse()));
    }

    public void handleSave(Conversation conversation) {
        JsonObject responseObject = (JsonObject) conversation.getRequestBodyAsJSON();
        Lesson lesson = informatieSysteem.getLesson(responseObject.getInt("lessonId"));

        JsonObject presenties = responseObject.getJsonObject("presenties");
        for (Presention presention: lesson.getPresentions()) {
            presention.setPresent(presenties.getBoolean(String.valueOf(presention.getStudent().getStudentId())));
        }
    }
}