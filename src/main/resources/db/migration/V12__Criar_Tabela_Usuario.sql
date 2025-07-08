

-- Copiando estrutura para tabela api.tb_usuario
CREATE TABLE IF NOT EXISTS `tb_usuario` (
      id int AUTO_INCREMENT PRIMARY KEY,
      user_name VARCHAR(50) NOT NULL UNIQUE,
      nome VARCHAR(100) NOT NULL,
      password VARCHAR(255) NOT NULL,
      enabled BOOLEAN DEFAULT TRUE,
      account_non_expired BOOLEAN DEFAULT TRUE,
      account_non_locked BOOLEAN DEFAULT TRUE,
      credentials_non_expired BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;