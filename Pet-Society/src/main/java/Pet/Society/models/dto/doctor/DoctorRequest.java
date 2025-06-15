package Pet.Society.models.dto.doctor;

import Pet.Society.models.enums.Speciality;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class DoctorRequest {
    @NotNull
    private String username;
    @NotNull
    @Size(min = 2,max = 50,message = "The password is incorrect form")
    private String password;
    @NotNull
    @Size(min = 2, max = 50, message = "Error en nombre")
    private String name;
    @NotNull
    @Size(min = 2, max = 50, message = "Error en apellido")
    private String surname;
    @NotNull
    @Size(min = 9, max = 20, message = "Error at phone")
    private String phone;
    @NotNull
    @Size(min = 7, max = 8, message = "Error in t")
    private String dni;
    @Email
    @NotNull
    private String email;
    @NotNull
    private Speciality speciality;

    public DoctorRequest() {
    }

    public DoctorRequest(Long id, String name, String surname, String phone, String dni, String email, Speciality speciality) {

        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.dni = dni;
        this.email = email;
        this.speciality = speciality;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @NotNull String getUsername() {
        return username;
    }

    public void setUsername(@NotNull String username) {
        this.username = username;
    }

    public @NotNull @Size(min = 2, max = 50, message = "The password is incorrect form") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @Size(min = 2, max = 50, message = "The password is incorrect form") String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
}
