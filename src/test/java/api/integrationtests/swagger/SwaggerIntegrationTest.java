package api.integrationtests.swagger;

import api.config.TestConfigs;
import api.integrationtests.testcontainers.AbstractIntegrationTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static junit.framework.TestCase.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = { "server.port=8888" })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SwaggerIntegrationTest extends AbstractIntegrationTest {

    @Test
    void shouldDisplaySwaggerUIPage() {
        var content = given()
                .basePath("/swagger-ui/index.html")
                    .port(TestConfigs.SERVER_PORT)
                .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                        .asString();
        assertTrue(content.contains("Swagger UI"));
    }

}
