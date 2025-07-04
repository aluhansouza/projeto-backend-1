package api.services.interfaces;

import api.entity.Usuario;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UsuarioService extends UserDetailsService {


    Usuario salvar(Usuario usuario);
    Optional<Usuario> buscarPorId(Long id);
    Optional<Usuario> buscarPorUserName(String userName);
    List<Usuario> listarTodos();
    void deletarPorId(Long id);
}
