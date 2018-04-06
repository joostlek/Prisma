package responses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LessonResponse {

    public String cursus;
    public String fromTime;
    public String toTime;
    public String datum;
    public ArrayList<PresentionResponse> presentionResponses;

    public LessonResponse(String cursus, LocalDateTime fromTime, LocalDateTime toTime, ArrayList<PresentionResponse> presentionResponses) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dtf_days = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.presentionResponses = presentionResponses;
        this.cursus = cursus;
        this.fromTime = fromTime.format(dtf);
        this.toTime = toTime.format(dtf);
        this.datum = fromTime.format(dtf_days);
    }
}
