package Pet.Society.models.dto.client;

import Pet.Society.models.dto.pet.PetDTO;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@Data
@Getter
@Setter
@NoArgsConstructor
@Builder

public class ClientDTO {
    private String name;
    private String surname;
    private String dni;
    private String phone;
    private String email;
    private List<PetDTO> pets;
}
