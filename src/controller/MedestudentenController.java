package controller;

import java.util.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import com.google.gson.Gson;
import model.PrIS;
import model.Group;
import model.person.Student;
import responses.StudentResponse;
import server.Conversation;
import server.Handler;

public class MedestudentenController implements Handler {
	private PrIS informatieSysteem;

	public static String ROUTE_MEDESTUDENT_OPHALEN = "/student/medestudenten/ophalen";
	/**
	 * De StudentController klasse moet alle student-gerelateerde aanvragen
	 * afhandelen. Methode handle() kijkt welke URI is opgevraagd en laat
	 * dan de juiste methode het werk doen. Je kunt voor elke nieuwe URI
	 * een nieuwe methode schrijven.
	 *
	 * @param infoSys - het toegangspunt tot het domeinmodel
	 */

	public MedestudentenController(PrIS infoSys) {
		informatieSysteem = infoSys;
	}

	public void handle(Conversation conversation) {
		if (conversation.getRequestedURI().startsWith(ROUTE_MEDESTUDENT_OPHALEN)) {
			ophalen(conversation);
		} else {
			opslaan(conversation);
		}
	}

	/**
	 * Deze methode haalt eerst de opgestuurde JSON-data op. Daarna worden
	 * de benodigde gegevens uit het domeinmodel gehaald. Deze gegevens worden
	 * dan weer omgezet naar JSON en teruggestuurd naar de Polymer-GUI!
	 *
	 * @param conversation - alle informatie over het request
	 */
	private void ophalen(Conversation conversation) {
		JsonObject jsonRequest = (JsonObject) conversation.getRequestBodyAsJSON();
		String username = jsonRequest.getString("username");
		Student student = informatieSysteem.getStudent(username);
		Group klas = informatieSysteem.getStudentGroup(student);
        ArrayList<StudentResponse> res = new ArrayList<>();
        for (Student medeStudent: klas.getStudents()) {
            if (medeStudent != student) {
            	StudentResponse response = new StudentResponse(medeStudent, (!student.getGroupId().equals("") && medeStudent.getGroupId().equals(student.getGroupId())));
                res.add(response);
            }
        }
        Gson gson = new Gson();
		conversation.sendJSONMessage(gson.toJson(res));
	}

	/**
	 * Deze methode haalt eerst de opgestuurde JSON-data op. Op basis van deze gegevens 
	 * het domeinmodel gewijzigd. Een eventuele errorcode wordt tenslotte
	 * weer (als JSON) teruggestuurd naar de Polymer-GUI!
	 *
	 * @param conversation - alle informatie over het request
	 */
	private void opslaan(Conversation conversation) {
		JsonObject lJsonObjectIn = (JsonObject) conversation.getRequestBodyAsJSON();
		String lGebruikersnaam = lJsonObjectIn.getString("username");
		Student lStudent = informatieSysteem.getStudent(lGebruikersnaam);

		//Het lJsonObjectIn bevat niet enkel strings maar ook een heel (Json) array van Json-objecten.
		// in dat Json-object zijn telkens het studentnummer en een indicatie of de student 
		// tot het zelfde team hoort opgenomen.

		//Het Json-array heeft als naam: "groupMembers"
		JsonArray lGroepMembers_jArray = lJsonObjectIn.getJsonArray("groupMembers");
		if (lGroepMembers_jArray != null) {
			// bepaal op basis van de huidige tijd een unieke string
			Calendar lCal = Calendar.getInstance();
			long lMilliSeconds = lCal.getTimeInMillis();
			String lGroepId = String.valueOf(lMilliSeconds);

			lStudent.setGroupId(lGroepId);
			for (int i=0;i<lGroepMembers_jArray.size();i++){
				JsonObject lGroepMember_jsonObj = lGroepMembers_jArray.getJsonObject(i );
				int lStudentNummer = lGroepMember_jsonObj.getInt("id");
				boolean lZelfdeGroep = lGroepMember_jsonObj.getBoolean("sameGroup");
				if (lZelfdeGroep) {
					Student lGroepStudent = informatieSysteem.getStudent(lStudentNummer);
					lGroepStudent.setGroupId(lGroepId);
				}
			}
		}

		JsonObjectBuilder lJob =	Json.createObjectBuilder();
		lJob.add("errorcode", 0);
		//nothing to return use only errorcode to signal: ready!
		String lJsonOutStr = lJob.build().toString();
		conversation.sendJSONMessage(lJsonOutStr);					// terug naar de Polymer-GUI!
	}
}
