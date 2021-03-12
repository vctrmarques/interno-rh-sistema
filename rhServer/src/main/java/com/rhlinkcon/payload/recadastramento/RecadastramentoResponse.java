package com.rhlinkcon.payload.recadastramento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.rhlinkcon.model.Recadastramento;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.pensionista.PensionistaResponse;
import com.rhlinkcon.util.Projecao;

public class RecadastramentoResponse extends DadoCadastralResponse {

	private Long id;

	private FuncionarioResponse funcionario;

	private PensionistaResponse pensao;

	private RecadastramentoDadosResponse dados;

	private RecadastramentoEnderecoResponse endereco;

	private RecadastramentoContatoResponse contato;

	private LocalDate data;

	private boolean status;

	private List<AnexoResponse> anexos;

	private LocalDate dataNascimento;

	private Integer matricula;

	public RecadastramentoResponse(Recadastramento obj, Projecao projecao) {

		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			setId(obj.getId());

			if (Objects.nonNull(obj.getFuncionario())) {
				setFuncionario(new FuncionarioResponse(obj.getFuncionario(), Projecao.BASICA));
				setDataNascimento(LocalDateTime.ofInstant(obj.getFuncionario().getDataNascimento(), ZoneOffset.UTC)
						.toLocalDate());
				setMatricula(obj.getFuncionario().getMatricula());
			}

			if (Objects.nonNull(obj.getPensao())) {
				setPensao(new PensionistaResponse(obj.getPensao(), Projecao.BASICA));
				setDataNascimento(obj.getPensao().getDataNascimento());
				setMatricula(obj.getPensao().getMatricula());
			}

			setData(obj.getData());

			setStatus(obj.isStatus());
		}

		if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			if (Objects.nonNull(obj.getDados()))
				setDados(new RecadastramentoDadosResponse(obj.getDados()));

			if (Objects.nonNull(obj.getEndereco()))
				setEndereco(new RecadastramentoEnderecoResponse(obj.getEndereco()));

			if (Objects.nonNull(obj.getContato()))
				setContato(new RecadastramentoContatoResponse(obj.getContato()));
		}

		if (projecao.equals(Projecao.COMPLETA)) {
			if (Objects.nonNull(obj.getAnexos()))
				setAnexos(obj.getAnexos().stream().map(anexo -> new AnexoResponse(anexo)).collect(Collectors.toList()));
		}

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

	public PensionistaResponse getPensao() {
		return pensao;
	}

	public void setPensao(PensionistaResponse pensao) {
		this.pensao = pensao;
	}

	public RecadastramentoDadosResponse getDados() {
		return dados;
	}

	public void setDados(RecadastramentoDadosResponse dados) {
		this.dados = dados;
	}

	public RecadastramentoEnderecoResponse getEndereco() {
		return endereco;
	}

	public void setEndereco(RecadastramentoEnderecoResponse endereco) {
		this.endereco = endereco;
	}

	public RecadastramentoContatoResponse getContato() {
		return contato;
	}

	public void setContato(RecadastramentoContatoResponse contato) {
		this.contato = contato;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<AnexoResponse> getAnexos() {
		return anexos;
	}

	public void setAnexos(List<AnexoResponse> anexos) {
		this.anexos = anexos;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

}
