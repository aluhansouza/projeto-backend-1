package api.controllers;

import api.controllers.docs.AuthControllerDocs;
import api.dto.request.auth.LoginRequestDTO;
import api.dto.request.auth.RefreshTokenRequestDTO;
import api.dto.request.auth.UsuarioCadastroRequestDTO;
import api.dto.response.auth.LoginResponseDTO;
import api.dto.response.auth.RefreshTokenResponseDTO;
import api.dto.response.auth.UsuarioCadastroResponseDTO;
import api.security.JwtUtil;
import api.services.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController implements AuthControllerDocs {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioServiceImpl usuarioService;


    @Override
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(), loginRequest.getPassword())
            );
            String accessToken = jwtUtil.generateToken(loginRequest.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(loginRequest.getUsername());

            return ResponseEntity.ok(new LoginResponseDTO(accessToken, refreshToken));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).build();
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponseDTO> refresh(@RequestBody RefreshTokenRequestDTO refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        // Valida se o token é válido e se é realmente um refresh token
        if (!jwtUtil.validateToken(refreshToken) || !jwtUtil.isRefreshToken(refreshToken)) {
            return ResponseEntity.badRequest().build();
        }

        String username = jwtUtil.getUsernameFromToken(refreshToken);
        String newAccessToken = jwtUtil.generateToken(username);

        return ResponseEntity.ok(new RefreshTokenResponseDTO(newAccessToken));
    }



    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioCadastroResponseDTO> cadastro(@RequestBody UsuarioCadastroRequestDTO registroRequest) {
        UsuarioCadastroResponseDTO response = usuarioService.cadastro(registroRequest);
        if ("Usuário já existente!".equals(response.getMessage())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.status(201).body(response);
    }




}