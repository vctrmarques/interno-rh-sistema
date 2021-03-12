package com.rhlinkcon.payload.verba;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rhlinkcon.model.ItemFormula;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.centroCusto.CentroCustoResponse;
import com.rhlinkcon.util.Projecao;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VerbaResponse extends DadoCadastralResponse {

	private Long id;

	private String codigo;

	private String descricaoVerba;

	private String descricaoResumida;

	private String tipoVerba;

	private String destinacaoExterna;

	private Double valorMaximo;

	private String tipoValor;

	private String comentario;

	private Integer contaDebito;

	private Integer contaCredito;

	private Integer contaAuxiliarPrimaria;

	private Integer contaAuxiliarSecundaria;

	private Instant vigenciaInicial;

	private Instant vigenciaFinal;

	private String recorrencia;

	private String faixaAliquota;

	private CentroCustoResponse centroCusto;

	private List<ItemFormulaDto> formulas;

	private List<VerbaResponse> incidenciasResponse;

	private Double valor;

	private Double referencia;

	private String identificadorVerba;

	private DadoBasicoDto verbaAssociada;

	// Atributo usado na FichaFinanceira para informar quais meses a verba se repete
	// no ano e seu valor.
//	private HashMap<String, Double> listMesesValores;

	private List<VerbaValorCompetenciaResponse> listMesesValores;

	public VerbaResponse() {
	}

	public VerbaResponse(Verba verba, Projecao projecao) {
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			setId(verba.getId());
			setCodigo(verba.getCodigo());
			setDescricaoVerba(verba.getDescricaoVerba());
			setDescricaoResumida(verba.getDescricaoResumida());
			setTipoVerba(verba.getTipoVerba().toString());
			if (Objects.nonNull(verba.getIdentificadorVerba())) {
				setIdentificadorVerba(verba.getIdentificadorVerba().toString());
			}
			if (Objects.nonNull(verba.getVerbaAssociada())) {
				setVerbaAssociada(new DadoBasicoDto(verba.getVerbaAssociada()));
			}
			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
				setComentario(verba.getComentario());
				setValorMaximo(verba.getValorMaximo());
				setRecorrencia(verba.getRecorrencia().toString());
				if (Objects.nonNull(verba.getFaixaAliquota()))
					setFaixaAliquota(verba.getFaixaAliquota().toString());
				setContaAuxiliarPrimaria(verba.getContaAuxiliarPrimaria());
				setTipoValor(verba.getTipoValor().toString());
				setContaAuxiliarSecundaria(verba.getContaAuxiliarSecundaria());
				setContaCredito(verba.getContaCredito());
				setContaDebito(verba.getContaDebito());
				setVigenciaInicial(verba.getVigenciaInicial());
				setVigenciaFinal(verba.getVigenciaFinal());
				if (Objects.nonNull(verba.getDestinacaoExterna()))
					setDestinacaoExterna(verba.getDestinacaoExterna().toString());
				setCriadoEm(verba.getCreatedAt());
				setAlteradoEm(verba.getUpdatedAt());
				setCentroCusto(new CentroCustoResponse(verba.getCentroCusto()));
				if (null != verba.getReferencia()) {
					setReferencia(verba.getReferencia());
				}
				if (verba.getFormulas() != null) {
					this.formulas = new ArrayList<ItemFormulaDto>();
					for (ItemFormula itemFormula : verba.getFormulas()) {
						this.formulas.add(new ItemFormulaDto(itemFormula));
					}
				}

				setIncidenciasResponse(new ArrayList<VerbaResponse>());
				if (!verba.getIncidencias().isEmpty()) {
					verba.getIncidencias().forEach(item -> {
						getIncidenciasResponse().add(new VerbaResponse(item, Projecao.BASICA));
					});
				}

			}
		}
	}

	public VerbaResponse(Long id, String descricaoVerba, String codigo, String label) {
		setId(id);
		setDescricaoVerba(descricaoVerba);
		setCodigo(codigo);
		setLabel(label);

	}

	public VerbaResponse(Verba verba) {
		setId(verba.getId());
		setCodigo(verba.getCodigo());
		setComentario(verba.getComentario());
		setDescricaoVerba(verba.getDescricaoVerba());
		setDescricaoResumida(verba.getDescricaoResumida());
		setValorMaximo(verba.getValorMaximo());
		setTipoVerba(verba.getTipoVerba().toString());
		setRecorrencia(verba.getRecorrencia().toString());
		setContaAuxiliarPrimaria(verba.getContaAuxiliarPrimaria());
		setTipoValor(verba.getTipoValor().toString());
		setContaAuxiliarSecundaria(verba.getContaAuxiliarSecundaria());
		setContaCredito(verba.getContaCredito());
		setContaDebito(verba.getContaDebito());

		if (Objects.nonNull(verba.getDestinacaoExterna()))
			setDestinacaoExterna(verba.getDestinacaoExterna().toString());

		if (Objects.nonNull(verba.getFaixaAliquota()))
			setFaixaAliquota(verba.getFaixaAliquota().toString());

		setVigenciaFinal(verba.getVigenciaFinal());
		setVigenciaInicial(verba.getVigenciaInicial());
		setCodigo(verba.getCodigo());
		setCriadoEm(verba.getCreatedAt());
		setAlteradoEm(verba.getUpdatedAt());

		setCentroCusto(new CentroCustoResponse(verba.getCentroCusto()));
		if (null != verba.getReferencia()) {
			setReferencia(verba.getReferencia());
		}

		if (Objects.nonNull(verba.getIdentificadorVerba())) {
			setIdentificadorVerba(verba.getIdentificadorVerba().toString());
			setVerbaAssociada(new DadoBasicoDto(verba.getVerbaAssociada()));
		}
		if (verba.getFormulas() != null) {
			this.formulas = new ArrayList<ItemFormulaDto>();
			for (ItemFormula itemFormula : verba.getFormulas()) {
				this.formulas.add(new ItemFormulaDto(itemFormula));
			}
		}

		setIncidenciasResponse(new ArrayList<VerbaResponse>());
		if (!verba.getIncidencias().isEmpty()) {
			verba.getIncidencias().forEach(item -> {
				getIncidenciasResponse().add(new VerbaResponse(item, Projecao.BASICA));
			});
		}

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricaoVerba() {
		return descricaoVerba;
	}

	public void setDescricaoVerba(String descricaoVerba) {
		this.descricaoVerba = descricaoVerba;
	}

	public String getDescricaoResumida() {
		return descricaoResumida;
	}

	public void setDescricaoResumida(String descricaoResumida) {
		this.descricaoResumida = descricaoResumida;
	}

	public String getTipoVerba() {
		return tipoVerba;
	}

	public void setTipoVerba(String tipoVerba) {
		this.tipoVerba = tipoVerba;
	}

	public String getDestinacaoExterna() {
		return destinacaoExterna;
	}

	public void setDestinacaoExterna(String destinacaoExterna) {
		this.destinacaoExterna = destinacaoExterna;
	}

	public Double getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(Double valorMaximo) {
		this.valorMaximo = valorMaximo;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Integer getContaDebito() {
		return contaDebito;
	}

	public void setContaDebito(Integer contaDebito) {
		this.contaDebito = contaDebito;
	}

	public Integer getContaCredito() {
		return contaCredito;
	}

	public void setContaCredito(Integer contaCredito) {
		this.contaCredito = contaCredito;
	}

	public Integer getContaAuxiliarPrimaria() {
		return contaAuxiliarPrimaria;
	}

	public void setContaAuxiliarPrimaria(Integer contaAuxiliarPrimaria) {
		this.contaAuxiliarPrimaria = contaAuxiliarPrimaria;
	}

	public Integer getContaAuxiliarSecundaria() {
		return contaAuxiliarSecundaria;
	}

	public void setContaAuxiliarSecundaria(Integer contaAuxiliarSecundaria) {
		this.contaAuxiliarSecundaria = contaAuxiliarSecundaria;
	}

	public Instant getVigenciaInicial() {
		return vigenciaInicial;
	}

	public void setVigenciaInicial(Instant vigenciaInicial) {
		this.vigenciaInicial = vigenciaInicial;
	}

	public Instant getVigenciaFinal() {
		return vigenciaFinal;
	}

	public void setVigenciaFinal(Instant vigenciaFinal) {
		this.vigenciaFinal = vigenciaFinal;
	}

	public String getRecorrencia() {
		return recorrencia;
	}

	public void setRecorrencia(String recorrencia) {
		this.recorrencia = recorrencia;
	}

	public String getTipoValor() {
		return tipoValor;
	}

	public void setTipoValor(String tipoValor) {
		this.tipoValor = tipoValor;
	}

	public CentroCustoResponse getCentroCusto() {
		return centroCusto;
	}

	public void setCentroCusto(CentroCustoResponse centroCusto) {
		this.centroCusto = centroCusto;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public void setReferencia(Double referencia) {
		this.referencia = referencia;
	}

	public Double getReferencia() {
		return referencia;
	}

	public List<VerbaResponse> getIncidenciasResponse() {
		return incidenciasResponse;
	}

	public void setIncidenciasResponse(List<VerbaResponse> incidenciasResponse) {
		this.incidenciasResponse = incidenciasResponse;
	}

	public List<VerbaValorCompetenciaResponse> getListMesesValores() {
		return listMesesValores;
	}

	public void setListMesesValores(List<VerbaValorCompetenciaResponse> listMesesValores) {
		this.listMesesValores = listMesesValores;
	}

	public String getIdentificadorVerba() {
		return identificadorVerba;
	}

	public void setIdentificadorVerba(String identificadorVerba) {
		this.identificadorVerba = identificadorVerba;
	}

	public DadoBasicoDto getVerbaAssociada() {
		return verbaAssociada;
	}

	public void setVerbaAssociada(DadoBasicoDto verbaAssociada) {
		this.verbaAssociada = verbaAssociada;
	}

	public List<ItemFormulaDto> getFormulas() {
		return formulas;
	}

	public void setFormulas(List<ItemFormulaDto> formulas) {
		this.formulas = formulas;
	}

	public String getFaixaAliquota() {
		return faixaAliquota;
	}

	public void setFaixaAliquota(String faixaAliquota) {
		this.faixaAliquota = faixaAliquota;
	}

//	public HashMap<String, Double> getListMesesValores() {
//		return listMesesValores;
//	}
//
//	public void setListMesesValores(HashMap<String, Double> listMesesValores) {
//		this.listMesesValores = listMesesValores;
//	}

}
