package api.controller;


import api.entity.Equipamento;
import api.service.EquipamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentoController {


    @Autowired
    private EquipamentoService equipamentoService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Equipamento> listar() {
        return equipamentoService.listar();
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Equipamento buscarPorId(@PathVariable("id") Long id) {
        return equipamentoService.buscarPorId(id);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Equipamento cadastrar(@RequestBody Equipamento equipamento) {
        return equipamentoService.cadastrar(equipamento);
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Equipamento atualizar(@RequestBody Equipamento equipamento) {
        return equipamentoService.atualizar(equipamento);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> excluir(@PathVariable("id") Long id) {
        equipamentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }


}
