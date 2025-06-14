package Pet.Society.models.dto.appointment;

import Pet.Society.models.enums.Reason;
import Pet.Society.models.enums.Status;

public class AppointmentUpdateDTO {
    private Reason reason;
    private Status status;
    private Long petId;
    private Long diagnosesId;
    private Boolean approved;

    public AppointmentUpdateDTO() {
    }

    public AppointmentUpdateDTO(Reason reason, Status status, Long petId, Long diagnosesId, Boolean approved) {
        this.reason = reason;
        this.status = status;
        this.petId = petId;
        this.diagnosesId = diagnosesId;
        this.approved = approved;
    }

    public Reason getReason() {
        return reason;
    }
    public void setReason(Reason reason) {
        this.reason = reason;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public Long getPetId() {
        return petId;
    }
    public void setPetId(Long petId) {
        this.petId = petId;
    }
    public Long getDiagnosesId() {
        return diagnosesId;
    }
    public void setDiagnosesId(Long diagnosesId) {
        this.diagnosesId = diagnosesId;
    }
    public Boolean getApproved() {
        return approved;
    }
    public void setApproved(Boolean approved) {
        this.approved = approved;
    }
}
