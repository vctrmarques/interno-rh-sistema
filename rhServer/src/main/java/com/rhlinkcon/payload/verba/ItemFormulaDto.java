package com.rhlinkcon.payload.verba;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.rhlinkcon.model.Contracheque;
import com.rhlinkcon.model.FaixaEnum;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.ItemFormula;
import com.rhlinkcon.model.ItemFormulaTipoEnum;
import com.rhlinkcon.model.ParametroGlobalChaveEnum;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.util.MotorCalculoAttribute;

public class ItemFormulaDto {

	private Long id;

	private Integer ordem;

	private String valor;

	private DadoBasicoDto rotina;

	private String tipo;

	private String descricao;

	public ItemFormulaDto() {

	}

	public ItemFormulaDto(ItemFormula itemFormula) {
		this.id = itemFormula.getId();
		this.ordem = itemFormula.getOrdem();
		this.valor = itemFormula.getValor();
		if (itemFormula.getTipo().equals(ItemFormulaTipoEnum.ROTINA)) {
			this.rotina = new DadoBasicoDto(itemFormula.getRotina());
			this.descricao = this.rotina.getDescricao();
		} else if (itemFormula.getTipo().equals(ItemFormulaTipoEnum.CODIGO)) {
			switch (this.valor) {
			case "if (":
				this.descricao = "SE";
				break;
			case "} else {":
				this.descricao = "SENÃO";
				break;
			case "} else if (":
				this.descricao = "SENÃO SE";
				break;
			case "}":
				this.descricao = "FIM DO SE";
				break;
			case ") {":
				this.descricao = "ENTÃO";
				break;
			case "&&":
				this.descricao = "E";
				break;
			case "||":
				this.descricao = "OU";
				break;
			case "===":
				this.descricao = "IGUAL";
				break;
			default:
				this.descricao = this.valor;
				break;
			}
		} else if (itemFormula.getTipo().equals(ItemFormulaTipoEnum.ATRIBUTO)) {

			// Atributos de Funcionário
			this.descricao = getLabelMotorCalcAttribByClass(new Funcionario(), itemFormula.getValor());

			// Atributos de Pensionista
			if (Objects.isNull(this.descricao)) {
				this.descricao = getLabelMotorCalcAttribByClass(new Pensionista(), itemFormula.getValor());
			}

			// Atributos de Contracheque
			if (Objects.isNull(this.descricao)) {
				this.descricao = getLabelMotorCalcAttribByClass(new Contracheque(), itemFormula.getValor());
			}

			// Atributos de Verba
			if (Objects.isNull(this.descricao)) {
				this.descricao = getLabelMotorCalcAttribByClass(new Verba(), itemFormula.getValor());
			}

			// Atributos de Parametro Global
			if (Objects.isNull(this.descricao)) {
				ParametroGlobalChaveEnum paramGlobalEnum = ParametroGlobalChaveEnum
						.getEnumByString(itemFormula.getValor());

				if (Objects.nonNull(paramGlobalEnum)) {
					this.descricao = paramGlobalEnum.getLabel();
				}
			}

			// Atributos de Alíquota de IRRF
			if (Objects.isNull(this.descricao)) {
				FaixaEnum faixaEnum = FaixaEnum.getEnumByString(itemFormula.getValor());
				if (Objects.nonNull(faixaEnum)) {
					this.descricao = "Alíquota de IRRF";
				}
			}

		} else {
			this.descricao = this.valor;
		}
		this.tipo = itemFormula.getTipo().toString();
	}

	private String getLabelMotorCalcAttribByClass(Object objectClass, String nameAttribuite) {
		List<Method> methods = Arrays.asList(objectClass.getClass().getDeclaredMethods());

		for (Method method : methods) {
			if (method.isAnnotationPresent(MotorCalculoAttribute.class)) {
				MotorCalculoAttribute attribute = method.getAnnotation(MotorCalculoAttribute.class);
				if (attribute.name().equals(nameAttribuite)) {
					return attribute.label();
				}
			}
		}

		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public DadoBasicoDto getRotina() {
		return rotina;
	}

	public void setRotina(DadoBasicoDto rotina) {
		this.rotina = rotina;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
