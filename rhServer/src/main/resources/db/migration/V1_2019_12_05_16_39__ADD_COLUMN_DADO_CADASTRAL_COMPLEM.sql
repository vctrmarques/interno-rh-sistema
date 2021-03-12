--Flávio Silva
--Adição de colunas novas na tabela de dado cadastral complementar.


if col_length('dado_cadastral_complementar', 'dt_ini_isencao_ir') is null
    begin
        alter table dado_cadastral_complementar add dt_ini_isencao_ir datetime2(7);
    end
    
if col_length('dado_cadastral_complementar', 'dt_fim_isencao_ir') is null
    begin
        alter table dado_cadastral_complementar add dt_fim_isencao_ir datetime2(7);
    end
    
if col_length('dado_cadastral_complementar', 'dt_ini_isencao_previdencia') is null
    begin
        alter table dado_cadastral_complementar add dt_ini_isencao_previdencia datetime2(7);
    end
    
if col_length('dado_cadastral_complementar', 'dt_fim_isencao_previdencia') is null
    begin
        alter table dado_cadastral_complementar add dt_fim_isencao_previdencia datetime2(7);
    end
    
if col_length('dado_cadastral_complementar', 'previdencia_especial') is null
    begin
        alter table dado_cadastral_complementar add previdencia_especial BIT;
    end
    
if col_length('dado_cadastral_complementar', 'contribuicao_inss') is not null
    begin
        alter table dado_cadastral_complementar drop column contribuicao_inss;
    end

if col_length('dado_cadastral_complementar', 'consignado_bloqueado') is not null
    begin
        alter table dado_cadastral_complementar drop column consignado_bloqueado;
    end
    
if col_length('dado_cadastral_complementar', 'contribuicao_ir') is not null
    begin
        alter table dado_cadastral_complementar drop column contribuicao_ir;
    end