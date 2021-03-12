-- Lucas Moura

-- Criação da tabela de Férias Programação

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'ferias_programacao')
CREATE TABLE ferias_programacao (
	
	id 									bigint 		PRIMARY KEY		NOT NULL IDENTITY(1,1),
	funcionario_id 						bigint 						NOT NULL,
	exercicio_inicio					date		 				NOT NULL,
	exercicio_fim						date 		 				NOT NULL,
	data_limite  						date 		 				NOT NULL,
	quant_faltas						varchar(3)							,
	tipo_ferias							varchar(30)					NOT NULL,
	quant_dias							varchar(3)					NOT NULL,
	abono_peculiario					bit									,
	decimo_terceiro						bit									,
	situacao							varchar(30)					NOT NULL,
	mes_competencia_particao_um			varchar(30)							,
	dias_a_gozar_um						varchar(2)							,
	tipo_processamento_um				varchar(30)							,
	data_inicial_um						date								,
	data_final_um						date								,
	situacao_um							varchar(30)							,
	mes_competencia_particao_dois		varchar(10)							,
	dias_a_gozar_dois					varchar(2)							,
	tipo_processamento_dois				varchar(30)							,
	data_inicial_dois					date								,
	data_final_dois						date								,
	situacao_dois						varchar(30)							,
	mes_competencia_particao_tres		varchar(30)							,
	dias_a_gozar_tres					varchar(2)							,
	tipo_processamento_tres				varchar(30)							,
	data_inicial_tres					date								,
	data_final_tres						date								,
	situacao_tres						varchar(30)							,	
	created_at 							DATETIME2(7) 				NOT NULL,
	updated_at 							DATETIME2(7) 				NOT NULL,
	created_by 							bigint								,
	updated_by 							bigint								,
	
	CONSTRAINT fk_ferias_programacao_funcionario_id FOREIGN KEY (funcionario_id) REFERENCES funcionario(id)
		
)