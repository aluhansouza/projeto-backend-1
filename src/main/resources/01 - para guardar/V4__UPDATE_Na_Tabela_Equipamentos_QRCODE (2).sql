

-- Copiando dados para a tabela api.tb_equipamentos: ~2 rows (aproximadamente)
UPDATE `tb_material`
SET `qrcode_valor` = CONCAT('http://localhost:8080/api/v1/equipamentos/', `id`);

