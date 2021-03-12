package com.rhlinkcon.payload.recadastramento;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.Recadastramento;
import com.rhlinkcon.model.Telefone;
import com.rhlinkcon.util.Utils;

public class ProjectionRecadastramentoResponse {

	private Integer matricula;

	private String nome;

	private Long recadastramentoId;

	private Long funcionarioId;

	private Long pensaoId;

	private String fundo;

	private LocalDate data;

	private LocalDate proximaData;

	private String status;

	private List<String> telefones;

	public ProjectionRecadastramentoResponse(Recadastramento recadastramento, Funcionario funcionario,
			Pensionista pensao) {
		if (Objects.nonNull(recadastramento) && recadastramento.isStatus()) {
			loadRecadastramento(recadastramento);
		} else {
			if (Objects.nonNull(pensao)) {
				loadPensionista(pensao, false);
			} else {
				loadFuncionario(funcionario, false);
			}
		}

		loadStatus();
	}

	public ProjectionRecadastramentoResponse(ProjectionRecadastramento item) {
		if (Objects.nonNull(item.getRecadastramento()) && item.getRecadastramento().isStatus()) {
			loadRecadastramento(item.getRecadastramento());
		} else {
			if (Objects.nonNull(item.getPensao())) {
				loadPensionista(item.getPensao(), false);
			} else {
				loadFuncionario(item.getFuncionario(), false);
			}
		}

		loadStatus();
	}

	private void loadStatus() {
		LocalDate proxima = getProximaData();
		LocalDate atual = LocalDate.now();

		if (atual.isBefore(proxima)) {
			Long duracao = ChronoUnit.DAYS.between(atual, proxima);
			if (duracao < 30) {
				setStatus("Perto de vencer");
			} else {
				setStatus("Recadastrado");
			}
		} else {
			setStatus("Não recadastrado");
		}

	}

	private void loadRecadastramento(Recadastramento item) {
		setRecadastramentoId(item.getId());
		if (Objects.nonNull(item.getPensao())) {
			setFuncionarioId(item.getPensao().getFuncionario().getId());
		} else {
			setFuncionarioId(item.getFuncionario().getId());
		}

		setData(item.getData());

		loadTelefones(item);

		if (Objects.nonNull(item.getPensao())) {
			loadPensionista(item.getPensao(), true);
		} else {
			loadFuncionario(item.getFuncionario(), true);
		}
	}

	private void loadTelefones(Recadastramento item) {
		setTelefones(new ArrayList<>());
		if (Objects.nonNull(item.getEndereco()) && Utils.checkList(item.getEndereco().getTelefones())) {
			for (Telefone t : item.getEndereco().getTelefones()) {
				String numero = t.getNumero();
				getTelefones().add(numero);
			}
		}

		if (Objects.nonNull(item.getContato()) && Utils.checkList(item.getContato().getTelefones())) {
			for (Telefone t : item.getContato().getTelefones()) {
				String numero = t.getNumero();
				getTelefones().add(numero);
			}
		}

	}

	private void loadPensionista(Pensionista item, boolean isRecadastro) {
		setPensaoId(item.getId());
		setFuncionarioId(item.getFuncionario().getId());
		setMatricula(item.getMatricula());
		setNome(item.getNome());
		setFundo(item.getFuncionario().getFundo().getLabel());
		if (!isRecadastro) {
			setData(item.getPensaoPagamento().getDataPrimeiroPagamento());
		}
		LocalDate dataTemp = getData();
		dataTemp = dataTemp.plusMonths(6);
		setProximaData(dataTemp);

	}

	private void loadFuncionario(Funcionario item, boolean isRecadastro) {
		setFuncionarioId(item.getId());
		setMatricula(item.getMatricula());
		setNome(item.getNome());
		setFundo(item.getFundo().getLabel());

		if (!isRecadastro) {

			if (Objects.isNull(item.getDataInicioSituacaoFuncional())) {
				// Provocará Status Não Recadastrado, pois não existe recadastramento ou data de
				// inicio.
				setData(LocalDate.now().minusYears(1));
			} else {
				LocalDate inicioSitFuncional = item.getDataInicioSituacaoFuncional().atZone(ZoneOffset.UTC)
						.toLocalDate();

				// Calculando o último aniversário que será usado como data base para o calculo
				LocalDate dataNascLocalDate = item.getDataNascimento().atZone(ZoneOffset.UTC).toLocalDate();
				Period period = Period.between(dataNascLocalDate, inicioSitFuncional);
				LocalDate ultimoAniversario = dataNascLocalDate.plusYears(period.getYears());

				setData(ultimoAniversario);
			}

		}
		LocalDate dataTemp = getData();
		dataTemp = dataTemp.plusYears(1);
		setProximaData(dataTemp);

	}

	public Integer getMatricula() {
		return matricula;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getFundo() {
		return fundo;
	}

	public void setFundo(String fundo) {
		this.fundo = fundo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public Long getRecadastramentoId() {
		return recadastramentoId;
	}

	public void setRecadastramentoId(Long recadastramentoId) {
		this.recadastramentoId = recadastramentoId;
	}

	public Long getFuncionarioId() {
		return funcionarioId;
	}

	public void setFuncionarioId(Long funcionarioId) {
		this.funcionarioId = funcionarioId;
	}

	public Long getPensaoId() {
		return pensaoId;
	}

	public void setPensaoId(Long pensaoId) {
		this.pensaoId = pensaoId;
	}

	public LocalDate getProximaData() {
		return proximaData;
	}

	public void setProximaData(LocalDate proximaData) {
		this.proximaData = proximaData;
	}

	public List<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<String> telefones) {
		this.telefones = telefones;
	}

}
