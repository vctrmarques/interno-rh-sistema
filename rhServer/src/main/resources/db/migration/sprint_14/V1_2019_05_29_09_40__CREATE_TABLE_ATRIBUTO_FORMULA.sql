-- Eduardo Costa

-- Criação da tabela de atributo responsável por armazenar o mapeamento entre os caminhos dos atributos utilizados 
-- na folha de pagamento e seus respectivos nomes.

IF NOT EXISTS (SELECT * FROM SYS.TABLES WHERE name = 'atributo_formula')
CREATE TABLE atributo_formula(
	id bigint IDENTITY(1,1) NOT NULL PRIMARY KEY,
	label varchar(255) NOT NULL,
	[path] varchar(255) NOT NULL,
) ON [PRIMARY]
GO