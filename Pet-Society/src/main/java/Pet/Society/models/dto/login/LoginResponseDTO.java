package Pet.Society.models.dto.login;

public class LoginResponseDTO {
    private final String token;
    private final Long id;

    public LoginResponseDTO(String token, Long id) {
        this.id = id;
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public Long getId() {
        return id;
    }
}
