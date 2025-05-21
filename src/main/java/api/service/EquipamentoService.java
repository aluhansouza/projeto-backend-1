package api.service;

import api.dto.EquipamentoDTO;
import api.entity.Equipamento;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EquipamentoService {

    List<EquipamentoDTO> listar();

    EquipamentoDTO buscarPorId(Long id);

    EquipamentoDTO cadastrar(EquipamentoDTO equipamentoDTO);

    EquipamentoDTO atualizar(EquipamentoDTO equipamentoDTO);

    void excluir(Long id);





}