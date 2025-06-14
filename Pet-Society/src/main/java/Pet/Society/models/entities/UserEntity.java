package Pet.Society.models.entities;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(min = 2, max = 50, message = "Error on name")
    private String name;
    @NotNull
    @Size(min = 2, max = 50, message = "Error on surname")
    private String surname;
    @NotNull
    @Size(min = 9, max = 20, message = "Error on phone")
    private String phone;
    @NotNull
    @Size(min = 7, max = 8, message = "Error on dni")
    @Column(unique = true)
    private String dni;
    @NotNull
    @Email
    @Column(unique = true)
    private String email;
    @NotNull
    @ColumnDefault("true")
    private Boolean subscribed = true;



    public UserEntity(long id, String name, String surname, String phone, String dni, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.dni = dni;
        this.email = email;
    }

    public UserEntity(String name, String surname, String phone, String dni, String email) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.dni = dni;
        this.email = email;
    }

    public UserEntity() {

    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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


}
