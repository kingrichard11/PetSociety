package Pet.Society.services;


import Pet.Society.models.dto.register.RegisterDTO;
import Pet.Society.models.entities.ClientEntity;
import Pet.Society.models.exceptions.UserExistsException;
import Pet.Society.models.exceptions.UserNotFoundException;
import Pet.Society.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.javafaker.Faker;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {


    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RegisterService registerService;

    public ClientEntity save(ClientEntity client) {
        Optional<ClientEntity> clientEntity= this.clientRepository.findByDni(client.getDni());
          if(clientEntity.isPresent()) {
              throw new UserExistsException("User already exists");
          }
          return clientRepository.save(client);
    }

    public ClientEntity findById(long id) {
        return this.clientRepository.findById(id).stream().findFirst().orElseThrow(() -> new UserNotFoundException("Client not found"));
    }

    //SOLO RECIBE UN JSON COMPLETO.
    public void update(ClientEntity clientToModify, Long id) {
        Optional<ClientEntity> existingClient = this.clientRepository.findById(id);
        if (existingClient.isEmpty()){
            throw new UserNotFoundException("User does not exist");
        }
        clientToModify.setId(id);
        takeAttributes(clientToModify, existingClient.get());
        this.clientRepository.save(clientToModify);
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

    public ClientEntity takeAttributes(ClientEntity origin, ClientEntity destination) {
        if(origin.getName() == null){origin.setPhone(destination.getPhone());}
        if(origin.getSurname() == null){origin.setSurname(destination.getSurname());}
        if(origin.getEmail() == null){origin.setEmail(destination.getEmail());}
        if(origin.getDni() == null){origin.setDni(destination.getDni());}
        if(!origin.getFoundation()){origin.setFoundation(destination.getFoundation());}
        if(origin.getPhone()==null){origin.setPhone(destination.getPhone());}

        return origin;
    }



    public List<ClientEntity> addRandomClients() {
        List<ClientEntity> clients = new ArrayList<>();
        Faker faker = new Faker();

        for (int i = 1; i <= 3; i++) {
            RegisterDTO dto = new RegisterDTO();
            dto.setName(faker.name().firstName());
            dto.setSurname(faker.name().lastName());
            dto.setPhone(faker.phoneNumber().cellPhone());
            dto.setDni(String.valueOf(faker.number().numberBetween(10000000, 99999999)));
            dto.setEmail(faker.internet().emailAddress());
            dto.setUsername(faker.name().username());
            dto.setPassword(faker.internet().password());

            registerService.registerNewClient(dto);


        }
        return clients;
    }


    public List<ClientEntity> getAllClients() {
        List<ClientEntity> clients = this.clientRepository.findAll();
        if (clients.isEmpty()) {
            throw new UserNotFoundException("No clients found");
        }
        return clients;
    }
}

