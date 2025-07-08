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
        // Busca pelo método correto do repositório
        Usuario usuario = usuarioRepository.buscarPorUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        // Obtendo os perfis/roles do usuário
        Set<String> roles = new HashSet<>();
        if (usuario.getUsuarioPerfis() != null) {
            usuario.getUsuarioPerfis().forEach(usuarioPerfil -> {
                roles.add(usuarioPerfil.getPerfil().getNome()); // Ex: ROLE_ADMIN, ROLE_USER
            });
        }

        // Retorna o UserDetails para o Spring Security
        return User.builder()
                .username(usuario.getUserName())
                .password(usuario.getPassword())
                .roles(roles.toArray(new String[0]))
                .accountExpired(!usuario.getAccountNonExpired())
                .accountLocked(!usuario.getAccountNonLocked())
                .credentialsExpired(!usuario.getCredentialsNonExpired())
                .disabled(!usuario.getEnabled())
                .build();
    }

    public UsuarioCadastroResponseDTO cadastro(UsuarioCadastroRequestDTO registroRequest) {
        if (usuarioRepository.existsByUserName(registroRequest.getUsername())) {
            return new UsuarioCadastroResponseDTO("Usuário já existente!");
        }

        Perfil perfilPadrao = perfilRepository.findByNome("ROLE_USUARIO")
                .orElseGet(() -> {
                    Perfil p = new Perfil();
                    p.setNome("ROLE_USER");
                    return perfilRepository.save(p);
                });

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