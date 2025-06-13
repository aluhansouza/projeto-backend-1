

-- Para tb_usuarios
ALTER TABLE tb_usuarios
  ADD COLUMN created_by VARCHAR(100),
  ADD COLUMN created_date TIMESTAMP,
  ADD COLUMN last_modified_by VARCHAR(100),
  ADD COLUMN last_modified_date TIMESTAMP;

-- Para tb_permissoes
ALTER TABLE tb_permissoes
  ADD COLUMN created_by VARCHAR(100),
  ADD COLUMN created_date TIMESTAMP,
  ADD COLUMN last_modified_by VARCHAR(100),
  ADD COLUMN last_modified_date TIMESTAMP;

-- Para tb_usuarios_permissoes
ALTER TABLE tb_usuarios_permissoes
  ADD COLUMN created_by VARCHAR(100),
  ADD COLUMN created_date TIMESTAMP,
  ADD COLUMN last_modified_by VARCHAR(100),
  ADD COLUMN last_modified_date TIMESTAMP;