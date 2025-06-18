
-- Copiando estrutura para tabela api.tb_usuarios_permissoes
CREATE TABLE IF NOT EXISTS `tb_usuarios_permissoes` (
  `id` int NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_permissao` int NOT NULL,
  CONSTRAINT `fk_id_usuario` foreign key(`id_usuario`) references `tb_usuarios`(`id`),
  CONSTRAINT `fk_id_permissao` foreign key(`id_permissao`) references `tb_permissoes`(`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


