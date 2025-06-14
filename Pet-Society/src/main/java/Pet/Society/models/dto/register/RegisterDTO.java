package Pet.Society.models.dto.register;

import Pet.Society.models.enums.Speciality;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class RegisterDTO {
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
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

    public RegisterDTO() {
    }

    public RegisterDTO(String username, String password, String name, String surname, String phone, String dni, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.dni = dni;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public RegisterDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RegisterDTO setPassword(String password) {
        this.password = password;
        return this;
    }
    
    public String getName() {
        return name;
    }

    public RegisterDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getSurname() {
        return surname;
    }

    public RegisterDTO setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public RegisterDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getDni() {
        return dni;
    }

    public RegisterDTO setDni(String dni) {
        this.dni = dni;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public RegisterDTO setEmail(String email) {
        this.email = email;
        return this;
    }


    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Speciality getSpeciality() {
        return this.speciality;
    }
}
