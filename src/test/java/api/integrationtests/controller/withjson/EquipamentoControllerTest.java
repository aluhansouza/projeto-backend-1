package api.integrationtests.controller.withjson;

import api.config.TestConfigs;
import api.integrationtests.dto.EquipamentoDTO;
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

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = { "server.port=8888" })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EquipamentoControllerTest extends AbstractIntegrationTest {

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
    void create() throws JsonProcessingException {
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
                .extract()
                .body()
                .asString();

        EquipamentoDTO criadoEquipamentoDTO = objectMapper.readValue(content, EquipamentoDTO.class);
        equipamentoDTO = criadoEquipamentoDTO;

        assertNotNull(criadoEquipamentoDTO.getId());
        assertNotNull(criadoEquipamentoDTO.getNome());

        assertTrue(criadoEquipamentoDTO.getId() > 0);

        assertEquals("Teste 2", criadoEquipamentoDTO.getNome());

    }

    @Test
    @Order(2)
    void createWithWrongOrigin() throws JsonProcessingException {

        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_NAO_AUTORIZADO)
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
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);

    }

    @Test
    @Order(3)
    void findById() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_LOCAL)
                .setBasePath("/api/v1/equipamentos")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", equipamentoDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();

        EquipamentoDTO criadoEquipamentoDTO = objectMapper.readValue(content, EquipamentoDTO.class);
        equipamentoDTO = criadoEquipamentoDTO;

        assertNotNull(criadoEquipamentoDTO.getId());
        assertNotNull(criadoEquipamentoDTO.getNome());

        assertTrue(criadoEquipamentoDTO.getId() > 0);

        assertEquals("Teste 2", criadoEquipamentoDTO.getNome());
    }
    @Test
    @Order(4)
    void findByIdWithWrongOrigin() throws JsonProcessingException {
        specification = new RequestSpecBuilder()
                .addHeader(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_NAO_AUTORIZADO)
                .setBasePath("/api/v1/equipamentos")
                .setPort(TestConfigs.SERVER_PORT)
                .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                .build();

        var content = given(specification)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .pathParam("id", equipamentoDTO.getId())
                .when()
                .get("{id}")
                .then()
                .statusCode(403)
                .extract()
                .body()
                .asString();

        assertEquals("Invalid CORS request", content);
    }

    private void mockEquipamento() {
        equipamentoDTO.setNome("Teste 2");
    }


}
