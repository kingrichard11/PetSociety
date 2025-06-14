package Pet.Society.controllers;

import Pet.Society.models.dto.login.LoginDTO;
import Pet.Society.models.dto.login.LoginResponseDTO;
import Pet.Society.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    // This controller can be used to handle authentication-related endpoints
    // For example, you can add methods for registration, password reset, etc.
    // Currently, it serves as a placeholder for future authentication-related functionality.

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO request){
        return ResponseEntity.ok(authService.login(request));
    }


}
