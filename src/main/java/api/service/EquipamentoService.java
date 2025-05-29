package api.service;

import api.dto.EquipamentoDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;


public interface EquipamentoService {


    PagedModel<EntityModel<EquipamentoDTO>> listar(Pageable pageable);

    PagedModel<EntityModel<EquipamentoDTO>> buscarPorNome(String nome, Pageable pageable);

    EquipamentoDTO buscarPorId(Long id);

    EquipamentoDTO cadastrar(EquipamentoDTO equipamentoDTO);

    EquipamentoDTO atualizar(EquipamentoDTO equipamentoDTO);

    void excluir(Long id);


}