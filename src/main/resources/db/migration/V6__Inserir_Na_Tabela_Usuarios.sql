

-- Copiando dados para a tabela api.tb_usuarios: ~2 rows (aproximadamente)
INSERT INTO `tb_usuarios`
(`nome`,
`user_name`,
`password`,
`account_non_expired`,
`account_non_locked`,
`credentials_non_expired`,
`enabled`
)
 VALUES
	('Administrador','administrador', 'e4d598723f77e5e96c895c1f9c267cc1',b'1',b'1',b'1',b'1'),
	('Geremte', 'gerente','e4d598723f77e5e96c895c1f9c267cc1',b'1',b'1',b'1',b'1')
;
