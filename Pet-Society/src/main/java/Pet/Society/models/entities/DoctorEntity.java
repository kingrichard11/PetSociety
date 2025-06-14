package Pet.Society.models.entities;

import Pet.Society.models.enums.Speciality;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
@Table(name="doctors")
public class DoctorEntity extends UserEntity{
    @Enumerated
    private Speciality speciality;



}
