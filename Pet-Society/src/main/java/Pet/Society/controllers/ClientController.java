package Pet.Society.controllers;

import Pet.Society.models.entities.AppointmentEntity;
import Pet.Society.models.entities.ClientEntity;
import Pet.Society.services.ClientService;
import ch.qos.logback.core.net.server.Client;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(
        name = "Client",
        description = "Controller for managing clients"
)
@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;




    @Operation(
            summary = "Update an existing client",
            description = "Endpoint to update an existing client's information.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Client updated successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Client not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @PatchMapping("/update/{id}")
    public ResponseEntity<ClientEntity> update(@RequestBody ClientEntity client, @PathVariable long id) {
            this.clientService.update(client,id);
            return ResponseEntity.ok(client);
    }

    @Operation(
            summary = "Unsubscribe a client",
            description = "Endpoint to unsubscribe a client by setting their active status to false.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Client unsubscribed successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Client not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @PatchMapping("/unsubscribe/{id}")
    public ResponseEntity<String> unsubscribe(@PathVariable long id) {
        this.clientService.unSubscribe(id);
        return ResponseEntity.status(HttpStatus.OK).body("Client unsubscribed successfully");
    }

    @Operation(
            summary = "Get client by DNI",
            description = "Endpoint to retrieve a client by their DNI (National ID).",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Client found successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AppointmentEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Client not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @GetMapping("/findByDni/{dni}")
    public ResponseEntity<ClientEntity> findByDni(@PathVariable String dni){
        return ResponseEntity.ok(this.clientService.findByDNI(dni));
    }

    @Operation(
            summary = "Find client by ID",
            description = "Endpoint to retrieve a client by their ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Client found successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ClientEntity.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Client not found",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    @GetMapping("/findById/{id}")
    public ResponseEntity<ClientEntity> findById(@PathVariable long id) {
        return ResponseEntity.ok(this.clientService.findById(id));
    }


    @PostMapping("/randomsClients")
    public ResponseEntity<?> addClients() {
        clientService.addRandomClients();
        return ResponseEntity.status(HttpStatus.CREATED).body("Clientes aleatorios agregados correctamente");
    }



}
