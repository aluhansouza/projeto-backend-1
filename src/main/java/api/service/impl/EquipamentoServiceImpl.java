package api.service.impl;

import api.controller.EquipamentoController;
import api.dto.EquipamentoDTO;
import api.entity.Equipamento;
import api.exception.RequiredObjectIsNullException;
import api.exception.ResourceNotFoundException;
import api.repository.EquipamentoRepository;
import api.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.logging.Logger;

import static api.mapper.ObjectMapper.parseListObjects;
import static api.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EquipamentoServiceImpl implements EquipamentoService {

    private Logger logger = Logger.getLogger(EquipamentoService.class.getName());

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Transactional(readOnly = true)
    public List<EquipamentoDTO> listar() {

        logger.info("Finding all People!");

        var equipamentos = parseListObjects(equipamentoRepository.findAll(), EquipamentoDTO.class);
        equipamentos.forEach(this::addHateoasLinks);
        return equipamentos;
    }

    @Transactional(readOnly = true)
    public EquipamentoDTO buscarPorId(Long id) {
        logger.info("Finding one Person!");

        var entity = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto =  parseObject(entity, EquipamentoDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public EquipamentoDTO cadastrar(EquipamentoDTO equipamentoDTO) {


        if (equipamentoDTO == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person!");
        var entity = parseObject(equipamentoDTO, Equipamento.class);

        var dto = parseObject(equipamentoRepository.save(entity), EquipamentoDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public EquipamentoDTO atualizar(EquipamentoDTO equipamentoDTO) {

        if (equipamentoDTO == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Person!");
        Equipamento equipamento = equipamentoRepository.findById(equipamentoDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        equipamento.setNome(equipamentoDTO.getNome());

        var dto = parseObject(equipamentoRepository.save(equipamento), EquipamentoDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Transactional
    public void excluir(Long id) {

        logger.info("Deleting one Person!");

        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        equipamentoRepository.delete(equipamento);
    }



    private void addHateoasLinks(EquipamentoDTO dto) {
        dto.add(linkTo(methodOn(EquipamentoController.class).buscarPorId(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(EquipamentoController.class).listar()).withRel("listar").withType("GET"));
        dto.add(linkTo(methodOn(EquipamentoController.class).cadastrar(dto)).withRel("cadastrar").withType("POST"));
        dto.add(linkTo(methodOn(EquipamentoController.class).atualizar(dto)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(EquipamentoController.class).excluir(dto.getId())).withRel("excluir").withType("DELETE"));
    }


}
