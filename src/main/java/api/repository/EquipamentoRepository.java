package api.repository;

import api.entity.Equipamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {


    @Query("SELECT e FROM Equipamento e WHERE LOWER(e.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Equipamento> buscarPorNome(@Param("nome") String nome, Pageable pageable);


}
