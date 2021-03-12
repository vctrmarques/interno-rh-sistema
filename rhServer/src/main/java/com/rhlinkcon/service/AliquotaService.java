package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Aliquota;
import com.rhlinkcon.model.CrmCrea;
import com.rhlinkcon.model.FaixaEnum;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.payload.aliquota.AliquotaResponse;
import com.rhlinkcon.payload.crmCrea.CrmCreaResponse;
import com.rhlinkcon.repository.AliquotaRepository;
import com.rhlinkcon.util.Projecao;

import io.jsonwebtoken.lang.Objects;

@Service
public class AliquotaService {
	@Autowired
	private AliquotaRepository aliquotaRepository;
	@Autowired
	private UsuarioService usuarioService;

	public List<AliquotaResponse> findAllByAno(String faixa,Integer ano) {
		List<AliquotaResponse> results = new ArrayList<>();
		List<Aliquota> aliquotas = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		if(java.util.Objects.isNull(ano)) {
			ano = cal.get(Calendar.YEAR);
		}
		if(Strings.isEmpty(faixa) || java.util.Objects.isNull(faixa)) {
			for (FaixaEnum enu : FaixaEnum.values()) {
				aliquotas = aliquotaRepository.findAllByAnoAndFaixa(ano, enu);
				results.add(new AliquotaResponse(enu.toString(), ano,aliquotas));
			}
		}else {
			aliquotas = aliquotaRepository.findAllByAnoAndFaixa(ano, FaixaEnum.getEnumByString(faixa));
			results.add(new AliquotaResponse(faixa, ano,aliquotas));
		}
		
		
		return results;
	}

	public void create(Aliquota aliquota) {
		aliquotaRepository.save(aliquota);
	}

	public void delete(Long id) {
		Aliquota aliquota = aliquotaRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Alíquota", "id", id));
		aliquotaRepository.delete(aliquota);
	}

	public List<AliquotaResponse> getAliquotasByFaixaAndAno(FaixaEnum faixa, Integer ano) {
		List<AliquotaResponse> aliquotas = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		if(java.util.Objects.isNull(ano)) {
			ano = cal.get(Calendar.YEAR);
		}
		List<Aliquota> lists = aliquotaRepository.findAllByAnoAndFaixa(ano, faixa);
		if(!lists.isEmpty()) {
			lists.forEach(a->{
				AliquotaResponse aliquotaResponse = new AliquotaResponse(a, Projecao.COMPLETA);
				aliquotaResponse.setCriadoPor(usuarioService.criadoPor(a));
				aliquotaResponse.setAlteradoPor(usuarioService.alteradoPor(a));
				aliquotas.add(aliquotaResponse);
			});
		}
		return aliquotas;
	}

	public List<Long> searchAnos(String faixa) {
		return aliquotaRepository.findDistinctAnoByFaixa(FaixaEnum.getEnumByString(faixa));
	}
	
	public Aliquota getAliquotaByFaixaAndValorReferencia(Double valorReferencia, FaixaEnum faixa) {
		Calendar cal = Calendar.getInstance();
		Integer ano = cal.get(Calendar.YEAR);
		
		List<Aliquota> lists = aliquotaRepository.findAllByAnoAndFaixa(ano, faixa);
		if(!lists.isEmpty()) {
			return lists.stream()
					.filter(item -> (valorReferencia > item.getValorInicial() && item.getValorFinal() != null && valorReferencia <= item.getValorFinal()) || 
							 valorReferencia > item.getValorInicial() && (item.getValorFinal() == null || item.getValorFinal() == 0D))
					.findFirst().orElse(null);
		}
		
		return null;
		
	}
	
}
