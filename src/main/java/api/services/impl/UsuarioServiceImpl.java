package api.services.impl;

import api.entity.Usuario;
import api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscando o usuário pelo nome de usuário
        Usuario usuario = usuarioRepository.buscarPorUsuario(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        // Obtendo as permissões do usuário
        Set<String> roles = new HashSet<>();
        usuario.getUsuarioPerfis().forEach(usuarioPerfil -> {
            // Aqui, você pode mapear o perfil para roles
            roles.add(usuarioPerfil.getPerfil().getNome());  // Exemplo de como pegar o nome do perfil (ROLE_ADMIN, ROLE_USER)
        });

        // Retorna o UserDetails que o Spring Security usará para autenticação
        return User.builder()
                .username(usuario.getUserName())
                .password(usuario.getPassword())
                .roles(roles.toArray(new String[0]))  // Converte o Set de roles para um array de strings
                .build();
    }
}
