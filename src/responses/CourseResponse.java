package responses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CourseResponse {

    public String cursus;
    public String fromTime;
    public String toTime;
    public String datum;

    public CourseResponse(String cursus, LocalDateTime fromTime, LocalDateTime toTime) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter dtf_days = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        this.cursus = cursus;
        this.fromTime = fromTime.format(dtf);
        this.toTime = fromTime.format(dtf);
        this.datum = fromTime.format(dtf_days);
    }
}
