package Pet.Society.controllers;

import Pet.Society.models.entities.DoctorEntity;
import Pet.Society.services.DoctorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@Tag(
        name = "Doctor",
        description = "Controller for managing doctors"
)
@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @Operation(
            summary = "Create a new doctor",
            description = "Creates a new doctor based on the provided entity.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Doctor created successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DoctorEntity.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<DoctorEntity> createDoctor(@Valid @RequestBody DoctorEntity doctor) {
        DoctorEntity doctorEntity = doctorService.save(doctor);
        return new ResponseEntity<>(doctorEntity, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an existing doctor",
            description = "Updates an existing doctor by ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Doctor updated successfully",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DoctorEntity.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Doctor not found",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PatchMapping("/update/{id}")
    public ResponseEntity<DoctorEntity> updateDoctor(@Valid @RequestBody DoctorEntity doctor, @PathVariable Long id) {
        doctorService.update(doctor, id);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @Operation(
            summary = "Unsubscribe a doctor",
            description = "Marks a doctor as unsubscribed by ID (soft delete or status change).",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Doctor unsubscribed successfully",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Doctor not found",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @PatchMapping("/unsubscribe/{id}")
    public ResponseEntity<DoctorEntity> unsuscribe(@PathVariable Long id) {
        doctorService.unSubscribe(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Operation(
            summary = "Find doctor by ID",
            description = "Retrieves a doctor by their database ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Doctor found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DoctorEntity.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Doctor not found",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping("/find/{id}")
    public ResponseEntity<DoctorEntity> findById(@PathVariable Long id) {
        DoctorEntity doctor = doctorService.findById(id);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @Operation(
            summary = "Find doctor by DNI",
            description = "Retrieves a doctor using their unique DNI.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Doctor found",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = DoctorEntity.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Doctor not found",
                            content = @Content(mediaType = "application/json")
                    )
            }
    )
    @GetMapping("/find/dni/{dni}")
    public ResponseEntity<DoctorEntity> findByDNI (@PathVariable String dni){
        DoctorEntity doctor = doctorService.findByDni(dni);
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

    @PostMapping("/addDoctors")
    public ResponseEntity<List<DoctorEntity>> addDoctors() {
        return ResponseEntity.ok(this.doctorService.addDoctors());
    }
}
