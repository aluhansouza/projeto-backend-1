package api.services.impl;

import api.controllers.EquipamentoController;
import api.dto.EquipamentoRequestDTO;
import api.dto.EquipamentoResponseDTO;
import api.entity.Equipamento;
import api.exceptions.BadRequestException;
import api.exceptions.FileStorageException;
import api.exceptions.RequiredObjectIsNullException;
import api.exceptions.ResourceNotFoundException;
import api.file.exporter.contract.EquipamentoExporter;
import api.file.exporter.factory.FileExporterFactory;
import api.file.importer.contract.FileImporter;
import api.file.importer.factory.FileImporterFactory;
import api.repository.EquipamentoRepository;
import api.services.interfaces.EquipamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import static api.mapper.ObjectMapper.parseObject;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EquipamentoServiceImpl implements EquipamentoService {

    private Logger logger = LoggerFactory.getLogger(EquipamentoServiceImpl.class.getName());

    @Autowired
    EquipamentoRepository equipamentoRepository;

    @Autowired
    FileImporterFactory importer;

    @Autowired
    FileExporterFactory exporter;


    @Autowired
    PagedResourcesAssembler<EquipamentoResponseDTO> assembler;

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<EquipamentoResponseDTO>> listar(Pageable pageable) {

        logger.info("Finding all Equipamentos!");

        var equipamentos = equipamentoRepository.findAll(pageable);
        return buildPagedModel(pageable, equipamentos);
    }

    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<EquipamentoResponseDTO>> buscarPorNome(String nome, Pageable pageable) {

        logger.info("Finding Equipamento by name!");

        var people = equipamentoRepository.buscarPorNome(nome, pageable);
        return buildPagedModel(pageable, people);
    }

    @Override
    @Transactional(readOnly = true)
    public EquipamentoResponseDTO buscarPorId(Long id) {
        logger.info("Finding one Person!");

        var entity = equipamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = parseObject(entity, EquipamentoResponseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
    @Transactional
    public EquipamentoResponseDTO cadastrar(EquipamentoRequestDTO equipamentoRequestDTO) {

        if (equipamentoRequestDTO == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Criando novo Equipamento!");

        var entity = parseObject(equipamentoRequestDTO, Equipamento.class);

        var equipamentoSalvo = equipamentoRepository.save(entity);

        // 3) Converte a entidade salva (com ID) para o DTO de resposta
        var dto = parseObject(equipamentoSalvo, EquipamentoResponseDTO.class);

        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/equipamentos/{id}")
                .buildAndExpand(equipamentoSalvo.getId())
                .toUriString();
        dto.setQrcodeValor(url);

        logger.debug("Equipamento persistido com ID={} e qrcodevalor={}", dto.getId(), dto.getQrcodeValor());

        addHateoasLinks(dto);

        return dto;

    }

    @Override
    @Transactional
    public EquipamentoResponseDTO atualizar(EquipamentoRequestDTO equipamentoRequestDTO) {

        if (equipamentoRequestDTO == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one Person!");
        Equipamento equipamento = equipamentoRepository.findById(equipamentoRequestDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        equipamento.setNome(equipamentoRequestDTO.getNome());

        var dto = parseObject(equipamentoRepository.save(equipamento), EquipamentoResponseDTO.class);
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


// ---------------------------------------------------------------------------------------------------------------//


    @Override
    public Resource exportarPagina(Pageable pageable, String acceptHeader) {
        logger.info("Exporting a Equipamento page!");

        var equipamentos = equipamentoRepository.findAll(pageable)
                .map(equipamento -> parseObject(equipamento, EquipamentoResponseDTO.class))
                .getContent();

        try {
            EquipamentoExporter exporter = this.exporter.getExporter(acceptHeader);
            return exporter.exportEquipamentos(equipamentos);
        } catch (Exception e) {
            throw new RuntimeException("Error during file export!", e);
        }
    }

    public Resource exportEquipamento(Long id, String acceptHeader) {
        logger.info("Exporting data of one Equipamento!");

        var equipamento = equipamentoRepository.findById(id)
                .map(entity -> parseObject(entity, EquipamentoResponseDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        try {
            EquipamentoExporter exporter = this.exporter.getExporter(acceptHeader);
            return exporter.exportEquipamento(equipamento);
        } catch (Exception e) {
            throw new RuntimeException("Error during file export!", e);
        }
    }


    @Override
    public List<EquipamentoResponseDTO> massCreation(MultipartFile file) {
        logger.info("Importing People from file!");

        if (file.isEmpty()) throw new BadRequestException("Please set a Valid File!");

        try (InputStream inputStream = file.getInputStream()) {
            String filename = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new BadRequestException("File name cannot be null"));
            FileImporter importer = this.importer.getImporter(filename);

            List<Equipamento> entities = importer.importFile(inputStream).stream()
                    .map(dto -> equipamentoRepository.save(parseObject(dto, Equipamento.class)))
                    .toList();

            return entities.stream()
                    .map(entity -> {
                        var dto = parseObject(entity, EquipamentoResponseDTO.class);
                        addHateoasLinks(dto);
                        return dto;
                    })
                    .toList();
        } catch (Exception e) {
            throw new FileStorageException("Error processing the file!");
        }
    }


    private PagedModel<EntityModel<EquipamentoResponseDTO>> buildPagedModel(Pageable pageable, Page<Equipamento> equipamentos) {

        var equipamentosWithLinks = equipamentos.map(equipamento -> {
            var dto = parseObject(equipamento, EquipamentoResponseDTO.class);
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


// ---------------------------------------------------------------------------------------------------------------//

    private void addHateoasLinks(EquipamentoResponseDTO dto) {
        dto.add(linkTo(methodOn(EquipamentoController.class).listar(1, 12, "asc")).withRel("listar").withType("GET"));
        dto.add(linkTo(methodOn(EquipamentoController.class).buscarPorNome("", 1, 12, "asc")).withRel("buscarPorNome").withType("GET"));
        dto.add(linkTo(methodOn(EquipamentoController.class).buscarPorId(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(EquipamentoController.class).cadastrar((EquipamentoRequestDTO) null)).withRel("cadastrar").withType("POST"));
        dto.add(linkTo(methodOn(EquipamentoController.class)).slash("massCreation").withRel("massCreation").withType("POST"));
        dto.add(linkTo(methodOn(EquipamentoController.class).atualizar((EquipamentoRequestDTO) null)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(EquipamentoController.class).excluir(dto.getId())).withRel("excluir").withType("DELETE"));

        dto.add(linkTo(methodOn(EquipamentoController.class)
                .exportarPagina(
                        1, 12, "asc", null))
                .withRel("exportarPagina")
                .withType("GET")
                .withTitle("Exportar Equipamentos")
        );
    }



    // ---------------------------------------------------------------------------------//




}



