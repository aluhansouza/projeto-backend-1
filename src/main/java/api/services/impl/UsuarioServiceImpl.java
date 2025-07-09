package api.services.impl;

import api.dto.request.auth.UsuarioCadastroRequestDTO;
import api.dto.response.auth.UsuarioCadastroResponseDTO;
import api.entity.Perfil;
import api.entity.Usuario;
import api.entity.UsuarioPerfil;
import api.mapstruct.UsuarioMapper;
import api.repository.PerfilRepository;
import api.repository.UsuarioRepository;
import api.services.interfaces.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UsuarioService, UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PerfilRepository perfilRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Tentando autenticar usuário: " + username);
        try {
            Usuario usuario = usuarioRepository.buscarPorUsuario(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));
            System.out.println("Usuário encontrado: " + usuario);

            System.out.println("userName: " + usuario.getUserName());
            System.out.println("password: " + usuario.getPassword());
            System.out.println("enabled: " + usuario.getEnabled());
            System.out.println("accountNonExpired: " + usuario.getAccountNonExpired());
            System.out.println("accountNonLocked: " + usuario.getAccountNonLocked());
            System.out.println("credentialsNonExpired: " + usuario.getCredentialsNonExpired());
            System.out.println("usuarioPerfis: " + usuario.getUsuarioPerfis());

            Set<String> roles = new HashSet<>();
            if (usuario.getUsuarioPerfis() != null) {
                usuario.getUsuarioPerfis().forEach(usuarioPerfil -> {
                    System.out.println("Perfil: " + usuarioPerfil);
                    if (usuarioPerfil.getPerfil() == null) {
                        System.out.println("Perfil do usuário está NULL!");
                    } else {
                        System.out.println("Nome do perfil: " + usuarioPerfil.getPerfil().getNome());
                        roles.add(usuarioPerfil.getPerfil().getNome());
                    }
                });
            } else {
                System.out.println("usuarioPerfis está NULL!");
            }

            System.out.println("Roles extraídas: " + roles);

            return User.builder()
                    .username(usuario.getUserName())
                    .password(usuario.getPassword())
                    .roles(roles.toArray(new String[0]))
                    .accountExpired(usuario.getAccountNonExpired() != null && !usuario.getAccountNonExpired())
                    .accountLocked(usuario.getAccountNonLocked() != null && !usuario.getAccountNonLocked())
                    .credentialsExpired(usuario.getCredentialsNonExpired() != null && !usuario.getCredentialsNonExpired())
                    .disabled(usuario.getEnabled() != null && !usuario.getEnabled())
                    .build();

        } catch (Exception e) {
            System.out.println("Erro ao autenticar usuário!");
            e.printStackTrace();
            throw e;
        }
    }

    public UsuarioCadastroResponseDTO cadastro(UsuarioCadastroRequestDTO registroRequest) {
        if (usuarioRepository.existsByUserName(registroRequest.getUsername())) {
            return new UsuarioCadastroResponseDTO("Usuário já existente!");
        }

        Perfil perfilPadrao = perfilRepository.findByNome("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Perfil padrão não encontrado"));

        Usuario usuario = usuarioMapper.toEntity(registroRequest);
        usuario.setPassword(passwordEncoder.encode(registroRequest.getPassword()));

        UsuarioPerfil usuarioPerfil = new UsuarioPerfil();
        usuarioPerfil.setUsuario(usuario);
        usuarioPerfil.setPerfil(perfilPadrao);
        usuarioPerfil.setDataAssociacao(LocalDate.now());
        usuarioPerfil.setAtivo(true);

        usuario.setUsuarioPerfis(Collections.singleton(usuarioPerfil));
        usuarioRepository.save(usuario);

        return new UsuarioCadastroResponseDTO("Usuário cadastrado com sucesso!");
    }
}