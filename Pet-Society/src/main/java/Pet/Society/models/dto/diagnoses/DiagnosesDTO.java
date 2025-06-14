package Pet.Society.models.dto.diagnoses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class DiagnosesDTO {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9 ]+$")
    private String diagnose;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9 ]+$")
    private String treatment;

    @NotNull
    private Long appointmentId;

    @NotNull
    @PastOrPresent
    private LocalDateTime date;

    public DiagnosesDTO() {
    }

    public DiagnosesDTO(String diagnose, String treatment, Long appointmentId, LocalDateTime date) {
        this.diagnose = diagnose;
        this.treatment = treatment;
        this.appointmentId = appointmentId;
        this.date = date;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public DiagnosesDTO setDiagnose(String diagnose) {
        this.diagnose = diagnose;
        return this;
    }

    public String getTreatment() {
        return treatment;
    }

    public DiagnosesDTO setTreatment(String treatment) {
        this.treatment = treatment;
        return this;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public DiagnosesDTO setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
        return this;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public DiagnosesDTO setDate(LocalDateTime date) {
        this.date = date;
        return this;
    }
}
