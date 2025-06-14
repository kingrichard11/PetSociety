package Pet.Society.models.entities;

import Pet.Society.models.enums.Speciality;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;

@Entity
public class DoctorEntity extends UserEntity{
    @Enumerated
    private Speciality speciality;

    public DoctorEntity(long id, String name, String surname, String phone, String DNI, String email, Speciality speciality) {
        super(id, name, surname, phone, DNI, email);
        this.speciality = speciality;
    }

    public DoctorEntity(String name, String surname, String phone, String DNI, String email, Speciality speciality) {
        super(name, surname, phone, DNI, email);
        this.speciality = speciality;
    }

    public DoctorEntity() {
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

}
