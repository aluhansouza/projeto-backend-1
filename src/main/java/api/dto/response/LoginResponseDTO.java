package api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta do login contendo o token JWT")
public class LoginResponseDTO {
    @Schema(description = "Token JWT gerado ao autenticar", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6...")
    private String token;

    public LoginResponseDTO() {}
    public LoginResponseDTO(String token) { this.token = token; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
}
