package api.repository;

import api.entity.Equipamento;
import api.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EquipamentoRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    EquipamentoRepository equipamentoRepository;
    private static Equipamento equipamento;

    @BeforeAll
    static void setUp() {
        equipamento = new Equipamento();
    }

    @Test
    @Order(1)
    void findEquipamentoByName() {
        Pageable pageable = PageRequest.of(
                0,
                12,
                Sort.by(Sort.Direction.ASC, "nome"));

        equipamento = equipamentoRepository.buscarPorNome("jo", pageable).getContent().get(0);

        assertNotNull(equipamento);
        assertNotNull(equipamento.getId());
        assertEquals("Joao", equipamento.getNome());

    }


    
}
