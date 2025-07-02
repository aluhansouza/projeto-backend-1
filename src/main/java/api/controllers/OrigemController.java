package api.controllers;

import api.controllers.docs.CategoriaControllerDocs;
import api.controllers.docs.OrigemControllerDocs;
import api.dto.request.CategoriaRequestDTO;
import api.dto.request.OrigemRequestDTO;
import api.dto.response.CategoriaResponseDTO;
import api.dto.response.OrigemResponseDTO;
import api.services.interfaces.CategoriaService;
import api.services.interfaces.OrigemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/origens")
@Tag(name = "Origens", description = "Endpoints para Categorias")
public class OrigemController implements OrigemControllerDocs {

    @Autowired
    private OrigemService origemService;

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<OrigemResponseDTO>>> listar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "200000000") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(origemService.listar(pageable));
    }

    @GetMapping(value = "/buscarPorNome/{nome}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<OrigemResponseDTO>>> buscarPorNome(
            @PathVariable("nome") String nome,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "200000000") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(origemService.buscarPorNome(nome, pageable));
    }

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public OrigemResponseDTO buscarPorId(@PathVariable("id") Long id) {
        return origemService.buscarPorId(id);
    }

    @PostMapping(consumes = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})
    @Override
    public OrigemResponseDTO cadastrar(@Valid @RequestBody OrigemRequestDTO requestDTO) {
        return origemService.cadastrar(requestDTO);
    }

    @PutMapping(value = "/{id}",
            consumes = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE},
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE})
    @Override
    public OrigemResponseDTO atualizar(@PathVariable("id") Long id,
                                      @Valid @RequestBody OrigemRequestDTO requestDTO) {
        return origemService.atualizar(id, requestDTO);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        origemService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verificar-conexao")
    public ResponseEntity<String> verificarConexao() {
        return ResponseEntity.ok("Conexão com Setor OK");
    }
}
