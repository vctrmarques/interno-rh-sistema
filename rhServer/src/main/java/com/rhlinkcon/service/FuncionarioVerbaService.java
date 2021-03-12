package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.FuncionarioVerba;
import com.rhlinkcon.model.RecorrenciaEnum;
import com.rhlinkcon.model.TipoValorEnum;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.funcionario.FuncionarioVerbaRequest;
import com.rhlinkcon.payload.funcionario.FuncionarioVerbaResponse;
import com.rhlinkcon.repository.FuncionarioVerbaRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.lancamento.LancamentoRepository;

@Service
public class FuncionarioVerbaService {

	@Autowired
	FuncionarioVerbaRepository funcionarioVerbaRepository;

	@Autowired
	FuncionarioRepository funcionarioRepository;

	@Autowired
	VerbaRepository verbaRepository;

	@Autowired
	LancamentoRepository lancamentoRepository;

	public List<FuncionarioVerbaResponse> getVerbasFuncionarioByFuncionarioId(Long funcionarioId) {
		Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
				.orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id", funcionarioId));

		List<FuncionarioVerba> lista = funcionarioVerbaRepository.findByFuncionarioId(funcionario.getId());

		return lista.stream().map(fv -> new FuncionarioVerbaResponse(fv)).collect(Collectors.toList());
	}

	public void createFuncionarioVerba(FuncionarioVerbaRequest request) {

		// Funcionário em questão.
		Funcionario funcionario = funcionarioRepository.findById(request.getFuncionario().getId()).orElseThrow(
				() -> new ResourceNotFoundException("Funcionario", "id", request.getFuncionario().getId()));

		// Lista que vai armazenar todos os ids de funcionário verba, para ser usada na
		// exclusão posteriormente.
		ArrayList<Long> funcinarioVerbaIds = new ArrayList<Long>();

		// Varrendo a lista de verba funcionário para salvar os novos itens adicionados
		request.getVerbasFuncionario().forEach(funcionarioVerba -> {

			if (Objects.isNull(funcionarioVerba.getId())) {

				// Criando funcionário verba novos.
				FuncionarioVerba fv = new FuncionarioVerba();

				fv.setFuncionario(funcionario);
				fv.setVerba(new Verba(funcionarioVerba.getVerbaId()));
				fv.setTipoValor(TipoValorEnum.getEnumByString(funcionarioVerba.getTipo().toString()));
				fv.setRecorrencia(RecorrenciaEnum.getEnumByString(funcionarioVerba.getRecorrencia().toString()));
				fv.setValor(funcionarioVerba.getValor());
				fv.setAtivo(true);

				if (fv.getRecorrencia().equals(RecorrenciaEnum.EM_PARCELA)) {
					if (Objects.isNull(funcionarioVerba.getParcelas()))
						throw new BadRequestException(
								"Verba de Recorrência 'Em Parcelas' tem que ter número de parcelas!");
					fv.setParcelas(funcionarioVerba.getParcelas());
					if (Objects.isNull(funcionarioVerba.getParcelasPagas()))
						fv.setParcelasPagas(0);
					else
						fv.setParcelasPagas(funcionarioVerba.getParcelasPagas());
				}

				// Salvando.
				funcionarioVerbaRepository.save(fv);

				// Pegando o id dos itens para excluir os items deletados da lista.
				funcinarioVerbaIds.add(fv.getId());

			} else {

				// Pegando o id dos itens para excluir os items deletados da lista.
				funcinarioVerbaIds.add(funcionarioVerba.getId());
			}

		});

		// Apagando os funcionário verbas que foram excluídos - Não pertencem mais a
		// lista atual de itens.
		Optional<List<FuncionarioVerba>> funcVerbaListToDeleteOpt = funcionarioVerbaRepository
				.findByFuncionarioAndIdNotIn(funcionario, funcinarioVerbaIds);

		// Checa se existem itens a serem excluídos.
		if (funcVerbaListToDeleteOpt.isPresent()) {

			// Varreo os itens para checar efetuar a exclusão normal ou lógica.
			for (FuncionarioVerba funcVerbaToDelete : funcVerbaListToDeleteOpt.get()) {

				// Checa se existem lançamentos realizados para o funcionário verba.
				if (lancamentoRepository.existsByFuncionarioVerba(funcVerbaToDelete)) {

					// Efetua a exclusão lógica
					funcVerbaToDelete.setAtivo(false);
					funcionarioVerbaRepository.save(funcVerbaToDelete);
				} else {

					// Efetua a exclusao normal
					funcionarioVerbaRepository.delete(funcVerbaToDelete);
				}

			}

		}

	}

}
