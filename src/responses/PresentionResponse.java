
package responses;

import model.Presention;

public class PresentionResponse {
    public StudentResponse studentResponse;
    public boolean present;

    public PresentionResponse(Presention presention) {
        this.studentResponse = presention.getStudent().toStudentResponse();
        this.present = presention.isPresent();
    }
}
