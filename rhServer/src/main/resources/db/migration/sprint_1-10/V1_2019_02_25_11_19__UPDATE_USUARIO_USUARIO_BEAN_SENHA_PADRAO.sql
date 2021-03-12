-- UPDATE da senha padrão do usuário ben - Usuário padrão do sistema.
-- Flávio Barbosa

UPDATE usuario SET senha = '$2a$10$H9PkWM/Zvy.y1MohiwrWE.xKSOVrXEAAjLyfXNFkHjKoU1WBGrJmy', ativo = 1 WHERE login = 'ben' ;
