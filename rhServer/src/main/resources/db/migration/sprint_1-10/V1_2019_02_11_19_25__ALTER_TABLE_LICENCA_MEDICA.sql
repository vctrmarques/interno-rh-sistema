--Railson Silva
--Add column cid_id
IF NOT EXISTS(SELECT *
          FROM   INFORMATION_SCHEMA.COLUMNS
          WHERE  TABLE_NAME = 'licenca_medica'
                 AND COLUMN_NAME = 'cid_id') 
  ALTER TABLE licenca_medica
    ADD cid_id bigint not null
    CONSTRAINT fk_licenca_cid foreign key (cid_id) REFERENCES classificacao_internacional_doenca(id)
