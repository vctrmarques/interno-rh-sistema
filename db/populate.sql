SET IDENTITY_INSERT dbo.lotacao ON;
GO
INSERT INTO [rhlinkcon].[dbo].[lotacao] ([id], [descricao], [descricao_completa], [nivel], [efetivo], [id_centro_custo], [tipo_conta], [numero_conta], [tipo],
										 [vigencia_inicial], [vigencia_final], [created_at], [updated_at], [created_by], [updated_by], [excluida])
VALUES (1, 'Teste de Lotação', 'Teste de Lotação', 1, 1, 1, 'DEBITO', 1111, 'SINTETICO', '2019-07-29 16:46:09.8490000', '2035-07-31 16:46:13.9760000',
		'2019-07-29 16:46:18.9000000', '2019-07-29 16:46:21.6190000', 1, 1, 0)
INSERT INTO [rhlinkcon].[dbo].[empresa_filial_lotacao] ([empresa_filial_id], [lotacao_id])
VALUES (1, 1),
	   (2, 1),
	   (3, 1)