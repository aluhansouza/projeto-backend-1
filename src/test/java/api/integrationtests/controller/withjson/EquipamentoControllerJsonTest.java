package api.integrationtests.controller.withjson;

import api.config.TestConfigs;
import api.integrationtests.dto.EquipamentoDTO;
import api.integrationtests.dto.wrappers.json.WrapperEquipamentoDTO;
import api.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = { "server.port=8888" })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EquipamentoControllerJsonTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static EquipamentoDTO equipamentoDTO;

    @BeforeAll
    static void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        equipamentoDTO = new EquipamentoDTO();
    }

    @Test
    @Order(1)
    void createTest() throws JsonProcessingException {
        mockEquipamento();

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_AUTORIZADO)
                .setBasePath("/api/v1/equipamentos")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(equipamentoDTO)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        EquipamentoDTO criadoEquipamento = objectMapper.readValue(content, EquipamentoDTO.class);
        equipamentoDTO = criadoEquipamento;

        assertNotNull(criadoEquipamento.getId());
        assertTrue(criadoEquipamento.getId() > 0);

        assertEquals("Teste2025", criadoEquipamento.getNome());

    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        equipamentoDTO.setNome("Teste 2");

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(equipamentoDTO)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        EquipamentoDTO criadoEquipamento = objectMapper.readValue(content, EquipamentoDTO.class);
        equipamentoDTO = criadoEquipamento;

        assertNotNull(criadoEquipamento.getId());
        assertTrue(criadoEquipamento.getId() > 0);

        assertEquals("Teste 2", criadoEquipamento.getNome());

    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", equipamentoDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        EquipamentoDTO criadoEquipamento = objectMapper.readValue(content, EquipamentoDTO.class);
        equipamentoDTO = criadoEquipamento;

        assertNotNull(criadoEquipamento.getId());
        assertTrue(criadoEquipamento.getId() > 0);

        assertEquals("Teste 2", criadoEquipamento.getNome());
    }

    /*
    @Test
    @Order(4)
    void disableTest() throws JsonProcessingException {

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", equipamentoDTO.getId())
                .when()
                .patch("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        PersonDTO createdPerson = objectMapper.readValue(content, PersonDTO.class);
        person = createdPerson;

        assertNotNull(createdPerson.getId());
        assertTrue(createdPerson.getId() > 0);

        assertEquals("Linus", createdPerson.getFirstName());
        assertEquals("Benedict Torvalds", createdPerson.getLastName());
        assertEquals("Helsinki - Finland", createdPerson.getAddress());
        assertEquals("Male", createdPerson.getGender());
        assertFalse(createdPerson.getEnabled());
    }
    */




    @Test
    @Order(4)
    void findAllTest() throws JsonProcessingException {

        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .queryParams("page", 0, "size", 12, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperEquipamentoDTO wrapper = objectMapper.readValue(content, WrapperEquipamentoDTO.class);
        List<EquipamentoDTO> equipamentos = wrapper.getEmbedded().getEquipamentos();

        EquipamentoDTO equipamento1 = equipamentos.get(0);
        assertNotNull(equipamento1.getId());
        assertTrue(equipamento1.getId() > 0);
        assertEquals("Joao", equipamento1.getNome());


        EquipamentoDTO equipamento2 = equipamentos.get(1);
        assertNotNull(equipamento2.getId());
        assertTrue(equipamento2.getId() > 0);
        assertEquals("Jose", equipamento2.getNome());
    }

    @Test
    @Order(5)
    void findByNameTest() throws JsonProcessingException {


        var content = given(specification)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("nome", "Jo")
                .queryParams("page", 0, "size", 12, "direction", "asc")
                .when()
                .get("buscarPorNome/{nome}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .extract()
                .body()
                .asString();

        WrapperEquipamentoDTO wrapper = objectMapper.readValue(content, WrapperEquipamentoDTO.class);
        List<EquipamentoDTO> equipamentos = wrapper.getEmbedded().getEquipamentos();

        EquipamentoDTO equipamento1 = equipamentos.get(0);
        assertNotNull(equipamento1.getId());
        assertTrue(equipamento1.getId() > 0);
        assertEquals("Joao", equipamento1.getNome());


        EquipamentoDTO equipamento2 = equipamentos.get(1);
        assertNotNull(equipamento2.getId());
        assertTrue(equipamento2.getId() > 0);
        assertEquals("Jose", equipamento2.getNome());
    }

    @Test
    @Order(6)
    void deleteTest() throws JsonProcessingException {

        given(specification)
                .pathParam("id", equipamentoDTO.getId())
                .when()
                .delete("{id}")
                .then()
                .statusCode(204);
    }

    private void mockEquipamento() {
        equipamentoDTO.setNome("Teste2025");
    }




}
