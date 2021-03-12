-- Alteração tabela turno
-- Wallace Nascimento

ALTER TABLE turno DROP COLUMN intervalo;
ALTER TABLE turno DROP COLUMN jornada;

ALTER TABLE turno ADD  intervalo datetime2;
ALTER TABLE turno ADD  jornada datetime2;
