drop table experiencia_profissional_funcao
go

truncate table experiencia_profissional
go


alter table experiencia_profissional
	add funcao_id bigint not null
go

alter table experiencia_profissional
	add constraint experiencia_profissional_funcao_id_fk
		foreign key (funcao_id) references funcao
go