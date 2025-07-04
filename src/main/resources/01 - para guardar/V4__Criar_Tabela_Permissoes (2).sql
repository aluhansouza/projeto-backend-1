
-- Copiando estrutura para tabela api.tb_permissoes
CREATE TABLE IF NOT EXISTS `tb_permissoes` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `nome` varchar(80) UNIQUE NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


