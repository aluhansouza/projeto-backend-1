package api.unittests.mapper;

import api.dto.request.EquipamentoRequestDTO;
import api.entity.Equipamento;
import api.unittests.mapper.mocks.MockEquipamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static api.mapper.ObjectMapper.parseListObjects;
import static api.mapper.ObjectMapper.parseObject;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectMapperTests {

    MockEquipamento inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockEquipamento();
    }

    @Test
    public void parseEntityToDTOTest() {
        EquipamentoRequestDTO output = parseObject(inputObject.mockEntity(), EquipamentoRequestDTO.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Nome0", output.getNome());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        List<EquipamentoRequestDTO> outputList = parseListObjects(inputObject.mockEntityList(), EquipamentoRequestDTO.class);
        EquipamentoRequestDTO outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Nome0", outputZero.getNome());

        EquipamentoRequestDTO outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Nome7", outputSeven.getNome());

        EquipamentoRequestDTO outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Nome12", outputTwelve.getNome());
    }

    @Test
    public void parseDTOToEntityTest() {
        Equipamento output = parseObject(inputObject.mockDTO(), Equipamento.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Nome0", output.getNome());
    }

    @Test
    public void parserDTOListToEntityListTest() {
        List<Equipamento> outputList = parseListObjects(inputObject.mockDTOList(), Equipamento.class);
        Equipamento outputZero = outputList.get(0);

        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Nome0", outputZero.getNome());

        Equipamento outputSeven = outputList.get(7);

        assertEquals(Long.valueOf(7L), outputSeven.getId());
        assertEquals("Nome7", outputSeven.getNome());

        Equipamento outputTwelve = outputList.get(12);

        assertEquals(Long.valueOf(12L), outputTwelve.getId());
        assertEquals("Nome12", outputTwelve.getNome());
    }
}
