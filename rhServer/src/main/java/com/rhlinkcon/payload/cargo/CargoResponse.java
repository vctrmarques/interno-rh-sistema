package com.rhlinkcon.payload.cargo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.Atividade;
import com.rhlinkcon.model.Cargo;
import com.rhlinkcon.model.ContagemTempoEspecialEnum;
import com.rhlinkcon.model.Curso;
import com.rhlinkcon.model.GrupoSalarial;
import com.rhlinkcon.model.Habilidade;
import com.rhlinkcon.model.MotivoLeiEnum;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.model.Vinculo;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.categoriaProfissional.CategoriaProfissionalResponse;
import com.rhlinkcon.payload.cbo.CboDto;
import com.rhlinkcon.payload.grupoSalarial.GrupoSalarialResponse;
import com.rhlinkcon.payload.naturezaFuncao.NaturezaFuncaoResponse;
import com.rhlinkcon.payload.processoFuncao.ProcessoFuncaoResponse;
import com.rhlinkcon.payload.sindicato.SindicatoResponse;
import com.rhlinkcon.payload.vinculo.VinculoResponse;
import com.rhlinkcon.util.Projecao;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CargoResponse extends DadoCadastralResponse {

	private Long id;

	private String nome;

	private String codigo;

	private String descricao;

	private Boolean dedicacaoExclusiva;

	private CboDto cbo;

	private NaturezaFuncaoResponse naturezaFuncao;

	private ProcessoFuncaoResponse processoFuncao;

	private CategoriaProfissionalResponse categoriaProfissional;

	private SindicatoResponse sindicato;

	private ContagemTempoEspecialEnum contagemTempoEspecial;

	private MotivoLeiEnum motivoLei;

	private String leiRespaldo;

	private List<VinculoResponse> vinculos;

	private List<Long> atividadesIds;

	private List<Long> habilidadesIds;

	private List<Long> cursosIds;

	private List<Long> verbasIds;

	private GrupoSalarialResponse grupoSalarial;

	public String getLeiRespaldo() {
		return leiRespaldo;
	}

	public void setLeiRespaldo(String leiRespaldo) {
		this.leiRespaldo = leiRespaldo;
	}

	public CargoResponse() {
	}

	public CargoResponse(Cargo cargo, Projecao projecao) {

		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.id = cargo.getId();
			this.nome = cargo.getNome();
			this.codigo = cargo.getCodigo();
			this.descricao = cargo.getDescricao();
		}

		if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			this.dedicacaoExclusiva = cargo.isDedicacaoExclusiva();
			this.leiRespaldo = cargo.getLeiRespaldo();

			if (Objects.nonNull(cargo.getCbo()))
				this.cbo = new CboDto(cargo.getCbo());

			if (Objects.nonNull(cargo.getNaturezaFuncao()))
				this.naturezaFuncao = new NaturezaFuncaoResponse(cargo.getNaturezaFuncao());

			if (Objects.nonNull(cargo.getProcessoFuncao()))
				this.processoFuncao = new ProcessoFuncaoResponse(cargo.getProcessoFuncao());

			if (Objects.nonNull(cargo.getCategoriaProfissional()))
				this.categoriaProfissional = new CategoriaProfissionalResponse(cargo.getCategoriaProfissional());

			if (Objects.nonNull(cargo.getSindicato()))
				this.sindicato = new SindicatoResponse(cargo.getSindicato());

			if (Objects.nonNull(cargo.getGrupoSalarial())) {
				this.grupoSalarial = new GrupoSalarialResponse(cargo.getGrupoSalarial());
			}
		}

		if (projecao.equals(Projecao.COMPLETA)) {

			this.contagemTempoEspecial = cargo.getContagemTempoEspecial();
			this.motivoLei = cargo.getMotivoLei();

			if (cargo.getVinculos() != null) {
				this.vinculos = new ArrayList<>();
				for (Vinculo vinculo : cargo.getVinculos()) {
					this.vinculos.add(new VinculoResponse(vinculo));
				}
			}

			if (!cargo.getAtividades().isEmpty()) {
				this.atividadesIds = new ArrayList<>();
				for (Atividade atividade : cargo.getAtividades()) {
					this.atividadesIds.add(atividade.getId());
				}
			}

			if (!cargo.getHabilidades().isEmpty()) {
				this.habilidadesIds = new ArrayList<>();
				for (Habilidade habilidade : cargo.getHabilidades()) {
					this.habilidadesIds.add(habilidade.getId());
				}
			}

			if (!cargo.getCursos().isEmpty()) {
				this.cursosIds = new ArrayList<>();
				for (Curso curso : cargo.getCursos()) {
					this.cursosIds.add(curso.getId());
				}
			}

			if (!cargo.getVerbas().isEmpty()) {
				this.verbasIds = new ArrayList<>();
				for (Verba verba : cargo.getVerbas()) {
					this.verbasIds.add(verba.getId());
				}
			}
		}
	}

	public CargoResponse(Long id, String nome, String codigo, GrupoSalarial grupoSalarial) {
		this.id = id;
		this.nome = nome;
		this.codigo = codigo;
		if (Objects.nonNull(grupoSalarial)) {
			this.grupoSalarial = new GrupoSalarialResponse(grupoSalarial.getId(), grupoSalarial.getNome());
		}
	}

	public CargoResponse(Cargo cargo) {
		this.id = cargo.getId();
		this.nome = cargo.getNome();
		this.codigo = cargo.getCodigo();
		this.descricao = cargo.getDescricao();
		this.dedicacaoExclusiva = cargo.isDedicacaoExclusiva();
		this.leiRespaldo = cargo.getLeiRespaldo();

		if (Objects.nonNull(cargo.getCbo()))
			this.cbo = new CboDto(cargo.getCbo());

		if (Objects.nonNull(cargo.getNaturezaFuncao()))
			this.naturezaFuncao = new NaturezaFuncaoResponse(cargo.getNaturezaFuncao());

		if (Objects.nonNull(cargo.getProcessoFuncao()))
			this.processoFuncao = new ProcessoFuncaoResponse(cargo.getProcessoFuncao());

		if (Objects.nonNull(cargo.getCategoriaProfissional()))
			this.categoriaProfissional = new CategoriaProfissionalResponse(cargo.getCategoriaProfissional());

		if (Objects.nonNull(cargo.getSindicato()))
			this.sindicato = new SindicatoResponse(cargo.getSindicato());

		if (Objects.nonNull(cargo.getGrupoSalarial())) {
			this.grupoSalarial = new GrupoSalarialResponse(cargo.getGrupoSalarial());
		}

		this.contagemTempoEspecial = cargo.getContagemTempoEspecial();
		this.motivoLei = cargo.getMotivoLei();

		if (cargo.getVinculos() != null) {
			this.vinculos = new ArrayList<>();
			for (Vinculo vinculo : cargo.getVinculos()) {
				this.vinculos.add(new VinculoResponse(vinculo));
			}
		}

		if (!cargo.getAtividades().isEmpty()) {
			this.atividadesIds = new ArrayList<>();
			for (Atividade atividade : cargo.getAtividades()) {
				this.atividadesIds.add(atividade.getId());
			}
		}

		if (!cargo.getHabilidades().isEmpty()) {
			this.habilidadesIds = new ArrayList<>();
			for (Habilidade habilidade : cargo.getHabilidades()) {
				this.habilidadesIds.add(habilidade.getId());
			}
		}

		if (!cargo.getCursos().isEmpty()) {
			this.cursosIds = new ArrayList<>();
			for (Curso curso : cargo.getCursos()) {
				this.cursosIds.add(curso.getId());
			}
		}

		if (!cargo.getVerbas().isEmpty()) {
			this.verbasIds = new ArrayList<>();
			for (Verba verba : cargo.getVerbas()) {
				this.verbasIds.add(verba.getId());
			}
		}

		setAlteradoEm(cargo.getUpdatedAt());
		setCriadoEm(cargo.getCreatedAt());
	}

	public CboDto getCbo() {
		return cbo;
	}

	public void setCbo(CboDto cbo) {
		this.cbo = cbo;
	}

	public NaturezaFuncaoResponse getNaturezaFuncao() {
		return naturezaFuncao;
	}

	public void setNaturezaFuncao(NaturezaFuncaoResponse naturezaFuncao) {
		this.naturezaFuncao = naturezaFuncao;
	}

	public ProcessoFuncaoResponse getProcessoFuncao() {
		return processoFuncao;
	}

	public void setProcessoFuncao(ProcessoFuncaoResponse processoFuncao) {
		this.processoFuncao = processoFuncao;
	}

	public CategoriaProfissionalResponse getCategoriaProfissional() {
		return categoriaProfissional;
	}

	public void setCategoriaProfissional(CategoriaProfissionalResponse categoriaProfissional) {
		this.categoriaProfissional = categoriaProfissional;
	}

	public SindicatoResponse getSindicato() {
		return sindicato;
	}

	public void setSindicato(SindicatoResponse sindicato) {
		this.sindicato = sindicato;
	}

	public ContagemTempoEspecialEnum getContagemTempoEspecial() {
		return contagemTempoEspecial;
	}

	public void setContagemTempoEspecial(ContagemTempoEspecialEnum contagemTempoEspecial) {
		this.contagemTempoEspecial = contagemTempoEspecial;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public MotivoLeiEnum getMotivoLei() {
		return motivoLei;
	}

	public void setMotivoLei(MotivoLeiEnum motivoLei) {
		this.motivoLei = motivoLei;
	}

	public Boolean isDedicacaoExclusiva() {
		return dedicacaoExclusiva;
	}

	public void setDedicacaoExclusiva(Boolean dedicacaoExclusiva) {
		this.dedicacaoExclusiva = dedicacaoExclusiva;
	}

	public List<VinculoResponse> getVinculos() {
		return vinculos;
	}

	public void setVinculos(List<VinculoResponse> vinculos) {
		this.vinculos = vinculos;
	}

	public List<Long> getAtividadesIds() {
		return atividadesIds;
	}

	public void setAtividadesIds(List<Long> atividadesIds) {
		this.atividadesIds = atividadesIds;
	}

	public List<Long> getHabilidadesIds() {
		return habilidadesIds;
	}

	public void setHabilidadesIds(List<Long> habilidadesIds) {
		this.habilidadesIds = habilidadesIds;
	}

	public List<Long> getCursosIds() {
		return cursosIds;
	}

	public void setCursosIds(List<Long> cursosIds) {
		this.cursosIds = cursosIds;
	}

	public GrupoSalarialResponse getGrupoSalarial() {
		return grupoSalarial;
	}

	public void setGrupoSalarial(GrupoSalarialResponse grupoSalarial) {
		this.grupoSalarial = grupoSalarial;
	}

	public List<Long> getVerbasIds() {
		return verbasIds;
	}

	public void setVerbasIds(List<Long> verbasIds) {
		this.verbasIds = verbasIds;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Boolean getDedicacaoExclusiva() {
		return dedicacaoExclusiva;
	}

}
