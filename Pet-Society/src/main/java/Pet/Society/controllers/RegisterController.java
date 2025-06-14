package Pet.Society.controllers;

import Pet.Society.models.dto.register.RegisterDTO;
import Pet.Society.services.RegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(
        name = "Register",
        description = "Controller for user registration"
)
@RestController
@RequestMapping("/register")
public class RegisterController {


    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @Operation(
            summary = "Register a new client user",
            description = "Creates a new user with the CLIENT role using the provided registration data.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Client registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
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
    @PostMapping("/new/client")
    public ResponseEntity<String> registerClient(@Valid @RequestBody RegisterDTO dto) {
        registerService.registerNewClient(dto);
        return ResponseEntity.ok("Successfully registered user");
    }

    @Operation(
            summary = "Register a new admin user",
            description = "Creates a new user with the ADMIN role using the provided registration data.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Admin registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
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
    @PostMapping("/new/admin")
    public ResponseEntity<String> registerAdmin(@Valid @RequestBody RegisterDTO dto) {
        registerService.registerNewAdmin(dto);
        return ResponseEntity.ok("Successfully registered admin");
    }

    @Operation(
            summary = "Register a new doctor user",
            description = "Creates a new user with the DOCTOR role using the provided registration data.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Doctor registered successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
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
    @PostMapping("/new/doctor")
    public ResponseEntity<String> registerDoctor(@Valid @RequestBody RegisterDTO dto) {
        registerService.registerNewDoctor(dto);
        return ResponseEntity.ok("Successfully registered doctor");
    }
}
