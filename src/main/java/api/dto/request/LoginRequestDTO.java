package api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Credenciais para login")
public class LoginRequestDTO {
    @Schema(description = "Nome de usuário", example = "admin")
    private String username;

    @Schema(description = "Senha do usuário", example = "123456")
    private String password;

    // Getters e setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}