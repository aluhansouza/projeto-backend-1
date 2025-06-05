package api.unittests.mapper.mocks;

import api.dto.EquipamentoRequestDTO;
import api.entity.Equipamento;

import java.util.ArrayList;
import java.util.List;

public class MockEquipamento {

    public Equipamento mockEntity() {
        return mockEntity(0);
    }

    public EquipamentoRequestDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Equipamento> mockEntityList() {
        List<Equipamento> equipamentos = new ArrayList<Equipamento>();
        for (int i = 0; i < 14; i++) {
            equipamentos.add(mockEntity(i));
        }
        return equipamentos;
    }

    public List<EquipamentoRequestDTO> mockDTOList() {
        List<EquipamentoRequestDTO> equipamentos = new ArrayList<>();
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

    public EquipamentoRequestDTO mockDTO(Integer number) {
        EquipamentoRequestDTO equipamentoRequestDTO = new EquipamentoRequestDTO();
        equipamentoRequestDTO.setId(number.longValue());
        equipamentoRequestDTO.setNome("Nome" + number);
        return equipamentoRequestDTO;
    }




}
