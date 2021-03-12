package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.filtro.MedicoFiltro;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.EspecialidadeMedica;
import com.rhlinkcon.model.EstadoCivilEnum;
import com.rhlinkcon.model.GeneroEnum;
import com.rhlinkcon.model.Medico;
import com.rhlinkcon.model.Municipio;
import com.rhlinkcon.model.Nacionalidade;
import com.rhlinkcon.model.Telefone;
import com.rhlinkcon.model.UnidadeFederativa;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.especialidadeMedica.EspecialidadeMedicaDto;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.medico.MedicoRequest;
import com.rhlinkcon.payload.medico.MedicoResponse;
import com.rhlinkcon.payload.telefone.TelefoneRequest;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.MunicipioRepository;
import com.rhlinkcon.repository.NacionalidadeRepository;
import com.rhlinkcon.repository.TelefoneRepository;
import com.rhlinkcon.repository.UnidadeFederativaRepository;
import com.rhlinkcon.repository.medico.MedicoRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Utils;

@Service
public class MedicoService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private MedicoRepository medicoRepository;
	
	@Autowired
	private EmpresaFilialRepository empresaFilialRepository;
	
	@Autowired
	private NacionalidadeRepository nacionalidadeRepository;
	
	@Autowired
	private MunicipioRepository municipioRepository;
	
	@Autowired
	private UnidadeFederativaRepository unidadeFederativaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;


	public ResponseEntity<?> create(MedicoRequest medicoRequest) {
		
		
		if (medicoRepository.existsByCpf(medicoRequest.getCpf())) {
			return Utils.badRequest(false, "Já existe um médico com este cpf.");
		}
		
		if (medicoRepository.existsByMatricula(medicoRequest.getMatricula())) {
			return Utils.badRequest(false, "Já existe um médico com está matrícula.");
		}
		
		if (medicoRepository.existsByCrm(medicoRequest.getCrm())) {
			return Utils.badRequest(false, "Já existe um médico com este crm.");
		}
		
		Medico medico = new Medico(medicoRequest);
		
		setEntidades(medico, medicoRequest);
		
		medico.setStatus(true);
		
		if(Objects.nonNull(medico.getTelefones())) {
			for(Telefone t : medico.getTelefones()) {
				telefoneRepository.save(t);
			}
		}
			
		medicoRepository.save(medico);
		
		return Utils.created(true, "Médico criado com sucesso!");	
		
	}

	
	public PagedResponse<MedicoResponse> getAllMedicos(int page, int size,
			MedicoFiltro medicoFiltro, String order) {
		Utils.validatePageNumberAndSize(page, size);

		// Retrieve Users
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<Medico> medicos = medicoRepository.filtro(medicoFiltro, pageable);

		if (medicos.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), medicos.getNumber(), medicos.getSize(),
					medicos.getTotalElements(), medicos.getTotalPages(), medicos.isLast());
		}

		List<MedicoResponse> medicosResponse = new ArrayList<MedicoResponse>();

		for (Medico medico : medicos) {
			MedicoResponse medicoResponse = new MedicoResponse();
			medicoResponse.setId(medico.getId());
			medicoResponse.setMatricula(medico.getMatricula());
			medicoResponse.setNome(medico.getNome());
			medicoResponse.setStatus(medico.isStatus());
			medicoResponse.setCrm(medico.getCrm());
			medicoResponse.setEspecialidadeMedica(medico.getEspecialidadesMedicas());
			medicoResponse.setFilial(new EmpresaFilialResponse(medico.getFilial()));

			medicosResponse.add(medicoResponse);
		}

		return new PagedResponse<>(medicosResponse, medicos.getNumber(), medicos.getSize(),
				medicos.getTotalElements(), medicos.getTotalPages(), medicos.isLast());

	}
	
	public MedicoResponse getMedicoById(Long medicoId) {
		Medico medico = medicoRepository.findById(medicoId)
				.orElseThrow(() -> new ResourceNotFoundException("Medico", "id", medicoId));
		
		Usuario userCreated = usuarioRepository.findById(medico.getCreatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", medico.getCreatedBy()));

		Usuario userUpdated = usuarioRepository.findById(medico.getUpdatedBy())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", medico.getUpdatedBy()));

		MedicoResponse medicoResponse = new MedicoResponse(medico, medico.getCreatedAt(),
				userCreated.getNome(), medico.getUpdatedAt(), userUpdated.getNome());

		return medicoResponse;
	}
	
	public void deleteMedico(Long id) {
		Medico medico = medicoRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Medico", "id", id));
		medicoRepository.delete(medico);
	}
	
	public ResponseEntity<?> updateMedico(MedicoRequest medicoRequest) {
		
		Medico medico = medicoRepository.findById(medicoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Medico", "id", medicoRequest.getId()));
		
		if(!medico.getCpf().equals(medicoRequest.getCpf())) {
			if (medicoRepository.existsByCpf(medicoRequest.getCpf())) {
				return Utils.badRequest(false, "Já existe um médico com este cpf.");
			}
		}
		
		if(!medico.getCrm().equals(medicoRequest.getCrm())) {
			if (medicoRepository.existsByCrm(medicoRequest.getCrm())) {
				return Utils.badRequest(false, "Já existe um médico com este crm.");
			}
		}
		
		setEntidades(medico, medicoRequest);
		
		if (Objects.nonNull(medicoRequest.getNome()))
			medico.setNome(medicoRequest.getNome());

		if (Objects.nonNull(medicoRequest.getMatricula()))
			medico.setMatricula(medicoRequest.getMatricula());

		if (Objects.nonNull(medicoRequest.getDataNascimento()))
			medico.setDataNascimento(medicoRequest.getDataNascimento());

		if (Objects.nonNull(medicoRequest.getSexo()))
			medico.setSexo(GeneroEnum.valueOf(medicoRequest.getSexo()));

		if (Utils.checkStr(medicoRequest.getEstadoCivil()))
			medico.setEstadoCivil(EstadoCivilEnum.valueOf(medicoRequest.getEstadoCivil()));

		if (Objects.nonNull(medicoRequest.getNomeMae()))
			medico.setNomeMae(medicoRequest.getNomeMae());

		if (Objects.nonNull(medicoRequest.getNomePai()))
			medico.setNomePai(medicoRequest.getNomePai());

		if (Objects.nonNull(medicoRequest.getLogradouro()))
			medico.setLogradouro(medicoRequest.getLogradouro());

		if (Objects.nonNull(medicoRequest.getNumero()))
			medico.setNumero(medicoRequest.getNumero());

		if (Objects.nonNull(medicoRequest.getComplemento()))
			medico.setComplemento(medicoRequest.getComplemento());

		if (Objects.nonNull(medicoRequest.getBairro()))
			medico.setBairro(medicoRequest.getBairro());

		if (Objects.nonNull(medicoRequest.getCep()))
			medico.setCep(medicoRequest.getCep());

		if (Objects.nonNull(medicoRequest.getIdentidade()))
			medico.setIdentidade(medicoRequest.getIdentidade());

		if (Objects.nonNull(medicoRequest.getOrgaoExpeditor()))
			medico.setOrgaoExpeditor(medicoRequest.getOrgaoExpeditor());

		if (Objects.nonNull(medicoRequest.getDataExpedicaoRg()))
			medico.setDataExpedicaoRg(medicoRequest.getDataExpedicaoRg());

		if (Objects.nonNull(medicoRequest.getCpf()))
			medico.setCpf(medicoRequest.getCpf());

		if (Objects.nonNull(medicoRequest.getCrm()))
			medico.setCrm(medicoRequest.getCrm());

		if (Objects.nonNull(medicoRequest.getDataExpedicaoCrm()))
			medico.setDataExpedicaoCrm(medicoRequest.getDataExpedicaoCrm());
		
		if (Objects.nonNull(medicoRequest.getDataExpedicaoRg()))
			medico.setDataExpedicaoRg(medicoRequest.getDataExpedicaoRg());
		
		if (Objects.nonNull(medicoRequest.isCoordenadorMedico()))
			medico.setCoordenadorMedico(medicoRequest.isCoordenadorMedico());
		
		if (Objects.nonNull(medicoRequest.isClinicoGeral()))
			medico.setClinicoGeral(medicoRequest.isClinicoGeral());
		
		if (Objects.nonNull(medicoRequest.isEspecialista()))
			medico.setEspecialista(medicoRequest.isEspecialista());
		
		if (Objects.nonNull(medicoRequest.getEmails())) {
			medico.setEmails(medicoRequest.getEmails());
		}
		
		if (Objects.nonNull(medicoRequest.getTelefones())) {
			medico.setTelefones(new ArrayList<>());
			for (TelefoneRequest tr : medicoRequest.getTelefones()) {
				Telefone t = new Telefone(tr);
				medico.getTelefones().add(t);
			}
		}
		
		if(Objects.nonNull(medico.getTelefones())) {
			for(Telefone t : medico.getTelefones()) {
				telefoneRepository.save(t);
			}
		}
		
		medico.setStatus(medicoRequest.isStatus());
		
		medicoRepository.save(medico);
		
		return Utils.created(true, "Médico alterado com sucesso!"); 

	}
	
	private void setEntidades(Medico medico, MedicoRequest medicoRequest) {
		
		if (Objects.nonNull(medicoRequest.getEmpresaId())) {
			EmpresaFilial empresa = empresaFilialRepository.findById(medicoRequest.getEmpresaId()).orElseThrow(
					() -> new ResourceNotFoundException("EmpresaFilial", "id", medicoRequest.getEmpresaId()));
			medico.setEmpresa(empresa);
		}

		if (Objects.nonNull(medicoRequest.getFilialId())) {
			EmpresaFilial filial = empresaFilialRepository.findById(medicoRequest.getFilialId()).orElseThrow(
					() -> new ResourceNotFoundException("EmpresaFilial", "id", medicoRequest.getFilialId()));
			medico.setFilial(filial);
		}
		
		if (Objects.nonNull(medicoRequest.getNaturalidade())) {
			Municipio naturalidade = municipioRepository.findById(medicoRequest.getNaturalidade().getId())
					.orElseThrow(() -> new ResourceNotFoundException("municipio", "id",
							medicoRequest.getNaturalidade().getId()));
			medico.setNaturalidade(naturalidade);
		} 

		if (Objects.nonNull(medicoRequest.getNacionalidade())) {
			Nacionalidade nacionalidade = nacionalidadeRepository.findById(medicoRequest.getNacionalidade().getId())
					.orElseThrow(() -> new ResourceNotFoundException("Nacionalidade", "id",
							medicoRequest.getNacionalidade().getId()));
			medico.setNacionalidade(nacionalidade);
		} 
		
		if (Objects.nonNull(medicoRequest.getMunicipioId())) {
			Municipio municipio = municipioRepository.findById(medicoRequest.getMunicipioId()).orElseThrow(
					() -> new ResourceNotFoundException("Municipio", "id", medicoRequest.getMunicipioId()));
			medico.setMunicipio(municipio);
		}
		
		if (Objects.nonNull(medicoRequest.getRgUfId())) {
			UnidadeFederativa rgUf = unidadeFederativaRepository.findById(medicoRequest.getRgUfId()).orElseThrow(
					() -> new ResourceNotFoundException("UnidadeFederativa", "id", medicoRequest.getRgUfId()));
			medico.setRgUf(rgUf);
		}
		
		if (Objects.nonNull(medicoRequest.getCrmUfId())) {
			UnidadeFederativa crmUf = unidadeFederativaRepository.findById(medicoRequest.getCrmUfId()).orElseThrow(
					() -> new ResourceNotFoundException("UnidadeFederativa", "id", medicoRequest.getCrmUfId()));
			medico.setCrmUf(crmUf);
		}
	
		if (Objects.nonNull(medicoRequest.getEnderecoUfId())) {
			UnidadeFederativa enderecoUf = unidadeFederativaRepository.findById(medicoRequest.getEnderecoUfId()).orElseThrow(
					() -> new ResourceNotFoundException("UnidadeFederativa", "id", medicoRequest.getEnderecoUfId()));
			medico.setEnderecoUf(enderecoUf);
		}
		
		if (Objects.nonNull(medicoRequest.getUsuario())) {
			Usuario usuario = usuarioRepository.findById(medicoRequest.getUsuario().getId()).orElseThrow(
					() -> new ResourceNotFoundException("Usuario", "id", medicoRequest.getUsuario().getId()));
			medico.setUsuario(usuario);
		}
		
		if (Objects.nonNull(medicoRequest.getEspecialidadeMedicaId())) {
			medico.setEspecialidadesMedicas(new ArrayList<>());
			
			for(Long id : medicoRequest.getEspecialidadeMedicaId()) {
				medico.getEspecialidadesMedicas().add(new EspecialidadeMedica(id));
			}
		}
		
	}
	
	public List<DadoBasicoDto> getDadoBasicoList(String search) {
		List<DadoBasicoDto> dadoBasicoList = null;

		dadoBasicoList = medicoRepository.getDadoBasicoList(search);

		return dadoBasicoList;
	}
	
	public List<EspecialidadeMedicaDto> getMedicoNome(String nome) {
		List<EspecialidadeMedicaDto> dtoList = new ArrayList<EspecialidadeMedicaDto>();

		Medico medico = medicoRepository.findByNome(nome);
		
		medico.getEspecialidadesMedicas().forEach(especialidadeMedica -> {
			dtoList.add(new EspecialidadeMedicaDto(especialidadeMedica));
		});

		return dtoList;
	}
	
	public Medico getMedicoByUsuarioId(Long id) {
		return medicoRepository.getMedicoByUsarioId(id);
	}

}

