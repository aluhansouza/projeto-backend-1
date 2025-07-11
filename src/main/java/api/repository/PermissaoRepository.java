package api.repository;

import api.entity.Permissao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissaoRepository extends JpaRepository<Permissao, Long> {

    Optional<Permissao> findByNome(String nome);


}
