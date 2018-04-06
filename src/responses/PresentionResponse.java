package responses;

import model.Presention;

public class PresentionResponse {
    public int studentId;
    public boolean present;

    public PresentionResponse(Presention presention) {
        this.studentId = presention.getStudent().getStudentId();
        this.present = presention.isPresent();
    }
}
