package api.unittests.service.impl;

import api.dto.request.EquipamentoRequestDTO;
import api.entity.Equipamento;
import api.exceptions.RequiredObjectIsNullException;
import api.repository.EquipamentoRepository;
import api.services.impl.EquipamentoServiceImpl;
import api.unittests.mapper.mocks.MockEquipamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class EquipamentoServiceTest {

    MockEquipamento input;

    @InjectMocks
    private EquipamentoServiceImpl equipamentoService;

    @Mock
    EquipamentoRepository equipamentoRepository;

    @BeforeEach
    void setUp() {
        input = new MockEquipamento();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void buscarPorId() {

        Equipamento equipamento = input.mockEntity(1);
        equipamento.setId(1L);
        when(equipamentoRepository.findById(1L)).thenReturn(Optional.of(equipamento));

        var resultado = equipamentoService.buscarPorId(1L);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getLinks());

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/v1/equipamentos/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("listar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("GET")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("cadastrar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("POST")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("atualizar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("excluir")
                        && link.getHref().endsWith("/api/v1/equipamentos/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Nome1", resultado.getNome());

    }

    @Test
    void cadastrar() {

        Equipamento equipamento = input.mockEntity(1);
        Equipamento equipamento_persistido = equipamento;
        equipamento_persistido.setId(1L);

        EquipamentoRequestDTO dto = input.mockDTO(1);

        when(equipamentoRepository.save(equipamento)).thenReturn(equipamento_persistido);

        var resultado = equipamentoService.cadastrar(dto);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getLinks());

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/v1/equipamentos/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("listar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("GET")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("cadastrar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("POST")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("atualizar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("excluir")
                        && link.getHref().endsWith("/api/v1/equipamentos/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Nome1", resultado.getNome());


    }

    @Test
    void testCadastrarWithNullEquipamento() {
        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    equipamentoService.cadastrar(null);
                });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }



    @Test
    void atualizar() {

        Equipamento equipamento = input.mockEntity(1);
        Equipamento equipamento_persistido = equipamento;
        equipamento_persistido.setId(1L);

        EquipamentoRequestDTO dto = input.mockDTO(1);

        when(equipamentoRepository.findById(1L)).thenReturn(Optional.of(equipamento));
        when(equipamentoRepository.save(equipamento)).thenReturn(equipamento_persistido);

        var resultado = equipamentoService.atualizar(dto);

        assertNotNull(resultado);
        assertNotNull(resultado.getId());
        assertNotNull(resultado.getLinks());

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/v1/equipamentos/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("listar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("GET")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("cadastrar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("POST")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("atualizar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(resultado.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("excluir")
                        && link.getHref().endsWith("/api/v1/equipamentos/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Nome1", resultado.getNome());
    }

    @Test
    void testAtualizarWithNullEquipamento() {

        Exception exception = assertThrows(RequiredObjectIsNullException.class,
                () -> {
                    equipamentoService.atualizar(null);
                });

        String expectedMessage = "It is not allowed to persist a null object!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

    }



    @Test
    void excluir() {

        Equipamento equipamento = input.mockEntity(1);
        equipamento.setId(1L);
        when(equipamentoRepository.findById(1L)).thenReturn(Optional.of(equipamento));

        equipamentoService.excluir(1L);
        verify(equipamentoRepository, times(1)).findById(anyLong());
        verify(equipamentoRepository, times(1)).delete(any(Equipamento.class));
        verifyNoMoreInteractions(equipamentoRepository);

    }

    @Test
    @Disabled("REASON: Still Under Development")
    void listar() {

        List<Equipamento> lista = input.mockEntityList();
        when(equipamentoRepository.findAll()).thenReturn(lista);
        List<EquipamentoRequestDTO> equipamentos = new ArrayList<>();

        assertNotNull(equipamentos);
        assertEquals(14, equipamentos.size());

        var equipamento1 = equipamentos.get(1);

        assertNotNull(equipamento1);
        assertNotNull(equipamento1.getId());
        assertNotNull(equipamento1.getLinks());

        assertNotNull(equipamento1.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/v1/equipamentos/1")
                        && link.getType().equals("GET")
                ));

        assertNotNull(equipamento1.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("listar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("GET")
                ));

        assertNotNull(equipamento1.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("cadastrar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("POST")
                ));

        assertNotNull(equipamento1.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("atualizar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(equipamento1.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("excluir")
                        && link.getHref().endsWith("/api/v1/equipamentos/1")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Nome1", equipamento1.getNome());

        var equipamento4 = equipamentos.get(4);

        assertNotNull(equipamento4);
        assertNotNull(equipamento4.getId());
        assertNotNull(equipamento4.getLinks());

        assertNotNull(equipamento4.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/v1/equipamentos/4")
                        && link.getType().equals("GET")
                ));

        assertNotNull(equipamento4.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("listar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("GET")
                ));

        assertNotNull(equipamento4.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("cadastrar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("POST")
                ));

        assertNotNull(equipamento4.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("atualizar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(equipamento4.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("excluir")
                        && link.getHref().endsWith("/api/v1/equipamentos/4")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Nome4", equipamento4.getNome());


        var equipamento7 = equipamentos.get(7);

        assertNotNull(equipamento7);
        assertNotNull(equipamento7.getId());
        assertNotNull(equipamento7.getLinks());

        assertNotNull(equipamento4.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("self")
                        && link.getHref().endsWith("/api/v1/equipamentos/7")
                        && link.getType().equals("GET")
                ));

        assertNotNull(equipamento7.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("listar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("GET")
                ));

        assertNotNull(equipamento7.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("cadastrar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("POST")
                ));

        assertNotNull(equipamento7.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("atualizar")
                        && link.getHref().endsWith("/api/v1/equipamentos")
                        && link.getType().equals("PUT")
                ));

        assertNotNull(equipamento7.getLinks().stream()
                .anyMatch(link -> link.getRel().value().equals("excluir")
                        && link.getHref().endsWith("/api/v1/equipamentos/7")
                        && link.getType().equals("DELETE")
                ));

        assertEquals("Nome7", equipamento7.getNome());


    }



}