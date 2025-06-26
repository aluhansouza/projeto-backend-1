package api.controllers;

import api.controllers.docs.CategoriaControllerDocs;
import api.controllers.docs.SetorControllerDocs;
import api.dto.request.CategoriaRequestDTO;
import api.dto.request.SetorRequestDTO;
import api.dto.response.CategoriaResponseDTO;
import api.dto.response.SetorResponseDTO;
import api.services.interfaces.CategoriaService;
import api.services.interfaces.SetorService;
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
@RequestMapping("/api/v1/categorias")
@Tag(name = "Categorias", description = "Endpoints para Categorias")
public class CategoriaController implements CategoriaControllerDocs {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<CategoriaResponseDTO>>> listar(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(categoriaService.listar(pageable));
    }

    @GetMapping(value = "/buscarPorNome/{nome}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public ResponseEntity<PagedModel<EntityModel<CategoriaResponseDTO>>> buscarPorNome(
            @PathVariable("nome") String nome,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        Direction sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        return ResponseEntity.ok(categoriaService.buscarPorNome(nome, pageable));
    }

    @GetMapping(value = "/{id}", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    @Override
    public CategoriaResponseDTO buscarPorId(@PathVariable("id") Long id) {
        return categoriaService.buscarPorId(id);
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
    public CategoriaResponseDTO cadastrar(@Valid @RequestBody CategoriaRequestDTO requestDTO) {
        return categoriaService.cadastrar(requestDTO);
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
    public CategoriaResponseDTO atualizar(@PathVariable("id") Long id,
                                      @Valid @RequestBody CategoriaRequestDTO requestDTO) {
        return categoriaService.atualizar(id, requestDTO);
    }

    @DeleteMapping(value = "/{id}")
    @Override
    public ResponseEntity<Void> excluir(@PathVariable("id") Long id) {
        categoriaService.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/verificar-conexao")
    public ResponseEntity<String> verificarConexao() {
        return ResponseEntity.ok("Conexão com Setor OK");
    }
}
