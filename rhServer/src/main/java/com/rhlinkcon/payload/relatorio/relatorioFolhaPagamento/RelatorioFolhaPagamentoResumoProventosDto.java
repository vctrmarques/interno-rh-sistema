package com.rhlinkcon.payload.relatorio.relatorioFolhaPagamento;

import java.util.Objects;

import com.rhlinkcon.model.IdentificacaoVerbaEnum;
import com.rhlinkcon.model.TipoVerba;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.verba.VerbaResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RelatorioFolhaPagamentoResumoProventosDto {

	private String verbas;
	private VerbaResponse verba;
	
	private Long countQuantidade;
	private Double valor;
	private Long id;
	private String identificadorVerba;
	private Long verbaAssociadaId;
	private String descricaoVerba;
	private String tipoVerba;
	private Long filialId;
	
	public RelatorioFolhaPagamentoResumoProventosDto(String verbas, Long countQuantidade, Double valor) {
		this.verbas = verbas;
		this.countQuantidade = countQuantidade;
		this.valor = valor;
	}
	
	public RelatorioFolhaPagamentoResumoProventosDto(Long count, Double valor, Verba verba, Long filialId) {
        this.countQuantidade = count;
        this.valor = valor;
        this.verba = new VerbaResponse(verba);
    }
	
	public RelatorioFolhaPagamentoResumoProventosDto(Long count, Double valor, Long verbaId, IdentificacaoVerbaEnum identificadorVerba, Long verbaAssiadaId, String descricaoVerba, TipoVerba tipoVerba, Long filialId) {
		this.countQuantidade = count;
		this.valor = valor;
		this.id = verbaId;
		this.identificadorVerba = Objects.nonNull(identificadorVerba) ? identificadorVerba.getLabel() : "" ;
		this.verbaAssociadaId = verbaAssiadaId;
		this.descricaoVerba = descricaoVerba;
		this.tipoVerba = Objects.nonNull(tipoVerba) ? tipoVerba.getLabel().toUpperCase() : "" ;
		this.filialId = filialId;
	}
	
	public RelatorioFolhaPagamentoResumoProventosDto(Long count, Double valor, Long verbaId, IdentificacaoVerbaEnum identificadorVerba, Long verbaAssiadaId, String descricaoVerba, TipoVerba tipoVerba) {
		this.countQuantidade = count;
		this.valor = valor;
		this.id = verbaId;
		this.identificadorVerba = Objects.nonNull(identificadorVerba) ? identificadorVerba.getLabel() : "" ;
		this.verbaAssociadaId = verbaAssiadaId;
		this.descricaoVerba = descricaoVerba;
		this.tipoVerba = Objects.nonNull(tipoVerba) ? tipoVerba.getLabel().toUpperCase() : "" ;
	}
	
	
	
}
