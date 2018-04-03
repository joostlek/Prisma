package controller;

import javax.json.JsonObject;
import com.google.gson.Gson;
import model.PrIS;
import server.Conversation;
import server.Handler;
import java.util.HashMap;
import java.util.Map;

class LoginController implements Handler {
    private PrIS prisma;

    public static String ROUTE_LOGIN = "/login";

    /**
     * De LoginController klasse moet alle algemene aanvragen afhandelen.
     * Methode handle() kijkt welke URI is opgevraagd en laat dan de juiste
     * methode het werk doen. Je kunt voor elke nieuwe URI een nieuwe methode
     * schrijven.
     *
     * @param infoSys - het toegangspunt tot het domeinmodel
     */
    public LoginController(PrIS infoSys) {
        prisma = infoSys;
    }

    public void handle(Conversation conversation) {
        if (conversation.getRequestedURI().startsWith(ROUTE_LOGIN)) {
            login(conversation);
        }
    }

    /**
     * Deze methode haalt eerst de opgestuurde JSON-data op. Daarna worden
     * de benodigde gegevens uit het domeinmodel gehaald. Deze gegevens worden
     * dan weer omgezet naar JSON en teruggestuurd naar de Polymer-GUI!
     *
     * @param conversation - alle informatie over het request
     */
    private void login(Conversation conversation) {
        JsonObject lJsonObjIn = (JsonObject) conversation.getRequestBodyAsJSON();

        String lGebruikersnaam = lJsonObjIn.getString("username");
        String lWachtwoord = lJsonObjIn.getString("password");

        Gson gson = new Gson();
        Map<String, Object> res = new HashMap<>();
        res.put("rol", prisma.login(lGebruikersnaam, lWachtwoord));
        conversation.sendJSONMessage(gson.toJson(res));
    }
}