package com.rhlinkcon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "item_formula")
public class ItemFormula implements Comparable<ItemFormula> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "verba_id")
	private Verba verba;

	@NotNull
	@Column(name = "ordem")
	private Integer ordem;

	@Column(name = "valor")
	private String valor;

	@ManyToOne
	@JoinColumn(name = "rotina_id")
	private Verba rotina;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	@NotNull
	private ItemFormulaTipoEnum tipo;

	public ItemFormula() {

	}

	@Override
	public int compareTo(ItemFormula arg0) {
		return this.ordem.compareTo(arg0.getOrdem());
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

	public ItemFormulaTipoEnum getTipo() {
		return tipo;
	}

	public void setTipo(ItemFormulaTipoEnum tipo) {
		this.tipo = tipo;
	}

	public Verba getVerba() {
		return verba;
	}

	public void setVerba(Verba verba) {
		this.verba = verba;
	}

	public Verba getRotina() {
		return rotina;
	}

	public void setRotina(Verba rotina) {
		this.rotina = rotina;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
