package api.testeconexao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TesteConexaoDB implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(TesteConexaoDB.class);

    private final JdbcTemplate jdbcTemplate;

    public TesteConexaoDB(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            logger.info("✅ Conectado com sucesso ao banco de dados!");
        } catch (Exception e) {
            logger.error("❌ Falha ao conectar ao banco de dados: {}", e.getMessage());
            // System.exit(1);
        }
    }
}