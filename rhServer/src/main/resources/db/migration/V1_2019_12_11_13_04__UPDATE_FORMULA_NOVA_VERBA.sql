--Atualização da coluna formula_nova com os novos valores de formulas. A coluna formula será depreciada posteriormente.
--Flávio Silva

UPDATE verba SET formula_nova = 'o{referenciaSalarialCargo.valor} /n' WHERE id = 1;
UPDATE verba SET formula_nova = '@r{VENC}  *  0.2' WHERE id = 2;
UPDATE verba SET formula_nova = '@r{VENC} ' WHERE id = 3;
UPDATE verba SET formula_nova = '98.44' WHERE id = 4;
UPDATE verba SET formula_nova = '@Referência Salarial Cargo * 0.5' WHERE id = 5;
UPDATE verba SET formula_nova = '@r{VENC}  ' WHERE id = 6;
UPDATE verba SET formula_nova = '@Referência Salarial Cargo / 3' WHERE id = 7;
UPDATE verba SET formula_nova = '@r{VENC} * 0.1' WHERE id = 8;
UPDATE verba SET formula_nova = '@SE 
@Total de Proventos  > @Teto da Prefeitura  
@ENTÃO  
@Total de Proventos  - @Teto da Prefeitura  
@SENÃO  
0
@FIM DO SE  
' WHERE id = 22;
UPDATE verba SET formula_nova = '@SE  
@Idade do Funcionário  >= 65 @E @Funcionário Aposentado 
@E 
( @Total de Proventos Incidentes  - @Total de Descontos Incidentes  - @r{Corte Teto}  ) > @Teto do INSS  
@ENTÃO  
( @Total de Proventos Incidentes  - @Total de Descontos Incidentes  - @r{Corte Teto} ) - @Teto do INSS  
@SENÃO  
( @Total de Proventos Incidentes  - @Total de Descontos Incidentes  - @r{Corte Teto} )
@FIM DO SE  
' WHERE id = 23;
UPDATE verba SET formula_nova = '@SE  
@Data de Nascimento do Funcionário  > @DATA("1954-12-31") @E  @Data de Admissão do Funcionário  <= @DATA("2002-04-30") 
@ENTÃO  
@r{Base Previdenciária}   * 0.11
@SENÃO  
0
@FIM DO SE      ' WHERE id = 24;
UPDATE verba SET formula_nova = '@SE  
@Data de Nascimento do Funcionário  <= @DATA("1954-12-31") @OU  @Data de Admissão do Funcionário  > @DATA("2002-04-30")  
@ENTÃO  
@r{Base Previdenciária}  * 0.11
@SENÃO  
0
@FIM DO SE    ' WHERE id = 25;
UPDATE verba SET formula_nova = '@SE  
@Idade do Funcionário  > 65
@ENTÃO  
( @Total de Proventos Incidentes  - @Total de Descontos Incidentes  - 1903.98 ) - @Número de Dependentes IRRF  * @Valor Dependente IRRF  
@SENÃO  
@Total de Proventos Incidentes  - @Total de Descontos Incidentes  - @Número de Dependentes IRRF  * @Valor Dependente IRRF  
@FIM DO SE  ' WHERE id = 26;
UPDATE verba SET formula_nova = 'a{IRRF(Base IR)}' WHERE id = 27;
UPDATE verba SET formula_nova = '@SE 
@Mês de Aniversário do Funcionário  
@ENTÃO  
@r{VENC}  
@SENÃO  
0
@FIM DO SE ' WHERE id = 28;
UPDATE verba SET formula_nova = '@SE  
@Mês de Aniversário do Funcionário  
@ENTÃO  
@Total de Proventos Incidentes  - @Total de Descontos Incidentes  
@SENÃO  
0
@FIM DO SE  ' WHERE id = 29;
UPDATE verba SET formula_nova = '@SE  
@Data de Nascimento do Funcionário  > @DATA("1954-12-31") @E  @Data de Admissão do Funcionário  <= @DATA("2002-04-30")
@E  @Mês de Aniversário do Funcionário  
@ENTÃO  
@r{13º Aniversário}  * 0.11
@SENÃO  
0
@FIM DO SE      ' WHERE id = 30;
UPDATE verba SET formula_nova = '@SE  
( @Data de Admissão do Funcionário  <= @DATA("1954-12-31")  @OU @Data de Admissão do Funcionário  > @DATA("2002-04-30")  ) 
@E @Mês de Aniversário do Funcionário  
@ENTÃO  
@r{13º Aniversário}  * 0.11
@SENÃO  
0
@FIM DO SE   ' WHERE id = 31;
UPDATE verba SET formula_nova = '@SE 
@Mês de Aniversário do Funcionário  
@ENTÃO  
a{IRRF(Base 13º Aniversário)}
@SENÃO  
0
@FIM DO SE  ' WHERE id = 32;
UPDATE verba SET formula_nova = '@Total de Proventos Incidentes  - @Total de Descontos Incidentes  ' WHERE id = 33;
UPDATE verba SET formula_nova = '@SE  
@r{Base do Salário Família}  <= 907.77
@ENTÃO  
@Número de Dependentes Salário Família  * 46.54
@SENÃO SE  
@r{Base do Salário Família}  > 907.77 @E  @r{Base do Salário Família}  <= 1364.43
@ENTÃO  
@Número de Dependentes Salário Família  * 32.80
@SENÃO  
0
@FIM DO SE  ' WHERE id = 34;
UPDATE verba SET formula_nova = '@SE  
@CPF do Funcionário  @IGUAL  "21073481115"
@ENTÃO  
( @r{VENC}  + @r{adTempServ}  ) * 0.5 
@SENÃO SE  
@Referência Salarial Função  > 0
@ENTÃO  
@Referência Salarial Função  
@SENÃO  
@r{VENC}  
@FIM DO SE  
' WHERE id = 35;
UPDATE verba SET formula_nova = '0' WHERE id = 36;
UPDATE verba SET formula_nova = '0.0' WHERE id = 37;