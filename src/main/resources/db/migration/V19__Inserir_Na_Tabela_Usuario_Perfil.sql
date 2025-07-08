

-- Relacionando usuários e perfis
INSERT INTO `tb_usuario_perfil` (`usuario_id`, `perfil_id`, `ativo`) VALUES
  (1, 1, 1), -- admin é ADMIN
  (2, 2, 1), -- user é USER
  (3, 3, 1), -- manager é MANAGER
  (1, 2, 1); -- admin também é USER
