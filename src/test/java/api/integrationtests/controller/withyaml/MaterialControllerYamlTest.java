package api.integrationtests.controller.withyaml;

import api.config.TestConfigs;
import api.integrationtests.controller.withyaml.mapper.YAMLMapper;
import api.integrationtests.dto.EquipamentoDTO;
import api.integrationtests.dto.wrappers.xmlandyaml.PagedModelEquipamento;
import api.integrationtests.testcontainers.AbstractIntegrationTest;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.util.List;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = { "server.port=8888" })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MaterialControllerYamlTest extends AbstractIntegrationTest {

    private static RequestSpecification specification;
    private static YAMLMapper objectMapper;

    private static EquipamentoDTO equipamentoDTO;

    @BeforeAll
    static void setUp() {
        objectMapper = new YAMLMapper();
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

        var criadoEquipamento = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(
                                        EncoderConfig.encoderConfig().
                                                encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                ).spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(equipamentoDTO, objectMapper)
                .when()
                .post()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(EquipamentoDTO.class, objectMapper);

        equipamentoDTO = criadoEquipamento;

        assertNotNull(criadoEquipamento.getId());
        assertTrue(criadoEquipamento.getId() > 0);

        assertEquals("Teste2025", criadoEquipamento.getNome());

    }

    @Test
    @Order(2)
    void updateTest() throws JsonProcessingException {
        equipamentoDTO.setNome("Teste2026");

        var criadoEquipamento = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(
                                        EncoderConfig.encoderConfig().
                                                encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                ).spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .body(equipamentoDTO, objectMapper)
                .when()
                .put()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(EquipamentoDTO.class, objectMapper);

        equipamentoDTO = criadoEquipamento;

        assertNotNull(criadoEquipamento.getId());
        assertTrue(criadoEquipamento.getId() > 0);

        assertEquals("Teste2026", criadoEquipamento.getNome());

    }

    @Test
    @Order(3)
    void findByIdTest() throws JsonProcessingException {

        var criadoEquipamento = given().config(
                        RestAssuredConfig.config()
                                .encoderConfig(
                                        EncoderConfig.encoderConfig().
                                                encodeContentTypeAs(MediaType.APPLICATION_YAML_VALUE, ContentType.TEXT))
                ).spec(specification)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("id", equipamentoDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(EquipamentoDTO.class, objectMapper);

        equipamentoDTO = criadoEquipamento;

        assertNotNull(criadoEquipamento.getId());
        assertTrue(criadoEquipamento.getId() > 0);

        assertEquals("Teste2026", criadoEquipamento.getNome());

    }



    @Test
    @Order(4)
    void findAllTest() throws JsonProcessingException {

        var response = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .queryParams("page", 0, "size", 12, "direction", "asc")
                .when()
                .get()
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PagedModelEquipamento.class, objectMapper);

        List<EquipamentoDTO> equipamentos = response.getContent();

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
    void findByNameTestTest() throws JsonProcessingException {

        var response = given(specification)
                .accept(MediaType.APPLICATION_YAML_VALUE)
                .pathParam("nome", "Jo")
                .queryParams("page", 0, "size", 12, "direction", "asc")
                .when()
                .get("buscarPorNome/{nome}")
                .then()
                .statusCode(200)
                .contentType(MediaType.APPLICATION_YAML_VALUE)
                .extract()
                .body()
                .as(PagedModelEquipamento.class, objectMapper);

        List<EquipamentoDTO> equipamentos = response.getContent();

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
