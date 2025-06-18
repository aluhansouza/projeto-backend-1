
-- Copiando estrutura para tabela api.tb_categoria
CREATE TABLE IF NOT EXISTS `tb_categoria` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
	`nome` VARCHAR(38) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


