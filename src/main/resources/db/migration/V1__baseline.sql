
-- Copiando estrutura para tabela db.tb_setor
CREATE TABLE IF NOT EXISTS `tb_setor` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
	`nome` VARCHAR(38) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Copiando estrutura para tabela db.tb_categoria
CREATE TABLE IF NOT EXISTS `tb_categoria` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
	`nome` VARCHAR(38) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Copiando estrutura para tabela db.tb_marca
CREATE TABLE IF NOT EXISTS `tb_marca` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
	`nome` VARCHAR(38) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Copiando estrutura para tabela db.tb_origem
CREATE TABLE IF NOT EXISTS `tb_origem` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
	`nome` VARCHAR(38) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Copiando estrutura para tabela db.tb_material
CREATE TABLE IF NOT EXISTS `tb_material` (
`id` INT AUTO_INCREMENT PRIMARY KEY,
	`nome` VARCHAR(40) NOT NULL,
	`situacao` ENUM('DISPONÍVEL', 'EMPRESTADO', 'DANIFICADO', 'DESATIVADO', 'MANUTENÇÃO') DEFAULT 'DISPONÍVEL',
	`patrimonio` VARCHAR(12) UNIQUE,
	`numserie` VARCHAR(40),
	`modelo` VARCHAR(40),
	`categoria_id` INT,
	`setor_id` INT,
	`marca_id` INT,
	`origem_id` INT,
	`localizacao_fisica` VARCHAR(50),
	`data_aquisicao` DATE,
	`descricao` TEXT,
	`valor_compra` DECIMAL(10,2),
	`identificacao_recibo` VARCHAR(30),
	`qr_valor` VARCHAR(255),
    `imagem_url` VARCHAR(255),
	`tipo_depreciacao` ENUM('LINEAR', 'ACELERADA') DEFAULT 'LINEAR',
	`percentual_depreciacao` DECIMAL(5,2),
	`vida_util_anos` INT,
	`valor_atual` DECIMAL(10,2),

	constraint fk_categoria_id FOREIGN KEY (categoria_id) REFERENCES tb_categoria(id),
	constraint fk_origem_id FOREIGN KEY (origem_id) REFERENCES tb_origem(id),
	constraint fk_setor_id FOREIGN KEY (setor_id) REFERENCES tb_setor(id),
	constraint fk_marca_id FOREIGN KEY (marca_id) REFERENCES tb_marca(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Copiando estrutura para tabela db.tb_permissao
CREATE TABLE IF NOT EXISTS `tb_permissao` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
	`nome` VARCHAR(38) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Copiando estrutura para tabela db.tb_perfil
CREATE TABLE IF NOT EXISTS `tb_perfil` (
  `id` INT AUTO_INCREMENT PRIMARY KEY,
	`nome` VARCHAR(38) NOT NULL UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- Copiando estrutura para tabela db.tb_usuario
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


-- Copiando estrutura para tabela db.tb_usuario_perfil
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


-- Copiando estrutura para tabela db.tb_perfil_permissao
CREATE TABLE IF NOT EXISTS `tb_perfil_permissao` (
  `perfil_id` INT NOT NULL,
  `permissao_id` INT NOT NULL,
  PRIMARY KEY (`perfil_id`, `permissao_id`),
  CONSTRAINT `fk_perfil_pp` FOREIGN KEY (`perfil_id`) REFERENCES `tb_perfil`(`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_permissao_pp` FOREIGN KEY (`permissao_id`) REFERENCES `tb_permissao`(`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
