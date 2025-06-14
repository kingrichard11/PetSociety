package Pet.Society.controllers;

import Pet.Society.models.dto.appointment.AppointmentDTO;
import Pet.Society.models.dto.appointment.AppointmentUpdateDTO;
import Pet.Society.models.dto.pet.AssingmentPetDTO;
import Pet.Society.models.entities.AppointmentEntity;
import Pet.Society.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Appointment",
        description = "Controller for managing appointments"
)
@RestController
@RequestMapping("/appointment")
public class AppointmentController {


    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    @Operation(
            summary = "Create a new appointment",
            description = "Endpoint to create a new appointment with the necessary details",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Appointment created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppointmentEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @PostMapping("/create")
    public ResponseEntity<AppointmentEntity> createAppointment(@RequestBody AppointmentDTO appointment) {
        AppointmentEntity appointmentEntity = this.appointmentService.save(appointment);

        return ResponseEntity.ok(appointmentEntity);
    }

    @Operation(
            summary = "Get all appointments",
            description = "Endpoint to retrieve all appointments",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of appointments retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppointmentEntity.class)
                            )
                    )
            }
    )
    @PatchMapping("/assign/{id}")
    public ResponseEntity<AppointmentEntity> assignAppointment(@PathVariable("id") Long appointmentId, @RequestBody AssingmentPetDTO pet) {
        return ResponseEntity.ok(this.appointmentService.bookAppointment(appointmentId,pet));
    }


    @Operation(
            summary = "Update an appointment",
            description = "Endpoint to update an existing appointment by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Appointment updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppointmentEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Appointment not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @PatchMapping("/update/{id}")
    public ResponseEntity<AppointmentEntity> updateAppointment(@PathVariable Long id, @RequestBody AppointmentUpdateDTO appointmentUpdateDTO) {
        AppointmentEntity appointment = this.appointmentService.updateAppointment(appointmentUpdateDTO, id);
        return ResponseEntity.ok(appointment);
    }

    @Operation(
            summary = "Get appointments from a specific client",
            description = "Endpoint to retrieve an all appointments from a specific client by their ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Appointments for the specified client retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppointmentEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Appointments for the specified client not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentsByClientId(@PathVariable Long clientId) {
            return ResponseEntity.ok(this.appointmentService.getAllAppointmentsByClientId(clientId));
    }

    @Operation(
            summary = "Get appointments from a specific pet",
            description = "Endpoint to retrieve an appointments from a specific pet by its ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Appointments for the specified pet retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppointmentEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Appointments for the specified pet not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @GetMapping("/pet/{petId}")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentsByPetId(@PathVariable Long petId) {
        return ResponseEntity.ok(this.appointmentService.getAllAppointmentsByPetId(petId));
    }

    @Operation(
            summary = "Get appointments from a specific doctor",
            description = "Endpoint to retrieve an appointments from a specific doctor by their ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Appointments for the specified doctor retrieved successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppointmentEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Appointments for the specified doctor not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentEntity>> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(this.appointmentService.getAllAppointmentsByDoctorId(doctorId));
    }

    @PostMapping("/assignRandom")
    public ResponseEntity<String> assignRandomPastAppointmentsToClients() {
        appointmentService.assignAppointmentToClient();
        return ResponseEntity.ok("Se asignó una cita aleatoria a cada cliente.");
    }


}
