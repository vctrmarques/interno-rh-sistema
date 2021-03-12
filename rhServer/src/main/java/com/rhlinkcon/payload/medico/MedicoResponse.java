package com.rhlinkcon.payload.medico;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.EspecialidadeMedica;
import com.rhlinkcon.model.Medico;
import com.rhlinkcon.model.Telefone;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.municipio.MunicipioResponse;
import com.rhlinkcon.payload.telefone.TelefoneReponse;
import com.rhlinkcon.repository.MunicipioRepository;

import lombok.Getter;
import lombok.Setter;

/**
 * @author João Marques
 *
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicoResponse extends DadoCadastralResponse {

	
	@Autowired
	private MunicipioRepository municipioRepository;
	
	private Long id;

	private Integer matricula;

	private String nome;

	private boolean status;

	private EmpresaFilialResponse empresa;

	private EmpresaFilialResponse filial;

	private DadoBasicoDto naturalidade;

	private DadoBasicoDto nacionalidade;

	private String estadoCivil;

	private String sexo;

	private Instant dataNascimento;

	private String nomeMae;

	private String nomePai;

	private String identidade;

	private String orgaoExpeditor;

	private Long rgUfId;

	private Instant dataExpedicaoRg;

	private String cpf;

	private Integer crm;

	private Instant dataExpedicaoCrm;

	private Long crmUfId;

	private List<EspecialidadeMedica> especialidadeMedica;
	
	private List<Long> especialidadeMedicaId;

	private String logradouro;

	private String numero;

	private String complemento;

	private Long enderecoUfId;

	private String bairro;

	private String cep;

	private MunicipioResponse municipio;

	private List<String> emails;

	private List<TelefoneReponse> telefones;

	private boolean coordenadorMedico;
	
	private boolean clinicoGeral;
	
	private boolean especialista;
	
	private DadoBasicoDto usuario;

	public MedicoResponse() {
	}
	
	public MedicoResponse(Medico medico) {
		setMedico(medico);
	}

	public MedicoResponse(Medico medico, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setMedico(medico);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}
	
	public void setMedico(Medico medico) {

		this.setId(medico.getId());
		this.setNome(medico.getNome());
		this.setMatricula(medico.getMatricula());
		this.setEmpresa(new EmpresaFilialResponse(medico.getEmpresa()));
		this.setFilial(new EmpresaFilialResponse(medico.getFilial()));
		this.setNacionalidade(new DadoBasicoDto(medico.getNacionalidade()));
		this.setNaturalidade(new DadoBasicoDto(medico.getNaturalidade()));

		this.setDataNascimento(medico.getDataNascimento());
		this.setSexo(medico.getSexo().toString());
		this.setEstadoCivil(medico.getEstadoCivil().toString());
		this.setMunicipio(new MunicipioResponse(medico.getMunicipio()));
		this.setNomeMae(medico.getNomeMae());
		this.setNomePai(medico.getNomePai());
		this.setStatus(medico.isStatus());

	
		this.setRgUfId(medico.getRgUf().getId());
		this.setCrmUfId(medico.getCrmUf().getId());
		this.setEnderecoUfId(medico.getEnderecoUf().getId());

		this.setLogradouro(medico.getLogradouro());
		this.setNumero(medico.getNumero());
		this.setComplemento(medico.getComplemento());
		this.setBairro(medico.getBairro());
		this.setCep(medico.getCep());

		this.setIdentidade(medico.getIdentidade());
		this.setOrgaoExpeditor(medico.getOrgaoExpeditor());
		this.setDataExpedicaoRg(medico.getDataExpedicaoRg());
		this.setCpf(medico.getCpf());
		this.setCrm(medico.getCrm());
		this.setCoordenadorMedico(medico.isCoordenadorMedico());
		this.setClinicoGeral(medico.isClinicoGeral());
		this.setEspecialista(medico.isEspecialista());
		if (medico.getEspecialidadesMedicas() != null) {
			this.setEspecialidadeMedica(medico.getEspecialidadesMedicas());
		}
		
		if (medico.getUsuario() != null) {
			DadoBasicoDto dto = new DadoBasicoDto();
			dto.setId(medico.getUsuario().getId());
			dto.setDescricao(medico.getUsuario().getNome());
			
			this.setUsuario(dto);
		}

		if(Objects.nonNull(medico.getEmails()))
			this.setEmails(medico.getEmails());
		
		if(Objects.nonNull(medico.getTelefones()))
			setTelefones(new ArrayList<>());
		for (Telefone tf : medico.getTelefones()) {
			TelefoneReponse t = new TelefoneReponse(tf);
			getTelefones().add(t);
		}
		
		if (Objects.nonNull(medico.getEspecialidadesMedicas())) {
			setEspecialidadeMedicaId(new ArrayList<>());
			
			for(EspecialidadeMedica es : medico.getEspecialidadesMedicas()) {
				getEspecialidadeMedicaId().add(es.getId());
			}
		}
	}
}
