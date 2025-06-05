package api.services.interfaces;

import api.dto.EquipamentoRequestDTO;
import api.dto.EquipamentoResponseDTO;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface EquipamentoService {


    PagedModel<EntityModel<EquipamentoResponseDTO>> listar(Pageable pageable);

    PagedModel<EntityModel<EquipamentoResponseDTO>> buscarPorNome(String nome, Pageable pageable);

    EquipamentoResponseDTO buscarPorId(Long id);

    EquipamentoResponseDTO cadastrar(EquipamentoRequestDTO equipamentoRequestDTO);

    EquipamentoResponseDTO atualizar(EquipamentoRequestDTO equipamentoRequestDTO);

    void excluir(Long id);

    // ---------------------------------------------------------------------------------

    Resource exportarPagina(Pageable pageable, String acceptHeader);

    Resource exportEquipamento(Long id, String acceptHeader);

    List<EquipamentoResponseDTO> massCreation(MultipartFile file);


}