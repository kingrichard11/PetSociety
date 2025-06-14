package Pet.Society.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class ClientEntity extends UserEntity {
    private Boolean foundation = true;
    @OneToMany(mappedBy = "client")
    private List<PetEntity> pets;

    public ClientEntity(long id, String name, String surname, String phone, String dni, String email) {
        super(id, name, surname, phone, dni, email);
    }

    public ClientEntity(String name, String surname, String phone, String dni, String email) {
        super(name, surname, phone, dni, email);
    }


    public ClientEntity() {
        super();
    }


    public Boolean getFoundation() {
        return foundation;
    }

    public void setFoundation(Boolean foundation) {
        this.foundation = foundation;
    }


    public List<PetEntity> getPets() {
        return pets;
    }
}
