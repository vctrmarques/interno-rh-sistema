package com.rhlinkcon.payload.dirf;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.Dirf;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.util.Projecao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirfDto extends DadoCadastralResponse {

	private Long id;
	
	@NotNull
	private Integer anoBase;
	
	@NotNull
	private String tipoDeclaracao;
	
	private Integer numeroDeclaracao;
	
	@NotNull
	private DirfEmpresaFilialDto filial;
	
	@NotNull
	private DirfResponsavelReceitaDto responsavelReceita;
	
	@NotNull
	private Integer naturezaDeclarante;
	
	private String situacao;
	
	public DirfDto() {}
	
	public DirfDto(Dirf obj) {
		mountDto(obj, Projecao.COMPLETA);
	}
	
	public DirfDto(Dirf obj, Projecao projecao) {
		mountDto(obj, projecao);
	}
	
	public void mountDto(Dirf obj, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.id = obj.getId();
			this.anoBase = obj.getAnoBase();
			if(Objects.nonNull(obj.getFilial()))
				this.filial = new DirfEmpresaFilialDto(obj.getFilial());
			this.tipoDeclaracao = obj.getTipoDeclaracao().getLabel();
			this.situacao = obj.getSituacao().getLabel();
		}
		if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			if(Objects.nonNull(obj.getNumeroDeclaracao()))
				this.numeroDeclaracao = obj.getNumeroDeclaracao();
		}
		if (projecao.equals(Projecao.COMPLETA)) {
			this.responsavelReceita = new DirfResponsavelReceitaDto(obj.getResponsavelReceita());
			this.naturezaDeclarante = obj.getNaturezaDeclarante();
		}
		
		this.setAlteradoEm(obj.getUpdatedAt());
		this.setCriadoEm(obj.getCreatedAt());
	}
	
	public Integer getAnoExercicio() {
		return this.anoBase + 1;
	}
}
