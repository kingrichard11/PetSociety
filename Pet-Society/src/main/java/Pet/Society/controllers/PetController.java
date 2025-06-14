package Pet.Society.controllers;

import Pet.Society.models.dto.pet.PetDTO;
import Pet.Society.models.entities.PetEntity;
import Pet.Society.services.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(
        name = "Pet",
        description = "Controller for managing pets"
)
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @Operation(
            summary = "Create a new pet",
            description = "Endpoint to register a new pet to a client.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Pet created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PetEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<PetEntity> createPet(@Valid @RequestBody PetDTO dto) {
        PetEntity createdPet = petService.createPet(dto);
        return new ResponseEntity<>(createdPet, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an existing pet",
            description = "Endpoint to update pet data based on its ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pet updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PetEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pet not found",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @PatchMapping("/update/{id}")
    public ResponseEntity<PetEntity> updatePet(@PathVariable Long id, @RequestBody PetDTO dto) {
        PetEntity updatedPet = petService.updatePet(id, dto);
        return new ResponseEntity<>(updatedPet, HttpStatus.OK);
    }

    @Operation(
            summary = "Unsubscribe a pet (soft delete)",
            description = "Sets the pet's active status to false instead of deleting from database.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pet unsubscribed successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PetEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pet not found",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @PatchMapping("/deleteActive/{id}")
    public ResponseEntity<PetEntity> deleteActive(@PathVariable Long id) {

        PetEntity pet = petService.getPetById(id);
        PetDTO dto = new PetDTO(
                pet.getName(),
                pet.getAge(),
                false,
                pet.getClient().getId()
        );
        PetEntity petEntity = petService.updatePet(id, dto);
        return ResponseEntity.ok(petEntity);
    }


    @Operation(
            summary = "Get pet by ID",
            description = "Retrieves a pet and maps it to a DTO by its unique ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pet found successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PetDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Pet not found",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    @GetMapping("/findByID/{id}")
    public ResponseEntity<PetDTO> getPetById(@PathVariable Long id) {

        PetEntity pet = petService.getPetById(id);
        PetDTO dto = new PetDTO(
                pet.getName(),
                pet.getAge(),
                pet.isActive(),
                pet.getClient().getId()
        );
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @Operation(
            summary = "Get all pets for a given client",
            description = "Retrieves all pets associated with a specific client ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Pets retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = PetDTO.class)
                            )
                    )
            }
    )
    @GetMapping("/findAllByClientId/{id}")
    public ResponseEntity<List<PetDTO>> getAllPetsByClientId(@PathVariable("id") Long id) {

        Iterable<PetEntity> pets = petService.getAllPetsByClientId(id);
        List<PetDTO> dtos = new ArrayList<>();
        for (PetEntity pet : pets) {
            dtos.add(new PetDTO(
                    pet.getName(),
                    pet.getAge(),
                    pet.isActive(),
                    pet.getClient().getId()
            ));
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }


    @PostMapping("/assignPets")
    public ResponseEntity<String> assignSamplePets() {
        petService.assignPetsToClients();
        return ResponseEntity.ok("Se asignaron 2 mascotas a cada cliente.");
    }

}
