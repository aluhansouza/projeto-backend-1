package api.service.impl;

import api.entity.Equipamento;
import api.exception.ResourceNotFoundException;
import api.repository.EquipamentoRepository;
import api.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

@Service
public class EquipamentoServiceImpl implements EquipamentoService {

    private Logger logger = Logger.getLogger(EquipamentoService.class.getName());

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Equipamento> listar() {
        logger.info("Finding all People!");

        return equipamentoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Equipamento buscarPorId(Long id) {
        logger.info("Finding one Person!");

        return equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
    }

    @Override
    @Transactional
    public Equipamento cadastrar(Equipamento equipamento) {
        logger.info("Creating one Person!");

        return equipamentoRepository.save(equipamento);
    }

    @Override
    @Transactional
    public Equipamento atualizar(Equipamento equipamento) {
        logger.info("Updating one Person!");
        Equipamento equipamento_atualizado = equipamentoRepository.findById(equipamento.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        equipamento_atualizado.setNome(equipamento.getNome());

        return equipamentoRepository.save(equipamento);
    }

    @Override
    @Transactional
    public void excluir(Long id) {
        logger.info("Deleting one Person!");

        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        equipamentoRepository.delete(equipamento);

    }


}
