package api.services.interfaces;

import api.dto.EquipamentoDTO;
import api.entity.Equipamento;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface EquipamentoService {


    PagedModel<EntityModel<EquipamentoDTO>> listar(Pageable pageable);

    PagedModel<EntityModel<EquipamentoDTO>> buscarPorNome(String nome, Pageable pageable);

    EquipamentoDTO buscarPorId(Long id);

    EquipamentoDTO cadastrar(EquipamentoDTO equipamentoDTO);

    EquipamentoDTO atualizar(EquipamentoDTO equipamentoDTO);

    void excluir(Long id);

    // ---------------------------------------------------------------------------------

    Resource exportarPagina(Pageable pageable, String acceptHeader);

    List<EquipamentoDTO> massCreation(MultipartFile file);


}