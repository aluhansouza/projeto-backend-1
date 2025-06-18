
-- Copiando estrutura para tabela api.tb_usuarios
CREATE TABLE IF NOT EXISTS `tb_usuarios` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `nome` varchar(80) NOT NULL,
  `user_name` varchar(80) UNIQUE NOT NULL,
  `password` varchar(80) NOT NULL,
  `account_non_expired` bit(1) DEFAULT NULL,
  `account_non_locked` bit(1) DEFAULT NULL,
  `credentials_non_expired` bit(1) DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


