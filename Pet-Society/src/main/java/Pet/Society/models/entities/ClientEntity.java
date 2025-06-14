package Pet.Society.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ClientEntity extends UserEntity {
    private Boolean foundation = true;
    @OneToMany(mappedBy = "client")
    private List<PetEntity> pets;


}
