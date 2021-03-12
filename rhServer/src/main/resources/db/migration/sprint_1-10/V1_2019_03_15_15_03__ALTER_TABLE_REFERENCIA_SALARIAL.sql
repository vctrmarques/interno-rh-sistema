-- Davi Queiroz

-- atualização do menu referente a referencia salarial


BEGIN IF (SELECT COUNT(id) FROM referencia_salarial WHERE descricao IS NOT NULL) > 0
	UPDATE referencia_salarial SET descricao = 'refatoracao tabela';

   ALTER TABLE referencia_salarial
   ALTER COLUMN descricao VARCHAR(255) NOT NULL;
END

BEGIN IF (SELECT COUNT(id) FROM referencia_salarial) <= 0
   ALTER TABLE referencia_salarial
   ALTER COLUMN descricao VARCHAR(255) NOT NULL
END