

-- Copiando estrutura para tabela api.tb_usuario_perfil
CREATE TABLE IF NOT EXISTS `tb_usuario_perfil` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `usuario_id` INT NOT NULL,
  `perfil_id` INT NOT NULL,
  `data_associacao` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `data_remocao` DATETIME DEFAULT CURRENT_TIMESTAMP,
  `ativo` TINYINT(1) DEFAULT 1,
  UNIQUE KEY `uk_usuario_perfil` (`usuario_id`, `perfil_id`),
  CONSTRAINT `fk_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `tb_usuario`(`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_perfil` FOREIGN KEY (`perfil_id`) REFERENCES `tb_perfil`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;