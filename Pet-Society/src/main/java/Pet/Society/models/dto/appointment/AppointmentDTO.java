package Pet.Society.models.dto.appointment;

import Pet.Society.models.entities.DoctorEntity;
import Pet.Society.models.enums.Reason;

import java.time.LocalDateTime;

public class AppointmentDTO {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private DoctorEntity doctor;
    private Reason reason;
    private boolean aproved;



    public AppointmentDTO() {
    }

    public AppointmentDTO(LocalDateTime startTime, LocalDateTime endTime, DoctorEntity doctor, Reason reason, boolean aproved) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.reason = reason;
        this.aproved = aproved;
    }

    public AppointmentDTO(LocalDateTime startTime, LocalDateTime endTime, DoctorEntity doctor, Reason reason) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
        this.reason = reason;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public boolean isAproved() {
        return aproved;
    }

    public void setAproved(boolean aproved) {
        this.aproved = aproved;
    }
}
