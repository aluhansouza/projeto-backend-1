package api.controller;


import api.dto.EquipamentoDTO;
import api.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipamentos")
public class EquipamentoController {


    @Autowired
    private EquipamentoService equipamentoService;


    @GetMapping(produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_YAML_VALUE})
    public List<EquipamentoDTO> listar() {
        return equipamentoService.listar();
    }

    @GetMapping(value = "/{id}",
            produces = {
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_YAML_VALUE}
    )
    public EquipamentoDTO buscarPorId(@PathVariable("id") Long id) {
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
    public EquipamentoDTO cadastrar(@RequestBody EquipamentoDTO equipamentoDTO) {
        return equipamentoService.cadastrar(equipamentoDTO);
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
    public EquipamentoDTO atualizar(@RequestBody EquipamentoDTO equipamentoDTO) {
        return equipamentoService.atualizar(equipamentoDTO);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        equipamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }


}
