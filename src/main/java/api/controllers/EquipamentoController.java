package api.controllers;

import api.controllers.docs.EquipamentoControllerDocs;
import api.dto.EquipamentoRequestDTO;
import api.dto.EquipamentoResponseDTO;
import api.file.exporter.MediaTypes;
import api.services.interfaces.EquipamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/equipamentos")
@Tag(name = "Equipamentos", description = "Endpoints para Equipamentos")
public class EquipamentoController implements EquipamentoControllerDocs {


    @Autowired
    private EquipamentoService equipamentoService;


    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<EquipamentoResponseDTO>>> listar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(equipamentoService.listar(pageable));
    }


    @GetMapping(value = "/buscarPorNome/{nome}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<EquipamentoResponseDTO>>> buscarPorNome(
            @PathVariable("nome") String nome,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(equipamentoService.buscarPorNome(nome, pageable));
    }


    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public EquipamentoResponseDTO buscarPorId(@PathVariable("id") Long id) {
        return equipamentoService.buscarPorId(id);
    }


    @PostMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public EquipamentoResponseDTO cadastrar(@RequestBody EquipamentoRequestDTO equipamentoRequestDTO) {
        return equipamentoService.cadastrar(equipamentoRequestDTO);
    }

    @PutMapping(
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public EquipamentoResponseDTO atualizar(@RequestBody EquipamentoRequestDTO equipamentoRequestDTO) {
        return equipamentoService.atualizar(equipamentoRequestDTO);
    }


    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        equipamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }


    // -----------------------------------------------------------------------


    @GetMapping(value = "/exportarPagina", produces = {
            MediaTypes.APPLICATION_XLSX_VALUE,
            MediaTypes.APPLICATION_CSV_VALUE,
            MediaTypes.APPLICATION_PDF_VALUE})
    @Override
    public ResponseEntity<Resource> exportarPagina(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            HttpServletRequest request
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));

        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);

        Resource file = equipamentoService.exportarPagina(pageable, acceptHeader);

        // 1. Obter o timestamp atual e formatar como string (ex.: 20250601_153045)
        LocalDateTime agora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timestamp = agora.format(formatter);

        Map<String, String> extensionMap = Map.of(
                MediaTypes.APPLICATION_XLSX_VALUE, ".xlsx",
                MediaTypes.APPLICATION_CSV_VALUE, ".csv",
                MediaTypes.APPLICATION_PDF_VALUE, ".pdf"
        );

        var fileExtension = extensionMap.getOrDefault(acceptHeader, "");
        var contentType = acceptHeader != null ? acceptHeader : "application/octet-stream";

        var filename = "equipamento_exportado_" + timestamp + fileExtension;

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .body(file);
    }

    @PostMapping(value = "/massCreation",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    @Override
    public List<EquipamentoResponseDTO> massCreation(@RequestParam("file") MultipartFile file) {
        return equipamentoService.massCreation(file);
    }

    @GetMapping(value = "/exportar/{id}",
            produces = {
                    MediaType.APPLICATION_PDF_VALUE}
    )
    @Override
    public ResponseEntity<Resource> exportar(Long id, HttpServletRequest request) {
        String acceptHeader = request.getHeader(HttpHeaders.ACCEPT);
        Resource file = equipamentoService.exportEquipamento(id, acceptHeader);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(acceptHeader))
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=person.pdf")
                .body(file);
    }


}
