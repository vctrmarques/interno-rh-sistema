-- Criação dos campos para regra de contagem de comparecimento do paciente e mudança do nome do submenu da tela.
-- João Marques

UPDATE menu SET nome ='Agenda Perícia Médica' WHERE nome = 'Perícia Médica'
ALTER TABLE paciente_pericia_medica ADD contador_nao_compareceu INT NULL;