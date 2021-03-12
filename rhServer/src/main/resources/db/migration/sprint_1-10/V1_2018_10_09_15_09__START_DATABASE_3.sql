
-- Criação do primeiro papel role admin

INSERT papel (nome, id_menu) VALUES ('ROLE_ADMIN', NULL);


-- Criação do primeiro usuário

INSERT usuario ( created_at, updated_at, ativo, cpf, email, login, nome, senha, created_by, updated_by, id_anexo) 
VALUES ((getdate()), (getdate()), 1, '08746114408','ben@ldap.com', 'ben', 'Ben User Teste', NULL, 1, 1, NULL);


-- Atribuição do primeiro usuário ao primeiro papel

INSERT usuario_papel (usuario_id, papel_id) VALUES 
((select u.id from usuario u where u.login = 'ben'), 
(select p.id from papel p where p.nome = 'ROLE_ADMIN'));


-- Criação dos primeiros menus.

INSERT INTO menu (created_at,updated_at,created_by,updated_by,nome,ativo,categoria) 
VALUES 
((getdate()),(getdate()),1,1,'Orgãos',1,'GESTAO'),
((getdate()),(getdate()),1,1,'Usuários',1,'GESTAO');