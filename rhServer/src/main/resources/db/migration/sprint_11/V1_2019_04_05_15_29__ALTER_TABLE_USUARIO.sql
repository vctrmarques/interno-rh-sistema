--Lucas Moura
--Add coluna empresa_filial_id

IF NOT EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'usuario'
                 AND COLUMN_NAME = 'empresa_filial_id') 
BEGIN
	ALTER TABLE usuario
	add  empresa_filial_id bigint
	alter table usuario
	add constraint fk_usuario_empresa_filial_id foreign key (empresa_filial_id) references empresa_filial(id);
END


