package controller;

import model.PrIS;
import model.person.Student;
import server.JSONFileServer;

import java.io.File;

public class Application {

    /**
     * Deze klasse is het startpunt voor de applicatie. Hierin maak je een server
     * op een bepaalde poort (bijv. 8888). Daarna is er een PrIS-object gemaakt. Dit
     * object fungeert als toegangspunt van het domeinmodel. Hiervandaan kan alle
     * informatie bereikt worden.
     * <p>
     * Om het domeinmodel en de Polymer-GUI aan elkaar te koppelen zijn diverse controllers
     * gemaakt. Er zijn meerdere controllers om het overzichtelijk te houden. Je mag zoveel
     * controller-klassen maken als je nodig denkt te hebben. Elke controller krijgt een
     * koppeling naar het PrIS-object om benodigde informatie op te halen.
     * <p>
     * Je moet wel elke URL die vanaf Polymer aangeroepen kan worden registreren! Dat is
     * hieronder gedaan voor een drietal URLs. Je geeft daarbij ook aan welke controller
     * de URL moet afhandelen.
     * <p>
     * Als je alle URLs hebt geregistreerd kun je de server starten en de applicatie in de
     * browser opvragen! Zie ook de controller-klassen voor een voorbeeld!
     */
    public static void main(String[] args) {
        JSONFileServer server = new JSONFileServer(new File("webapp/app"), 8080);

        PrIS infoSysteem = new PrIS();

        SysteemDatumController systeemDatumController = new SysteemDatumController(infoSysteem);
        MedestudentenController medestudentenController = new MedestudentenController(infoSysteem);
        AgendaController agendaController = new AgendaController(infoSysteem);
        SearchController searchController = new SearchController(infoSysteem);
        StudentController studentController = new StudentController(infoSysteem);
        CursusController cursusController = new CursusController(infoSysteem);


        // @route /login
        LoginController loginController = new LoginController(infoSysteem);
        server.registerHandler(LoginController.ROUTE_LOGIN, loginController);

        // @route /systeemdatum/lesinfo
        server.registerHandler(SysteemDatumController.ROUTE_SYSTEEM_DATUM, systeemDatumController);

        // @route /student/medestudenten/ophalen
        server.registerHandler(MedestudentenController.ROUTE_MEDESTUDENT_OPHALEN, medestudentenController);

        // @route /search/
        server.registerHandler(SearchController.ROUTE_SEARCH, searchController);

        // @route /cursus/
        server.registerHandler(CursusController.ROUTE_CURSUS, cursusController);

        // @route /presentie/ophalen
        server.registerHandler(StudentController.ROUTE_PRESENT_FETCH, studentController);

        // @route /student/info
        server.registerHandler(StudentController.ROUTE_STUDENT_INFO, studentController);

        // @route /student/medestudenten/opslaan
        server.registerHandler(MedestudentenController.ROUTE_MEDESTUDENT_OPSLAAN, medestudentenController);

        // @route /agenda/laad
        server.registerHandler(AgendaController.ROUTE_AGENDA_LADEN, agendaController);

        server.start();
    }
}