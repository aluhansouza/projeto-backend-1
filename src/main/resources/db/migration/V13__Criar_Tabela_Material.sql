
-- Copiando estrutura para tabela api.tb_material
CREATE TABLE IF NOT EXISTS `tb_material` (
`id` INT AUTO_INCREMENT PRIMARY KEY,
	`nome` VARCHAR(38) NOT NULL,
	`situacao` ENUM('disponível', 'emprestado', 'danificado', 'descartado', 'em manutenção') DEFAULT 'disponível',
	`patrimonio` VARCHAR(4) UNIQUE,
	`categoria_id` INT,
	`setor_id` INT,
	`localizacao_fisica` VARCHAR(50),
	`data_aquisicao` DATE,
	`descricao` TEXT,
	`valor_compra` DECIMAL(10,2),
	`identificacao_recibo` VARCHAR(30),
	`qr_valor` VARCHAR(255),

	`tipo_depreciacao` ENUM('Linear', 'Acelerada') DEFAULT 'Linear',
	`percentual_depreciacao` DECIMAL(5,2),
	`vida_util_anos` INT,
	`valor_atual` DECIMAL(10,2),
    
	constraint fk_categoria_id FOREIGN KEY (categoria_id) REFERENCES tb_categoria(id),
	constraint fk_setor_id FOREIGN KEY (setor_id) REFERENCES tb_setor(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


