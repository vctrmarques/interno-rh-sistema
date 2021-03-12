package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jrimum.utilix.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.filtro.RelatorioDadosAposentadoPensionistaFiltro;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;
import com.rhlinkcon.payload.relatorioDadosAposentadoPensionista.RelatorioDadosAposentadoPensionistaDto;

@Service
public class RelatorioDadosAposentadoPensionistaService {
	
	@Autowired
	private EmpresaFilialService empresaFilialService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	@Autowired
	private PensionistaService pensionistaService;
	
	public RelatorioDadosAposentadoPensionistaDto get(RelatorioDadosAposentadoPensionistaFiltro valor) {
		
		RelatorioDadosAposentadoPensionistaDto dto = new RelatorioDadosAposentadoPensionistaDto();
		
		if(valor.getSituacao().equals("APOSENTADO")) {
			dto.setAposentados(funcionarioService.findAllBySituacaoAposentadoAndFilialIdAndLotacaoId(valor.getFilial(), Collections.isEmpty(valor.getLotacoesId()), valor.getLotacoesId()));
		} else {
			Boolean status = null;
			
			if(Objects.nonNull(valor.getStatus())) 
				status = valor.getStatus().equals("ATIVO");
			
			dto.setPensionistas(pensionistaService.findAllByFuncionarioFilialIdAndFuncionarioLotacaoId(valor.getFilial(), Collections.isEmpty(valor.getLotacoesId()), valor.getLotacoesId(), status));
		}
		 
		return dto;
	}
	
	// Preenche o combo de filiais
	public List<DadoBasicoDto> getPagedListFilial() {
		List<EmpresaFilialResponse> lista = empresaFilialService.getAllEmpresasFiliais(false);
		List<DadoBasicoDto> listaDto = new ArrayList<>();
		
		lista.forEach(e -> {
			listaDto.add(getDadoBasico(e.getId(), e.getNomeFilial()));
		});
		
		return listaDto;
	}

	private DadoBasicoDto getDadoBasico(Long id, String descricao) {
		DadoBasicoDto dto = new DadoBasicoDto();
		dto.setId(id);
		dto.setDescricao(descricao);
		return dto;
	}

	// Preenche o combo de lotações
	public List<DadoBasicoDto> getPagedListLotacao(Long filialId) {
		List<LotacaoResponse> lista = empresaFilialService.getLotacoesByEmpresaFilialId(filialId);
		List<DadoBasicoDto> listaDto = new ArrayList<>();
		
		lista.forEach(e -> {
			listaDto.add(getDadoBasico(e.getId(), e.getDescricao()));
		});
		
		return listaDto;
	}

}
