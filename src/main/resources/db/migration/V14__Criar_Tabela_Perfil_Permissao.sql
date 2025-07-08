

-- Copiando estrutura para tabela api.tb_perfil_permissao
CREATE TABLE IF NOT EXISTS `tb_perfil_permissao` (
  `perfil_id` INT NOT NULL,
  `permissao_id` INT NOT NULL,
  PRIMARY KEY (`perfil_id`, `permissao_id`),
  CONSTRAINT `fk_perfil_pp` FOREIGN KEY (`perfil_id`) REFERENCES `tb_perfil`(`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_permissao_pp` FOREIGN KEY (`permissao_id`) REFERENCES `tb_permissao`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;