package com.rhlinkcon.payload.avaliacaoDesempenho;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.AvaliacaoDesempenho;
import com.rhlinkcon.model.AvaliacaoDesempenhoItem;
import com.rhlinkcon.model.AvaliacaoDesempenhoModeloEnum;
import com.rhlinkcon.model.Cargo;
import com.rhlinkcon.model.DefinicaoOrganico;
import com.rhlinkcon.model.Funcao;
import com.rhlinkcon.model.Lotacao;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.cargo.CargoResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.funcao.FuncaoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.lotacao.LotacaoResponse;

public class AvaliacaoDesempenhoResponse extends DadoCadastralResponse {

	private Long id;

	@NotNull
	private Long empresaFilialId;

	@NotNull
	private String codAvaliacao;

	@NotNull
	private String nome;

	@NotNull
	private AvaliacaoDesempenhoModeloEnum modelo;

	@NotNull
	private String modeloLabel;

	private List<Long> lotacoesIds;

	private List<Long> cargosIds;

	private List<Long> funcoesIds;

	private List<LotacaoResponse> lotacoes;

	private List<CargoResponse> cargos;

	private List<FuncaoResponse> funcoes;

	private List<AvaliacaoDesempenhoItemResponse> itens;

	private FuncionarioResponse funcionario;

	private EmpresaFilialResponse empresaFilial;

	private Date vigenciaInicial;

	private Date vigenciaFinal;

	private String emailResponsavel;

	private String primeiroSubstitutoNome;

	private String primeiroSubstitutoEmail;

	private String segundoSubstitutoNome;

	private String segundoSubstitutoEmail;

	private Long previsaoFuncionarios;

	private Long funcionariosAtuais;

	private Long totalFuncionarios;

	private BigDecimal previsaoCustos;

	private BigDecimal custosAtuais;

	private BigDecimal custoTotal;

	private Boolean confCriticaAvisar;

	private Boolean confCriticaCriticar;

	private Boolean confCriticaNenhum;

	public AvaliacaoDesempenhoResponse(DefinicaoOrganico definicaoOrganico) {
		setDefinicaoOrganico(definicaoOrganico);
	}

	public AvaliacaoDesempenhoResponse(AvaliacaoDesempenho avaliacao) {
		this.id = avaliacao.getId();
		this.empresaFilial = new EmpresaFilialResponse(avaliacao.getEmpresaFilial());
		this.empresaFilialId = avaliacao.getEmpresaFilial().getId();
		this.codAvaliacao = avaliacao.getCodAvaliacao();
		this.nome = avaliacao.getNome();
		this.modelo = avaliacao.getModelo();
		this.modeloLabel = avaliacao.getModelo().getLabel();
		if (!avaliacao.getLotacoes().isEmpty()) {
			this.lotacoes = new ArrayList<>();
			this.lotacoesIds = new ArrayList<>();
			for (Lotacao l : avaliacao.getLotacoes()) {
				this.lotacoes.add(new LotacaoResponse(l));
				this.lotacoesIds.add(l.getId());
			}
		}
		if (!avaliacao.getCargos().isEmpty()) {
			this.cargos = new ArrayList<>();
			this.cargosIds = new ArrayList<>();
			for (Cargo c : avaliacao.getCargos()) {
				this.cargos.add(new CargoResponse(c));
				this.cargosIds.add(c.getId());
			}
		}
		if (!avaliacao.getFuncoes().isEmpty()) {
			this.funcoes = new ArrayList<>();
			this.funcoesIds = new ArrayList<>();
			for (Funcao f : avaliacao.getFuncoes()) {
				this.funcoes.add(new FuncaoResponse(f));
				this.funcoesIds.add(f.getId());
			}
		}
		if (!avaliacao.getItens().isEmpty()) {
			this.itens = new ArrayList<>();
			for (AvaliacaoDesempenhoItem item : avaliacao.getItens()) {
				this.itens.add(new AvaliacaoDesempenhoItemResponse(item));
			}
		}
	}

	public AvaliacaoDesempenhoResponse(DefinicaoOrganico definicaoOrganico, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setDefinicaoOrganico(definicaoOrganico);
		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setDefinicaoOrganico(DefinicaoOrganico definicaoOrganico) {
		this.setId(definicaoOrganico.getId());
//		if (Objects.nonNull(definicaoOrganico.getFuncionario()))
//			this.setFuncionario(new FuncionarioResponse(definicaoOrganico.getFuncionario()));
//		if (Objects.nonNull(definicaoOrganico.getEmpresaFilial()))
//			this.setEmpresaFilial(new EmpresaFilialResponse(definicaoOrganico.getEmpresaFilial()));
//		if (Objects.nonNull(definicaoOrganico.getVigenciaInicial()))
//			this.setVigenciaInicial(definicaoOrganico.getVigenciaInicial());
//		this.setVigenciaFinal(definicaoOrganico.getVigenciaFinal());
//		if (Objects.nonNull(definicaoOrganico.getEmailResponsavel()))
//			this.setEmailResponsavel(definicaoOrganico.getEmailResponsavel());
		this.setPrimeiroSubstitutoNome(definicaoOrganico.getPrimeiroSubstitutoNome());
		this.setPrimeiroSubstitutoEmail(definicaoOrganico.getPrimeiroSubstitutoEmail());
		this.setSegundoSubstitutoNome(definicaoOrganico.getSegundoSubstitutoNome());
		this.setSegundoSubstitutoEmail(definicaoOrganico.getSegundoSubstitutoEmail());
		this.setPrevisaoFuncionarios(definicaoOrganico.getPrevisaoFuncionarios());
		this.setFuncionariosAtuais(definicaoOrganico.getFuncionariosAtuais());
		this.setTotalFuncionarios(definicaoOrganico.getTotalFuncionarios());
		this.setPrevisaoCustos(definicaoOrganico.getPrevisaoCustos());
		this.setCustosAtuais(definicaoOrganico.getCustosAtuais());
		this.setCustoTotal(definicaoOrganico.getCustoTotal());
		this.setConfCriticaAvisar(definicaoOrganico.getConfCriticaAvisar());
		this.setConfCriticaCriticar(definicaoOrganico.getConfCriticaCriticar());
		this.setConfCriticaNenhum(definicaoOrganico.getConfCriticaNenhum());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEmpresaFilialId() {
		return empresaFilialId;
	}

	public void setEmpresaFilialId(Long empresaFilialId) {
		this.empresaFilialId = empresaFilialId;
	}

	public String getCodAvaliacao() {
		return codAvaliacao;
	}

	public void setCodAvaliacao(String codAvaliacao) {
		this.codAvaliacao = codAvaliacao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public AvaliacaoDesempenhoModeloEnum getModelo() {
		return modelo;
	}

	public void setModelo(AvaliacaoDesempenhoModeloEnum modelo) {
		this.modelo = modelo;
	}

	public String getModeloLabel() {
		return modeloLabel;
	}

	public void setModeloLabel(String modeloLabel) {
		this.modeloLabel = modeloLabel;
	}

	public List<Long> getLotacoesIds() {
		return lotacoesIds;
	}

	public void setLotacoesIds(List<Long> lotacoesIds) {
		this.lotacoesIds = lotacoesIds;
	}

	public List<Long> getCargosIds() {
		return cargosIds;
	}

	public void setCargosIds(List<Long> cargosIds) {
		this.cargosIds = cargosIds;
	}

	public List<Long> getFuncoesIds() {
		return funcoesIds;
	}

	public void setFuncoesIds(List<Long> funcoesIds) {
		this.funcoesIds = funcoesIds;
	}

	public FuncionarioResponse getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioResponse funcionario) {
		this.funcionario = funcionario;
	}

	public EmpresaFilialResponse getEmpresaFilial() {
		return empresaFilial;
	}

	public void setEmpresaFilial(EmpresaFilialResponse empresaFilial) {
		this.empresaFilial = empresaFilial;
	}

	public List<LotacaoResponse> getLotacoes() {
		return lotacoes;
	}

	public void setLotacoes(List<LotacaoResponse> lotacoes) {
		this.lotacoes = lotacoes;
	}

	public Date getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Date vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public Date getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(Date vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public String getEmailResponsavel() {
		return emailResponsavel;
	}

	public void setEmailResponsavel(String emailResponsavel) {
		this.emailResponsavel = emailResponsavel;
	}

	public String getPrimeiroSubstitutoNome() {
		return primeiroSubstitutoNome;
	}

	public void setPrimeiroSubstitutoNome(String primeiroSubstitutoNome) {
		this.primeiroSubstitutoNome = primeiroSubstitutoNome;
	}

	public String getPrimeiroSubstitutoEmail() {
		return primeiroSubstitutoEmail;
	}

	public void setPrimeiroSubstitutoEmail(String primeiroSubstitutoEmail) {
		this.primeiroSubstitutoEmail = primeiroSubstitutoEmail;
	}

	public String getSegundoSubstitutoNome() {
		return segundoSubstitutoNome;
	}

	public void setSegundoSubstitutoNome(String segundoSubstitutoNome) {
		this.segundoSubstitutoNome = segundoSubstitutoNome;
	}

	public String getSegundoSubstitutoEmail() {
		return segundoSubstitutoEmail;
	}

	public void setSegundoSubstitutoEmail(String segundoSubstitutoEmail) {
		this.segundoSubstitutoEmail = segundoSubstitutoEmail;
	}

	public Long getPrevisaoFuncionarios() {
		return previsaoFuncionarios;
	}

	public void setPrevisaoFuncionarios(Long previsaoFuncionarios) {
		this.previsaoFuncionarios = previsaoFuncionarios;
	}

	public Long getFuncionariosAtuais() {
		return funcionariosAtuais;
	}

	public void setFuncionariosAtuais(Long funcionariosAtuais) {
		this.funcionariosAtuais = funcionariosAtuais;
	}

	public Long getTotalFuncionarios() {
		return totalFuncionarios;
	}

	public void setTotalFuncionarios(Long totalFuncionarios) {
		this.totalFuncionarios = totalFuncionarios;
	}

	public BigDecimal getPrevisaoCustos() {
		return previsaoCustos;
	}

	public void setPrevisaoCustos(BigDecimal previsaoCustos) {
		this.previsaoCustos = previsaoCustos;
	}

	public BigDecimal getCustosAtuais() {
		return custosAtuais;
	}

	public void setCustosAtuais(BigDecimal custosAtuais) {
		this.custosAtuais = custosAtuais;
	}

	public BigDecimal getCustoTotal() {
		return custoTotal;
	}

	public void setCustoTotal(BigDecimal custoTotal) {
		this.custoTotal = custoTotal;
	}

	public Boolean getConfCriticaAvisar() {
		return confCriticaAvisar;
	}

	public void setConfCriticaAvisar(Boolean confCriticaAvisar) {
		this.confCriticaAvisar = confCriticaAvisar;
	}

	public Boolean getConfCriticaCriticar() {
		return confCriticaCriticar;
	}

	public void setConfCriticaCriticar(Boolean confCriticaCriticar) {
		this.confCriticaCriticar = confCriticaCriticar;
	}

	public Boolean getConfCriticaNenhum() {
		return confCriticaNenhum;
	}

	public void setConfCriticaNenhum(Boolean confCriticaNenhum) {
		this.confCriticaNenhum = confCriticaNenhum;
	}

	public List<CargoResponse> getCargos() {
		return cargos;
	}

	public List<FuncaoResponse> getFuncoes() {
		return funcoes;
	}

	public void setCargos(List<CargoResponse> cargos) {
		this.cargos = cargos;
	}

	public void setFuncoes(List<FuncaoResponse> funcoes) {
		this.funcoes = funcoes;
	}

	public List<AvaliacaoDesempenhoItemResponse> getItens() {
		return itens;
	}

	public void setItens(List<AvaliacaoDesempenhoItemResponse> itens) {
		this.itens = itens;
	}
}