package api.unittests.mapper.mocks;

import api.dto.EquipamentoDTO;
import api.entity.Equipamento;

import java.util.ArrayList;
import java.util.List;

public class MockEquipamento {

    public Equipamento mockEntity() {
        return mockEntity(0);
    }

    public EquipamentoDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Equipamento> mockEntityList() {
        List<Equipamento> equipamentos = new ArrayList<Equipamento>();
        for (int i = 0; i < 14; i++) {
            equipamentos.add(mockEntity(i));
        }
        return equipamentos;
    }

    public List<EquipamentoDTO> mockDTOList() {
        List<EquipamentoDTO> equipamentos = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            equipamentos.add(mockDTO(i));
        }
        return equipamentos;
    }

    public Equipamento mockEntity(Integer number) {
        Equipamento equipamento = new Equipamento();
        equipamento.setId(number.longValue());
        equipamento.setNome("Nome" + number);
        return equipamento;
    }

    public EquipamentoDTO mockDTO(Integer number) {
        EquipamentoDTO equipamentoDTO = new EquipamentoDTO();
        equipamentoDTO.setId(number.longValue());
        equipamentoDTO.setNome("Nome" + number);
        return equipamentoDTO;
    }




}
