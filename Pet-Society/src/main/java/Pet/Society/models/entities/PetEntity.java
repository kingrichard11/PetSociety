package Pet.Society.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.ColumnDefault;

@Entity
public class PetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "pet_id")
    private long id;
    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String name;
    @Positive(message = "La edad no puede ser nula")
    private int age;
    @ColumnDefault("1")
    private boolean active = true;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    @NotNull(message = "La id_cliente no puede ser nula")
    private ClientEntity client;

    public PetEntity(long id, String name, int age, boolean active) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.active = active;
    }

    public PetEntity(String name, int age, boolean active) {
        this.name = name;
        this.age = age;
        this.active = active;
    }

    public PetEntity(String name, int age, boolean active, ClientEntity client) {
        this.name = name;
        this.age = age;
        this.active = active;
        this.client = client;
    }

    public PetEntity() {
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "PetEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", active=" + active +
                ", client=" + client +
                '}';
    }
}
