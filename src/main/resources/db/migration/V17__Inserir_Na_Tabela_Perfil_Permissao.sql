

-- Relacionando perfis e permissões
INSERT INTO `tb_perfil_permissao` (`perfil_id`, `permissao_id`) VALUES
  (1, 1), -- ADMIN = ROLE_ADMIN
  (1, 2), -- ADMIN = ROLE_USER
  (2, 2), -- USER = ROLE_USER
  (3, 3), -- MANAGER = ROLE_MANAGER
  (3, 2); -- MANAGER = ROLE_USER
