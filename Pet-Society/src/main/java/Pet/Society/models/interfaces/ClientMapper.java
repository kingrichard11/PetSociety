package Pet.Society.models.interfaces;

import Pet.Society.models.dto.client.ClientDTO;
import Pet.Society.models.entities.ClientEntity;
import com.mysql.cj.xdevapi.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@Data
@NoArgsConstructor
@SuperBuilder
public class ClientMapper {
    private ClientEntity clientEntity;
    private ClientDTO clientDTO;

    public ClientDTO toClientDTO(ClientEntity entity) {
        return ClientDTO.builder().
                name(entity.getName()).
                surname(entity.getSurname())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .dni(entity.getDni())
                .build();
    }

    public ClientEntity toClientEntity(ClientDTO dto) {
        return ClientEntity.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .dni(dto.getDni())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .subscribed(true)
                .build();
    }


}
