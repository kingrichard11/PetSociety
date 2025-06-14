package Pet.Society.models.dto.diagnoses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;

public class DiagnosesDTOResponse {

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9 ]+$")
    private String diagnose;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9 ]+$")
    private String treatment;

    @NotNull
    private Long doctorId;

    @NotNull
    private Long petId;

    @NotNull
    private Long appointmentId;

    @NotNull
    @PastOrPresent
    private LocalDateTime date;

    public DiagnosesDTOResponse() {
    }

    public DiagnosesDTOResponse(String diagnose, String treatment, Long doctorId, Long petId, Long appointmentId, LocalDateTime date) {
        this.diagnose = diagnose;
        this.treatment = treatment;
        this.doctorId = doctorId;
        this.petId = petId;
        this.appointmentId = appointmentId;
        this.date = date;
    }

    public String getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(String diagnose) {
        this.diagnose = diagnose;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
