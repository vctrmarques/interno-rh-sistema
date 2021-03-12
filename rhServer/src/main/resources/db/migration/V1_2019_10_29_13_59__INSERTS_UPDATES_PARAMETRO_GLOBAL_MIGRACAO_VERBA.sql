--Inserts de parâmetros globais básicos.
--Flávio Silva

IF NOT EXISTS(SELECT * FROM parametro_global WHERE nome = 'tetoPrefeitura')
	INSERT INTO .dbo.parametro_global (created_at, updated_at, created_by, updated_by, ativo, nome, tipo, valor)
		VALUES(SYSDATETIME(), SYSDATETIME(), 0, 0, 1, 'tetoPrefeitura', 'MOEDA', '27089.54');

IF NOT EXISTS(SELECT * FROM parametro_global WHERE nome = 'tetoInss')
	INSERT INTO .dbo.parametro_global (created_at, updated_at, created_by, updated_by, ativo, nome, tipo, valor)
		VALUES(SYSDATETIME(), SYSDATETIME(), 0, 0, 1, 'tetoInss', 'MOEDA', '5839.45');
		
IF NOT EXISTS(SELECT * FROM parametro_global WHERE nome = 'valorDependenteIrrf')
	INSERT INTO .dbo.parametro_global (created_at, updated_at, created_by, updated_by, ativo, nome, tipo, valor)
		VALUES(SYSDATETIME(), SYSDATETIME(), 0, 0, 1, 'valorDependenteIrrf', 'MOEDA', '189.59');
		
-- Com a vinda da formula para a verba, segue os updates das verbas já existentes afim de migrar.
		
update verba set formula = 'o{referenciaSalarialCargo.valor} /n', descricao_formula = 'VENC' where descricao_verba = 'VENCIMENTO'
update verba set formula = 'r{VENC}  *  0.2 /n', descricao_formula = 'insalubridade' where descricao_verba = 'ADICIONAL DE INSALUBRIDADE'
update verba set formula = 'r{VENC} /n', descricao_formula = 'aip' where descricao_verba = 'ADICIONAL DE INCENTIVO A PROFISSIONALIZAÇÃO'
update verba set formula = '0', descricao_formula = 'peculio' where descricao_verba = 'PECÚLIO'
update verba set formula = 'o{referenciaSalarialCargo.valor} * 0.5 /n', descricao_formula = 'periculosidade' where descricao_verba = 'ADICIONAL PERICULOSIDADE'
update verba set formula = 'r{VENC} /n', descricao_formula = 'adc.titul.aper' where descricao_verba = 'ADICIONAL TITUL. APERFEIÇOAMENTO'
update verba set formula = 'o{referenciaSalarialCargo.valor} /3 /n', descricao_formula = 'vant.pessoal' where descricao_verba = 'VANTAGEM PESSOAL'
