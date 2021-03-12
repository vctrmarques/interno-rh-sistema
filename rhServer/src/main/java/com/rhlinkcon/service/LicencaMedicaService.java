package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.rhlinkcon.audit.UserDateAudit;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.SituacaoFuncional;
import com.rhlinkcon.model.Convenio;
import com.rhlinkcon.model.CrmCrea;
import com.rhlinkcon.model.CrmCreaEnum;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.LicencaMedica;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.model.MotivoAfastamento;
import com.rhlinkcon.model.TipoContaLotacaoEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.crmCrea.CrmCreaRequest;
import com.rhlinkcon.payload.crmCrea.CrmCreaResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.payload.licencaMedica.LicencaMedicaRequest;
import com.rhlinkcon.payload.licencaMedica.LicencaMedicaResponse;
import com.rhlinkcon.payload.lotacao.LotacaoRequest;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.repository.SituacaoFuncionalRepository;
import com.rhlinkcon.repository.CentroCustoRepository;
import com.rhlinkcon.repository.CrmCreaRepository;
import com.rhlinkcon.repository.EmpresaFilialRepository;
import com.rhlinkcon.repository.LicencaMedicaRepository;
import com.rhlinkcon.repository.LotacaoRepository;
import com.rhlinkcon.repository.MotivoAfastamentoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.Projecao;
import com.rhlinkcon.util.Utils;

@Service
public class LicencaMedicaService {
	@Autowired
	private LicencaMedicaRepository licencaMedicaRepository;
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private SituacaoFuncionalRepository situacaoFuncionalRepository;
	@Autowired
	private MotivoAfastamentoRepository motivoAfastamentoRepository;
	@Autowired
	private CrmCreaRepository crmCreaRepository;

	public PagedResponse<LicencaMedicaResponse> getAllLicencasMedicas(int page, int size, String order, String nomeFuncionario) {
		Utils.validatePageNumberAndSize(page, size);

		// ordenação
		String orderBy = order == null || order.isEmpty() ? "createdAt" : order;
		Direction direction = Sort.Direction.ASC;
		if (orderBy.startsWith("-")) {
			orderBy = orderBy.replace("-", "");
			direction = Sort.Direction.DESC;
		}
		Pageable pageable = PageRequest.of(page, size, direction, orderBy);

		Page<LicencaMedica> licencasMedicas = null;

		if (Utils.checkStr(nomeFuncionario))
			licencasMedicas = licencaMedicaRepository.findAllByFuncionarioNomeIgnoreCaseContaining(nomeFuncionario, pageable);
		else
			licencasMedicas = licencaMedicaRepository.findAll(pageable);

		if (licencasMedicas.getNumberOfElements() == 0) {
			return new PagedResponse<>(Collections.emptyList(), licencasMedicas.getNumber(), licencasMedicas.getSize(), licencasMedicas.getTotalElements(),
					licencasMedicas.getTotalPages(), licencasMedicas.isLast());
		}

		List<LicencaMedicaResponse> licencaMedicaResponses = licencasMedicas.map(licenca -> {
			return new LicencaMedicaResponse(licenca, Projecao.COMPLETA);
		}).getContent();
		return new PagedResponse<>(licencaMedicaResponses, licencasMedicas.getNumber(), licencasMedicas.getSize(), licencasMedicas.getTotalElements(),
				licencasMedicas.getTotalPages(), licencasMedicas.isLast());
	}

	public LicencaMedicaResponse getLicencaById(Long licencaId) {
		LicencaMedica licencaMedica = licencaMedicaRepository.findById(licencaId).orElseThrow(() -> new ResourceNotFoundException("CrmCrea", "id", licencaId));

		LicencaMedicaResponse licencaMedicaResponse = new LicencaMedicaResponse(licencaMedica, Projecao.COMPLETA);

		licencaMedicaResponse.setCriadoPor(usuarioService.criadoPor(licencaMedica));
		licencaMedicaResponse.setAlteradoPor(usuarioService.alteradoPor(licencaMedica));

		return licencaMedicaResponse;
	}

	public void createLicencaMedica(LicencaMedicaRequest licencaMedicaRequest) {		
		LicencaMedica licencaMedica = new LicencaMedica(licencaMedicaRequest);	
		licencaMedicaRepository.save(licencaMedica);
	}
	
	public void deleteLicencaMedica(Long id) {
		LicencaMedica licencaMedica = licencaMedicaRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Licença Médica", "id", id));
		licencaMedicaRepository.delete(licencaMedica);
	}


}
