package com.rhlinkcon.payload.medico;

import java.time.Instant;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.telefone.TelefoneRequest;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicoRequest {

		// Início aba Dados Cadastrais
		private Long id;

		@NotNull
		private Integer matricula;
		
		@NotNull
		private String nome;
		
		@NotNull
		private boolean status;

		@NotNull
		private Long empresaId;

		@NotNull
		private Long filialId;
		
		@NotNull
		private DadoBasicoDto naturalidade;
		
		@NotNull
		private DadoBasicoDto nacionalidade;

		@NotNull
		private String estadoCivil;
		
		@NotNull
		private String sexo;

		@NotNull
		private Instant dataNascimento;

		@NotNull
		private String nomeMae;

		private String nomePai;

		// Fim aba Dados do Contrato
		
		// Início aba Documentação
		@NotNull
		private String identidade;

		@NotNull
		private String orgaoExpeditor;

		@NotNull
		private Long rgUfId;

		@NotNull
		private Instant dataExpedicaoRg;

		@NotNull
		@Size(min = 11, max = 11)
		private String cpf;

		@NotNull
		private Integer crm;

		@NotNull
		private Instant dataExpedicaoCrm;
		
		@NotNull
		private Long crmUfId;

		private List<Long> especialidadeMedicaId;
		
		private DadoBasicoDto usuario;
		
		// Fim Aba Documentação
		
		// Início aba Contato & Endereço

		@NotNull
		private String logradouro;

		@NotNull
		private String numero;

		private String complemento;

		@NotNull
		private Long enderecoUfId;
		
		@NotNull
		private String bairro;

		@NotNull
		private String cep;
		
		private Long municipioId;
		
		private List<String> emails;

		private List<TelefoneRequest> telefones;
		
		private boolean coordenadorMedico;
		
		private boolean clinicoGeral;
		
		private boolean especialista;

		// Fim aba Contato & Endereço
}
