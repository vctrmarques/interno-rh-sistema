package com.rhlinkcon.payload.licencaMedica;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;

import com.rhlinkcon.model.ClassificacaoInternacionalDoenca;
import com.rhlinkcon.model.Convenio;
import com.rhlinkcon.model.EmpresaFilial;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.LicencaMedica;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.classificacaoInternacionalDoenca.ClassificacaoInternacionalDoencaResponse;
import com.rhlinkcon.payload.convenio.ConvenioResponse;
import com.rhlinkcon.payload.crmCrea.CrmCreaResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.motivoAfastamento.MotivoAfastamentoResponse;
import com.rhlinkcon.payload.situacaoFuncional.SituacaoFuncionalResponse;
import com.rhlinkcon.util.Projecao;

public class LicencaMedicaResponse extends DadoCadastralResponse {
	private Long id;
	
	private FuncionarioResponse funcionario;

	private CrmCreaResponse crm;

	private SituacaoFuncionalResponse afastamento;

	private MotivoAfastamentoResponse motivoAfastamento;
	
	private ClassificacaoInternacionalDoencaResponse cid;

	private Instant periodoInicial;

	private Instant periodoFinal;

	private Instant dataInspecao;

	public LicencaMedicaResponse(LicencaMedica licencaMedica, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.funcionario = new FuncionarioResponse(licencaMedica.getFuncionario());
			this.id = licencaMedica.getId();
			if (Objects.nonNull(licencaMedica.getCrm())) {
				this.crm = new CrmCreaResponse(licencaMedica.getCrm(), projecao);
			}
			this.afastamento = new SituacaoFuncionalResponse(licencaMedica.getAfastamento());
			this.motivoAfastamento = new MotivoAfastamentoResponse(licencaMedica.getMotivoAfastamento());
			this.periodoInicial = licencaMedica.getPeriodoInicial();
			this.periodoFinal = licencaMedica.getPeriodoFinal();
			this.dataInspecao = licencaMedica.getDataInspecao();
			this.cid = new ClassificacaoInternacionalDoencaResponse(licencaMedica.getCid());

			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {

				if (projecao.equals(Projecao.COMPLETA)) {
					setCriadoEm(licencaMedica.getCreatedAt());
					setAlteradoEm(licencaMedica.getUpdatedAt());
				}
			}

		}

	}

	public LicencaMedicaResponse() {

	}

	public CrmCreaResponse getCrm() {
		return crm;
	}

	public void setCrm(CrmCreaResponse crm) {
		this.crm = crm;
	}

	

	public SituacaoFuncionalResponse getAfastamento() {
		return afastamento;
	}

	public void setAfastamento(SituacaoFuncionalResponse afastamento) {
		this.afastamento = afastamento;
	}

	public MotivoAfastamentoResponse getMotivoAfastamento() {
		return motivoAfastamento;
	}

	public void setMotivoAfastamento(MotivoAfastamentoResponse motivoAfastamento) {
		this.motivoAfastamento = motivoAfastamento;
	}

	public Instant getPeriodoInicial() {
		return periodoInicial;
	}

	public void setPeriodoInicial(Instant periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	public Instant getPeriodoFinal() {
		return periodoFinal;
	}

	public void setPeriodoFinal(Instant periodoFinal) {
		this.periodoFinal = periodoFinal;
	}


	public Instant getDataInspecao() {
		return dataInspecao;
	}

	public void setDataInspecao(Instant dataInspecao) {
		this.dataInspecao = dataInspecao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public ClassificacaoInternacionalDoencaResponse getCid() {
		return cid;
	}

	public void setCid(ClassificacaoInternacionalDoencaResponse cid) {
		this.cid = cid;
	}

	
	
}
