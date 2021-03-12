IF NOT EXISTS(SELECT * FROM SYS.TABLES WHERE name = 'certidao_compensacao_historico_classificacao')
BEGIN
	CREATE TABLE certidao_compensacao_historico_classificacao (
		certidao_compensacao_historico_id bigint NOT NULL,
		classificacao varchar(255) NOT NULL,
		CONSTRAINT pk_certidao_compensacao_historico_classificacao PRIMARY KEY (certidao_compensacao_historico_id,classificacao),
		CONSTRAINT fk_certidao_compensacao_historico_classificacao_certidao_compensacao_id FOREIGN KEY (certidao_compensacao_historico_id) REFERENCES certidao_compensacao_historico(id) 
	);
END


-- Flávio Silva
-- Alteração da tabela certidao_compensacao_historico para retirar o atributo classificacao.
if col_length('certidao_compensacao_historico', 'classificacao') is not null
    begin
        alter table certidao_compensacao_historico drop column classificacao;
    end