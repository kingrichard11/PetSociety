package Pet.Society.services;


import Pet.Society.models.dto.client.ClientDTO;
import Pet.Society.models.entities.ClientEntity;
import Pet.Society.models.exceptions.UserExistsException;
import Pet.Society.models.exceptions.UserNotFoundException;
import Pet.Society.models.interfaces.Mapper;
import Pet.Society.repositories.ClientRepository;
import com.mysql.cj.xdevapi.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;



import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements Mapper <ClientDTO, ClientEntity> {

    private final ClientRepository clientRepository;


    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;

    }

    public ClientDTO save(ClientDTO clientDTO) {
        Optional<ClientEntity> clientEntity= this.clientRepository.findByDni(clientDTO.getDni());
          if(clientEntity.isPresent()) {
              throw new UserExistsException("User already exists");
          }
        ClientEntity clientToSave = toEntity(clientDTO);
          this.clientRepository.save(clientToSave);
          return clientDTO;
    }

    public ClientDTO findById(long id) {
       ClientEntity client= this.clientRepository.findById(id).stream()
               .findFirst().orElseThrow(() -> new UserNotFoundException("Client not found"));
       return toDTO(client);
    }

    //SOLO RECIBE UN JSON COMPLETO.
    public ClientDTO update(ClientDTO clientToModify, Long id) {
        Optional<ClientEntity> existingClient = this.clientRepository.findById(id);
        if (existingClient.isEmpty()){
            throw new UserNotFoundException("User does not exist");
        }
        ClientEntity clientToUpdate = toEntity(clientToModify);
        takeAttributes(clientToUpdate, existingClient.get());
        this.clientRepository.save(clientToUpdate);
        return clientToModify;
    }

    public void unSubscribe(Long id){
        Optional<ClientEntity> existingClient = this.clientRepository.findById(id);
        if (existingClient.isEmpty()){
            throw new UserNotFoundException("User does not exist");
        }
        ClientEntity clientToUnsubscribe = existingClient.get();
        clientToUnsubscribe.setSubscribed(false);
        this.clientRepository.save(clientToUnsubscribe);
    }

    public ClientEntity findByDNI(String DNI){
        return this.clientRepository.findByDni(DNI).orElseThrow(()-> new UserExistsException("User does not exist"));
    }

    public ClientDTO takeAttributes(ClientEntity origin, ClientEntity destination) {
        if(origin.getName() == null){origin.setPhone(destination.getPhone());}
        if(origin.getSurname() == null){origin.setSurname(destination.getSurname());}
        if(origin.getEmail() == null){origin.setEmail(destination.getEmail());}
        if(origin.getDni() == null){origin.setDni(destination.getDni());}
        if(origin.getPhone()==null){origin.setPhone(destination.getPhone());}

        return toDTO(destination);
    }

    public List<ClientEntity> getAllClients() {
        List<ClientEntity> clients = this.clientRepository.findAll();
        if (clients.isEmpty()) {
            throw new UserNotFoundException("No clients found");
        }
        return clients;
    }


    @Override
    public ClientEntity toEntity(ClientDTO dto) {
        return ClientEntity.builder()
                .name(dto.getName())
                .surname(dto.getSurname())
                .dni(dto.getDni())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .subscribed(true)
                .build();
    }

    @Override
    public ClientDTO toDTO(ClientEntity entity) {
        return ClientDTO.builder().
                name(entity.getName()).
                surname(entity.getSurname())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .dni(entity.getDni())
                .build();
    }
}

