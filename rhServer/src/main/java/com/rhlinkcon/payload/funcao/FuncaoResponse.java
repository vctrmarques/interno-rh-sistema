package com.rhlinkcon.payload.funcao;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.Atividade;
import com.rhlinkcon.model.Curso;
import com.rhlinkcon.model.Funcao;
import com.rhlinkcon.model.Habilidade;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.atividade.AtividadeResponse;
import com.rhlinkcon.payload.cbo.CboDto;
import com.rhlinkcon.payload.centroCusto.CentroCustoResponse;
import com.rhlinkcon.payload.curso.CursoResponse;
import com.rhlinkcon.payload.habilidade.HabilidadeResponse;
import com.rhlinkcon.payload.requisito.RequisitoResponse;
import com.rhlinkcon.payload.verba.VerbaResponse;
import com.rhlinkcon.payload.vinculo.VinculoResponse;
import com.rhlinkcon.util.Projecao;

public class FuncaoResponse extends DadoCadastralResponse {

	private Long id;

	@NotNull
	private String nome;

	private String descricao;

	private String codigo;

	@NotNull
	private Long cboId;

	private CboDto cbo;

	@NotNull
	private Instant dataCriacao;

	private Instant dataExtincao;

	private Boolean funcaoExtinta;

	private Long naturezaFuncaoId;

	private Long processoFuncaoId;

	private Long categoriaProfissionalId;

	private String categoriaProfissional;

	private List<VinculoResponse> vinculos;

	private List<CursoResponse> cursos;

	private List<HabilidadeResponse> habilidades;

	private List<AtividadeResponse> atividades;

	private List<RequisitoResponse> requisitos;

	private List<VerbaResponse> verbas;

	private Boolean dedicacaoExclusiva;

	private Integer numeroLei;

	private Instant dataLei;

	private String funcaoAcumulavel;

	private String contagemTempoEspecial;

	private String motivoLei;

	private Long grupoSalarialId;

	private CentroCustoResponse centroCusto;

	private List<FuncaoHistoricoLeiResponse> historicoLeis;

	public FuncaoResponse(Funcao funcao) {
		setFuncao(funcao);
	}

	public FuncaoResponse(Funcao funcao, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA)) {
			this.setId(funcao.getId());
			this.setNome(funcao.getNome());
			this.setDescricao(funcao.getDescricao());
			this.setCodigo(funcao.getCodigo());
		}
	}

	public FuncaoResponse(Funcao funcao, Instant criadoEm, String criadoPor, Instant alteradoEm, String alteradoPor) {
		setFuncao(funcao);

		this.setAlteradoEm(alteradoEm);
		this.setAlteradoPor(alteradoPor);
		this.setCriadoEm(criadoEm);
		this.setCriadoPor(criadoPor);
	}

	private void setFuncao(Funcao funcao) {
		this.setCodigo(funcao.getCodigo());
		this.setId(funcao.getId());
		this.setNome(funcao.getNome());
		this.setDescricao(funcao.getDescricao());
		this.setDataCriacao(funcao.getDataCriacao());
		this.setDataExtincao(funcao.getDataExtincao());
		this.setFuncaoExtinta(funcao.getFuncaoExtinta());
		this.setDedicacaoExclusiva(funcao.getDedicacaoExclusiva());
		this.setNumeroLei(funcao.getNumeroLei());
		this.setDataLei(funcao.getDataLei());

		this.setHistoricoLeis(funcao.getHistoricoLeis().stream()
				.map(historico -> new FuncaoHistoricoLeiResponse(historico)).collect(Collectors.toList()));

		if (Objects.nonNull(funcao.getCentroCusto()))
			this.setCentroCusto(new CentroCustoResponse(funcao.getCentroCusto()));

		if (Objects.nonNull(funcao.getCbo())) {
			this.setCbo(new CboDto(funcao.getCbo()));
			this.setCboId(this.getCbo().getId());

		}

		if (Objects.nonNull(funcao.getNaturezaFuncao()))
			this.setNaturezaFuncaoId(funcao.getNaturezaFuncao().getId());

		if (Objects.nonNull(funcao.getProcessoFuncao()))
			this.setProcessoFuncaoId(funcao.getProcessoFuncao().getId());

		if (Objects.nonNull(funcao.getCategoriaProfissional())) {
			this.setCategoriaProfissionalId(funcao.getCategoriaProfissional().getId());
			this.categoriaProfissional = funcao.getCategoriaProfissional().getDescricao();
		}

		if (Objects.nonNull(funcao.getFuncaoAcumulavel()))
			this.setFuncaoAcumulavel(funcao.getFuncaoAcumulavel().toString());

		if (Objects.nonNull(funcao.getContagemTempoEspecial()))
			this.setContagemTempoEspecial(funcao.getContagemTempoEspecial().toString());

		if (Objects.nonNull(funcao.getMotivoLei()))
			this.setMotivoLei(funcao.getMotivoLei().toString());

		if (Objects.nonNull(funcao.getGrupoSalarial()))
			this.setGrupoSalarialId(funcao.getGrupoSalarial().getId());

		if (!Objects.isNull(funcao.getHabilidades()) && funcao.getHabilidades().size() > 0) {
			this.setHabilidades(new ArrayList<>());
			for (Habilidade habilidade : funcao.getHabilidades()) {
				this.getHabilidades().add(new HabilidadeResponse(habilidade));
			}
		} else {
			this.setHabilidades(new ArrayList<>());
		}

		if (!Objects.isNull(funcao.getCursos()) && funcao.getCursos().size() > 0) {
			this.setCursos(new ArrayList<>());
			for (Curso curso : funcao.getCursos()) {
				this.getCursos().add(new CursoResponse(curso));
			}
		} else {
			this.setCursos(new ArrayList<>());
		}

		if (!Objects.isNull(funcao.getRequisitos()) && funcao.getRequisitos().size() > 0) {
			this.setRequisitos(funcao.getRequisitos().stream().map(requisito -> new RequisitoResponse(requisito))
					.collect(Collectors.toList()));
		} else {
			this.setRequisitos(new ArrayList<>());
		}

		if (!Objects.isNull(funcao.getVerbas()) && funcao.getVerbas().size() > 0) {
			this.setVerbas(
					funcao.getVerbas().stream().map(verba -> new VerbaResponse(verba)).collect(Collectors.toList()));
		} else {
			this.setVerbas(new ArrayList<>());
		}

		if (!Objects.isNull(funcao.getAtividades()) && funcao.getAtividades().size() > 0) {
			this.setAtividades(new ArrayList<>());
			for (Atividade atividade : funcao.getAtividades()) {
				this.getAtividades().add(new AtividadeResponse(atividade));
			}
		} else {
			this.setAtividades(new ArrayList<>());
		}

	}

	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public Long getCboId() {
		return cboId;
	}

	public Instant getDataCriacao() {
		return dataCriacao;
	}

	public Instant getDataExtincao() {
		return dataExtincao;
	}

	public Boolean getFuncaoExtinta() {
		return funcaoExtinta;
	}

	public Long getNaturezaFuncaoId() {
		return naturezaFuncaoId;
	}

	public Long getProcessoFuncaoId() {
		return processoFuncaoId;
	}

	public Long getCategoriaProfissionalId() {
		return categoriaProfissionalId;
	}

	public List<VinculoResponse> getVinculos() {
		return vinculos;
	}

	public Boolean getDedicacaoExclusiva() {
		return dedicacaoExclusiva;
	}

	public Integer getNumeroLei() {
		return numeroLei;
	}

	public Instant getDataLei() {
		return dataLei;
	}

	public String getFuncaoAcumulavel() {
		return funcaoAcumulavel;
	}

	public String getContagemTempoEspecial() {
		return contagemTempoEspecial;
	}

	public String getMotivoLei() {
		return motivoLei;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setCboId(Long cboId) {
		this.cboId = cboId;
	}

	public void setDataCriacao(Instant dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public void setDataExtincao(Instant dataExtincao) {
		this.dataExtincao = dataExtincao;
	}

	public void setFuncaoExtinta(Boolean funcaoExtinta) {
		this.funcaoExtinta = funcaoExtinta;
	}

	public void setNaturezaFuncaoId(Long naturezaFuncaoId) {
		this.naturezaFuncaoId = naturezaFuncaoId;
	}

	public void setProcessoFuncaoId(Long processoFuncaoId) {
		this.processoFuncaoId = processoFuncaoId;
	}

	public void setCategoriaProfissionalId(Long categoriaProfissionalId) {
		this.categoriaProfissionalId = categoriaProfissionalId;
	}

	public void setVinculos(List<VinculoResponse> vinculos) {
		this.vinculos = vinculos;
	}

	public void setDedicacaoExclusiva(Boolean dedicacaoExclusiva) {
		this.dedicacaoExclusiva = dedicacaoExclusiva;
	}

	public void setNumeroLei(Integer numeroLei) {
		this.numeroLei = numeroLei;
	}

	public void setDataLei(Instant dataLei) {
		this.dataLei = dataLei;
	}

	public void setFuncaoAcumulavel(String funcaoAcumulavel) {
		this.funcaoAcumulavel = funcaoAcumulavel;
	}

	public void setContagemTempoEspecial(String contagemTempoEspecial) {
		this.contagemTempoEspecial = contagemTempoEspecial;
	}

	public void setMotivoLei(String motivoLei) {
		this.motivoLei = motivoLei;
	}

	public Long getGrupoSalarialId() {
		return grupoSalarialId;
	}

	public List<RequisitoResponse> getRequisitos() {
		return requisitos;
	}

	public void setRequisitos(List<RequisitoResponse> requisitos) {
		this.requisitos = requisitos;
	}

	public void setGrupoSalarialId(Long grupoSalarialId) {
		this.grupoSalarialId = grupoSalarialId;
	}

	public List<CursoResponse> getCursos() {
		return cursos;
	}

	public void setCursos(List<CursoResponse> cursos) {
		this.cursos = cursos;
	}

	public List<HabilidadeResponse> getHabilidades() {
		return habilidades;
	}

	public void setHabilidades(List<HabilidadeResponse> habilidades) {
		this.habilidades = habilidades;
	}

	public List<AtividadeResponse> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<AtividadeResponse> atividades) {
		this.atividades = atividades;
	}

	public String getCategoriaProfissional() {
		return categoriaProfissional;
	}

	public void setCategoriaProfissional(String categoriaProfissional) {
		this.categoriaProfissional = categoriaProfissional;
	}

	public CboDto getCbo() {
		return cbo;
	}

	public void setCbo(CboDto cbo) {
		this.cbo = cbo;
	}

	public CentroCustoResponse getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCustoResponse centroCusto) {
		this.centroCusto = centroCusto;
	}

	public List<FuncaoHistoricoLeiResponse> getHistoricoLeis() {
		return historicoLeis;
	}

	public void setHistoricoLeis(List<FuncaoHistoricoLeiResponse> historicoLeis) {
		this.historicoLeis = historicoLeis;
	}

	public List<VerbaResponse> getVerbas() {
		return verbas;
	}

	public void setVerbas(List<VerbaResponse> verbas) {
		this.verbas = verbas;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
