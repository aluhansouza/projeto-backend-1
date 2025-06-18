package api.services.impl;

import api.controllers.MaterialController;
import api.dto.request.MaterialRequestDTO;
import api.dto.response.MaterialResponseDTO;
import api.entity.Categoria;
import api.entity.Material;
import api.entity.Origem;
import api.entity.Setor;
import api.exceptions.BadRequestException;
import api.exceptions.FileStorageException;
import api.exceptions.RequiredObjectIsNullException;
import api.exceptions.ResourceNotFoundException;
import api.file.exporter.contract.MaterialExporter;
import api.file.exporter.factory.FileExporterFactory;
import api.file.importer.contract.MaterialImporter;
import api.file.importer.factory.FileImporterFactory;
import api.repository.MaterialRepository;
import api.services.interfaces.MaterialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
public class MaterialServiceImpl implements MaterialService {

    private Logger logger = LoggerFactory.getLogger(MaterialServiceImpl.class.getName());

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    FileImporterFactory importer;

    @Autowired
    FileExporterFactory exporter;


    @Autowired
    PagedResourcesAssembler<MaterialResponseDTO> assembler;

    //@Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<MaterialResponseDTO>> listar(Pageable pageable) {

        logger.info("Finding all Materiais!");

        var materiais = materialRepository.findAll(pageable);

        List<MaterialResponseDTO> dtos = materiais.getContent().stream()
                .map(material ->{MaterialResponseDTO dto = new MaterialResponseDTO();

                    dto.setId(material.getId());
                    dto.setNome(material.getNome());

                    if(material.getCategoria() != null) {
                        dto.setCategoriaId(material.getCategoria().getId());
                        System.out.println("ID da Categoria: "+ dto.getCategoriaId());
                    }

                    if(material.getSetor() != null) {
                        dto.setSetorId(material.getSetor().getId());
                        System.out.println("ID do Setor: "+ dto.getSetorId());
                    }

                    if(material.getOrigem() != null) {
                        dto.setOrigemId(material.getOrigem().getId());
                        System.out.println("ID da Origem: "+ dto.getOrigemId());
                    }

                    return dto;}).toList();

        return buildPagedModel(pageable, materiais);
        //return buildPagedModel(pageable, new PageImpl<>(dtos, pageable, materiais.getTotalElements()));
    }


    @Override
    @Transactional(readOnly = true)
    public PagedModel<EntityModel<MaterialResponseDTO>> buscarPorNome(String nome, Pageable pageable) {

        logger.info("Finding Material by name!");



        var people = materialRepository.buscarPorNome(nome, pageable);
        return buildPagedModel(pageable, people);
    }

    @Override
    @Transactional(readOnly = true)
    public MaterialResponseDTO buscarPorId(Long id) {
        logger.info("Finding one Person!");

        var entity = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        var dto = parseObject(entity, MaterialResponseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
    @Transactional
    public MaterialResponseDTO cadastrar(MaterialRequestDTO materialRequestDTO) {

        if (materialRequestDTO == null) {
            throw new RequiredObjectIsNullException();
        }

        logger.info("Criando novo Material!");

        var entity = parseObject(materialRequestDTO, Material.class);

        // Associando Setor
        if (materialRequestDTO.getSetorId() != null) {
            Setor setor = new Setor();
            setor.setId(materialRequestDTO.getSetorId());
            System.out.println("ID do Setor: "+ materialRequestDTO.getSetorId());
            entity.setSetor(setor);
        }

        // Associando Categoria
        if (materialRequestDTO.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(materialRequestDTO.getCategoriaId());
            System.out.println("ID da Categoria: "+ materialRequestDTO.getCategoriaId());
            entity.setCategoria(categoria);
        }

        // Associando Origem
        if (materialRequestDTO.getOrigemId() != null) {
            Origem origem = new Origem();
            origem.setId(materialRequestDTO.getOrigemId());
            System.out.println("ID da Origem: "+ materialRequestDTO.getOrigemId());
            entity.setOrigem(origem);
        }

        var materialSalvo = materialRepository.save(entity);

        System.out.println("QRCODEVALOR: "+materialRequestDTO.getQrValor());

        var entidade = materialRepository.findById(materialSalvo.getId()).orElseThrow();


        String url = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/materiais/{id}")
                .buildAndExpand(materialSalvo.getId())
                .toUriString();
        entidade.setQrValor(url);
        entidade = materialRepository.save(entity);
        var dto = parseObject(entidade, MaterialResponseDTO.class);

        System.out.println("QRCODEVALOR: "+materialRequestDTO.getQrValor());

        logger.debug("Material persistido com ID={} e qrcodevalor={}", dto.getId(), dto.getQrValor());

        addHateoasLinks(dto);

        return dto;

    }

    @Override
    @Transactional
    public MaterialResponseDTO atualizar(MaterialRequestDTO materialRequestDTO) {

        if (materialRequestDTO == null) throw new RequiredObjectIsNullException();

        logger.info("Updating one material!");
        Material material = materialRepository.findById(materialRequestDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        // Associando Setor
        if (materialRequestDTO.getSetorId() != null) {
            Setor setor = new Setor();
            setor.setId(materialRequestDTO.getSetorId());
            System.out.println("ID do Setor: "+ materialRequestDTO.getSetorId());
            material.setSetor(setor);
        }

        // Associando Categoria
        if (materialRequestDTO.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(materialRequestDTO.getCategoriaId());
            System.out.println("ID da Categoria: "+ materialRequestDTO.getCategoriaId());
            material.setCategoria(categoria);
        }

        // Associando Origem
        if (materialRequestDTO.getOrigemId() != null) {
            Origem origem = new Origem();
            origem.setId(materialRequestDTO.getOrigemId());
            System.out.println("ID da Origem: "+ materialRequestDTO.getOrigemId());
            material.setOrigem(origem);
        }


        material.setNome(materialRequestDTO.getNome());

        Material.Situacao enumSituacao = Material.Situacao.valueOf(materialRequestDTO.getSituacao().name());
        material.setSituacao(enumSituacao);

        material.setPatrimonio(materialRequestDTO.getPatrimonio());
        material.setLocalizacaoFisica(materialRequestDTO.getLocalizacaoFisica());
        material.setDataAquisicao(materialRequestDTO.getDataAquisicao());
        material.setDescricao(materialRequestDTO.getDescricao());
        material.setValorCompra(materialRequestDTO.getValorCompra());
        material.setIdentificacaoRecibo(materialRequestDTO.getIdentificacaoRecibo());

        Material.TipoDepreciacao enumTipoDepreciacao = Material.TipoDepreciacao.valueOf(materialRequestDTO.getTipoDepreciacao().name());
        material.setTipoDepreciacao(enumTipoDepreciacao);

        material.setPercentualDepreciacao(materialRequestDTO.getPercentualDepreciacao());
        material.setVidaUtilAnos(materialRequestDTO.getVidaUtilAnos());
        material.setValorAtual(materialRequestDTO.getValorAtual());

        //material.setTipoDepreciacao(Material.TipoDepreciacao.LINEAR);
        //material.setSituacao(Material.Situacao.EMPRESTADO);

        var dto = parseObject(materialRepository.save(material), MaterialResponseDTO.class);
        addHateoasLinks(dto);
        return dto;
    }

    @Override
    @Transactional
    public void excluir(Long id) {

        logger.info("Deleting one Material!");

        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        materialRepository.delete(material);
    }


// ---------------------------------------------------------------------------------------------------------------//


    @Override
    public Resource exportarPagina(Pageable pageable, String acceptHeader) {
        logger.info("Exporting a Material page!");

        var materiais = materialRepository.findAll(pageable)
                .map(equipamento -> parseObject(equipamento, MaterialResponseDTO.class))
                .getContent();

        try {
            MaterialExporter exporter = this.exporter.getExporter(acceptHeader);
            return exporter.exportMateriais(materiais);
        } catch (Exception e) {
            throw new RuntimeException("Error during file export!", e);
        }
    }

    public Resource exportMaterial(Long id, String acceptHeader) {
        logger.info("Exporting data of one Material!");

        var material = materialRepository.findById(id)
                .map(entity -> parseObject(entity, MaterialResponseDTO.class))
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        try {
            MaterialExporter exporter = this.exporter.getExporter(acceptHeader);
            return exporter.exportMaterial(material);
        } catch (Exception e) {
            throw new RuntimeException("Error during file export!", e);
        }
    }


    @Override
    public List<MaterialResponseDTO> massCreation(MultipartFile file) {
        logger.info("Importing People from file!");

        if (file.isEmpty()) throw new BadRequestException("Please set a Valid File!");

        try (InputStream inputStream = file.getInputStream()) {
            String filename = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(() -> new BadRequestException("File name cannot be null"));
            MaterialImporter importer = this.importer.getImporter(filename);

            List<Material> entities = importer.importFile(inputStream).stream()
                    .map(dto -> materialRepository.save(parseObject(dto, Material.class)))
                    .toList();

            return entities.stream()
                    .map(entity -> {
                        var dto = parseObject(entity, MaterialResponseDTO.class);
                        addHateoasLinks(dto);
                        return dto;
                    })
                    .toList();
        } catch (Exception e) {
            throw new FileStorageException("Error processing the file!");
        }
    }


    private PagedModel<EntityModel<MaterialResponseDTO>> buildPagedModel(Pageable pageable, Page<Material> materiais) {

        var materiaisWithLinks = materiais.map(material -> {
            var dto = parseObject(material, MaterialResponseDTO.class);
            dto.setCategoriaId(material.getCategoria() != null ? material.getCategoria().getId() : null);
            dto.setSetorId(material.getSetor() != null ? material.getSetor().getId() : null);
            dto.setOrigemId(material.getOrigem() != null ? material.getOrigem().getId() : null);
            addHateoasLinks(dto);
            return dto;
        });

        Link findAllLink = WebMvcLinkBuilder.linkTo(
                        WebMvcLinkBuilder.methodOn(MaterialController.class)
                                .listar(
                                        pageable.getPageNumber(),
                                        pageable.getPageSize(),
                                        String.valueOf(pageable.getSort())))
                .withSelfRel();
        return assembler.toModel(materiaisWithLinks, findAllLink);
    }


// ---------------------------------------------------------------------------------------------------------------//

    private void addHateoasLinks(MaterialResponseDTO dto) {
        dto.add(linkTo(methodOn(MaterialController.class).listar(1, 12, "asc")).withRel("listar").withType("GET"));
        dto.add(linkTo(methodOn(MaterialController.class).buscarPorNome("", 1, 12, "asc")).withRel("buscarPorNome").withType("GET"));
        dto.add(linkTo(methodOn(MaterialController.class).buscarPorId(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(MaterialController.class).cadastrar((MaterialRequestDTO) null)).withRel("cadastrar").withType("POST"));
        dto.add(linkTo(methodOn(MaterialController.class)).slash("massCreation").withRel("massCreation").withType("POST"));
        dto.add(linkTo(methodOn(MaterialController.class).atualizar((MaterialRequestDTO) null)).withRel("atualizar").withType("PUT"));
        dto.add(linkTo(methodOn(MaterialController.class).excluir(dto.getId())).withRel("excluir").withType("DELETE"));

        dto.add(linkTo(methodOn(MaterialController.class)
                .exportarPagina(
                        1, 12, "asc", null))
                .withRel("exportarPagina")
                .withType("GET")
                .withTitle("Exportar Materiais")
        );
    }



    // ---------------------------------------------------------------------------------//




}



