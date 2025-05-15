package api.service;

import api.entity.Equipamento;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EquipamentoService {

    List<Equipamento> listar();

    Equipamento buscarPorId(Long id);

    Equipamento cadastrar(Equipamento equipamento);

    Equipamento atualizar(Equipamento equipamento);

    void excluir(Long id);





}