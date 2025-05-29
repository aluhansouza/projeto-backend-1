package api.service.impl;

import api.controller.EquipamentoController;
import api.dto.EquipamentoDTO;
import api.entity.Equipamento;
import api.exception.RequiredObjectIsNullException;
import api.exception.ResourceNotFoundException;
import api.repository.EquipamentoRepository;
import api.service.EquipamentoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static api.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EquipamentoServiceImpl implements EquipamentoService {

    private Logger logger = LoggerFactory.getLogger(EquipamentoServiceImpl.class.getName());

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    PagedResourcesAssembler<EquipamentoDTO> assembler;


    @Override
    @Transactional
    public PagedModel<EntityModel<EquipamentoDTO>> listar(Pageable pageable) {
        logger.info("listar Equipamentos!");

        var equipamentos = equipamentoRepository.findAll(pageable);

        var equipamentosWithLinks = equipamentos.map(equipamento -> {
            var dto = parseObject(equipamento, EquipamentoDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(EquipamentoController.class)
                                .listar(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();
        return assembler.toModel(equipamentosWithLinks, findAllLink);
    }

    @Override
    @Transactional
    public PagedModel<EntityModel<EquipamentoDTO>> buscarPorNome(String nome, Pageable pageable) {
        logger.info("Buscar Equipamento por Nome!");

        var equipamentos = equipamentoRepository.buscarPorNome(nome, pageable);

        var equipamentosWithLinks = equipamentos.map(equipamento -> {
            var dto = parseObject(equipamento, EquipamentoDTO.class);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                        methodOn(EquipamentoController.class)
                                .listar(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();
        return assembler.toModel(equipamentosWithLinks, findAllLink);
    }

    @Override
    @Transactional
    public EquipamentoDTO buscarPorId(Long id) {
        logger.info("Finding one Person!");

        var entity = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = parseObject(entity, EquipamentoDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
    @Transactional
    public EquipamentoDTO cadastrar(EquipamentoDTO equipamentoDTO) {


        if (equipamentoDTO == null) throw new RequiredObjectIsNullException();

        logger.info("Creating one Person!");
        var entity = parseObject(equipamentoDTO, Equipamento.class);

        var dto = parseObject(equipamentoRepository.save(entity), EquipamentoDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
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

    @Override
    @Transactional
    public void excluir(Long id) {

        logger.info("Deleting one Person!");

        Equipamento equipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        equipamentoRepository.delete(equipamento);
    }


    private void addHateoasLinks(EquipamentoDTO dto) {
        dto.add(linkTo(methodOn(EquipamentoController.class).buscarPorId(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(EquipamentoController.class).listar(1, 12, "asc")).withRel("listar").withType("GET"));
        dto.add(linkTo(methodOn(EquipamentoController.class).cadastrar(dto)).withRel("cadastrar").withType("POST"));
        dto.add(linkTo(methodOn(EquipamentoController.class).atualizar(dto)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(EquipamentoController.class).excluir(dto.getId())).withRel("excluir").withType("DELETE"));
    }


}
