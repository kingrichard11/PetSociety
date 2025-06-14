package Pet.Society.services;

import Pet.Society.models.dto.pet.PetDTO;
import Pet.Society.models.entities.ClientEntity;
import Pet.Society.models.entities.PetEntity;
import Pet.Society.models.exceptions.PetNotFoundException;
import Pet.Society.models.exceptions.UserNotFoundException;
import Pet.Society.models.interfaces.Mapper;
import Pet.Society.repositories.ClientRepository;
import Pet.Society.repositories.PetRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import java.util.ArrayList;
import java.util.List;


@Service
public class PetService implements Mapper<PetDTO, PetEntity> {

    private final PetRepository petRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public PetService(PetRepository petRepository, ClientRepository clientRepository) {
        this.petRepository = petRepository;
        this.clientRepository = clientRepository;
    }


    public PetDTO createPet(PetDTO dto) {
        ClientEntity client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new UserNotFoundException("Cliente with ID " + dto.getClientId() + " not found."));

        PetEntity pet = new PetEntity();
        pet.setName(dto.getName());
        pet.setAge(dto.getAge());
        pet.setActive(dto.isActive());
        pet.setClient(client);
        petRepository.save(pet);

        return dto;
    }


    //CORREGIR
    public PetEntity updatePet(Long id,PetDTO pet) {
        PetEntity existingPet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("La mascota con ID: " + id + " no existe."));

        //MODULARIZAR ESTA COSA HORRIBLE
        /**Validar y actualizar cada campo*/
        if (pet.getName() != null) {
            existingPet.setName(pet.getName());
        }
        if (pet.getAge() != 0) {
            existingPet.setAge(pet.getAge());
        }

        if (pet.getClientId() != null){
            ClientEntity client = clientRepository.findById(pet.getClientId())
                    .orElseThrow(() -> new UserNotFoundException("Cliente con ID " + pet.getClientId() + " no encontrado."));
            existingPet.setClient(client);
        }

        if (pet.isActive() != existingPet.isActive()) {
            existingPet.setActive(pet.isActive());
        }
        return petRepository.save(existingPet);
    }



    public PetEntity getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("the pet doesn't exist with ID: " + id));

    }

    public Optional<ClientEntity> getOwnerByPetId(Long id) {
        PetEntity pet = petRepository.findById(id)
                .orElseThrow(() -> new PetNotFoundException("The pet doesn't exist with ID: " + id));
        return Optional.ofNullable(pet.getClient());
    }

    public boolean existsPetById(Long id) {
        return petRepository.existsById(id);
    }


    public Iterable<PetEntity> getAllPets() {
        return petRepository.findAll();
    }

    public Iterable<PetEntity> getAllPetsByClientId(Long clientId) {
        ClientEntity client = clientRepository.findById(clientId)
                .orElseThrow(() -> new UserNotFoundException("Cliente con ID " + clientId + " no encontrado."));

        return petRepository.findAllByClient(client);
    }

    public void assignPetsToClients() {
        Faker faker = new Faker();
        List<PetEntity> petsToSave = new ArrayList<>();
        Iterable<ClientEntity> clients = clientRepository.findAll();

        for (ClientEntity client : clients) {
            for (int i = 0; i < 2; i++) {
                PetEntity pet = new PetEntity();
                pet.setName(faker.animal().name());
                pet.setAge(faker.number().numberBetween(1, 15));
                pet.setActive(true);
                pet.setClient(client);
                petsToSave.add(pet);
            }
        }
        petRepository.saveAll(petsToSave);
    }


    @Override
    public PetEntity toEntity(PetDTO dto) {
        return PetEntity.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .active(dto.isActive())
                .client(this.clientRepository.findById(dto.getClientId()).get())
                .build();
    }

    @Override
    public PetDTO toDTO(PetEntity entity) {
        return PetDTO.builder()
                .name(entity.getName())
                .age(entity.getAge())
                .active(entity.isActive())
                .clientId(entity.getClient().getId())
                .build();
    }
}
