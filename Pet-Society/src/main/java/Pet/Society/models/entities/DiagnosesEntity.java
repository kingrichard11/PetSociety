package Pet.Society.models.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
public class DiagnosesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Lob //indica que es un Character Large Object para que lo pase como tipo TEXT
    private String diagnose;
    @Lob
    private String treatment;
    @ManyToOne
    private DoctorEntity doctor;
    @ManyToOne
    private PetEntity pet;
    @OneToOne
    private AppointmentEntity appointment;
    private LocalDateTime date;

    public DiagnosesEntity() {
    }

    public DiagnosesEntity(long id, String diagnose, String treatment, DoctorEntity doctor, PetEntity pet, AppointmentEntity appointment, LocalDateTime date) {
        this.id = id;
        this.diagnose = diagnose;
        this.treatment = treatment;
        this.doctor = doctor;
        this.pet = pet;
        this.appointment = appointment;
        this.date = date;
    }

    public DiagnosesEntity(String diagnose, String treatment, DoctorEntity doctor, PetEntity pet, AppointmentEntity appointment, LocalDateTime date) {
        this.diagnose = diagnose;
        this.treatment = treatment;
        this.doctor = doctor;
        this.pet = pet;
        this.appointment = appointment;
        this.date = date;
    }

    public DiagnosesEntity(String diagnose, String treatment, AppointmentEntity appointment, LocalDateTime date) {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public DoctorEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(DoctorEntity doctor) {
        this.doctor = doctor;
    }

    public PetEntity getPet() {
        return pet;
    }

    public void setPet(PetEntity pet) {
        this.pet = pet;
    }

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentEntity appointment) {
        this.appointment = appointment;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }


}
