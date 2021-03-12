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
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.model.PensionistaVerba;
import com.rhlinkcon.model.RecorrenciaEnum;
import com.rhlinkcon.model.TipoValorEnum;
import com.rhlinkcon.model.Verba;
import com.rhlinkcon.payload.pensionistaVerba.PensionistaVerbaRequest;
import com.rhlinkcon.payload.pensionistaVerba.PensionistaVerbaResponse;
import com.rhlinkcon.repository.PensionistaVerbaRepository;
import com.rhlinkcon.repository.VerbaRepository;
import com.rhlinkcon.repository.lancamento.LancamentoRepository;
import com.rhlinkcon.repository.pensionista.PensionistaRepository;

@Service
public class PensionistaVerbaService {

	@Autowired
	PensionistaVerbaRepository pensionistaVerbaRepository;

	@Autowired
	PensionistaRepository pensionistaRepository;

	@Autowired
	VerbaRepository verbaRepository;

	@Autowired
	LancamentoRepository lancamentoRepository;

	public List<PensionistaVerbaResponse> getVerbasPensionistaByPensionistaId(Long pensionistaId) {
		Pensionista pensionista = pensionistaRepository.findById(pensionistaId)
				.orElseThrow(() -> new ResourceNotFoundException("Pensionista", "id", pensionistaId));

		List<PensionistaVerba> lista = pensionistaVerbaRepository.findByPensionistaId(pensionista.getId());

		return lista.stream().map(fv -> new PensionistaVerbaResponse(fv)).collect(Collectors.toList());
	}

	public void createPensionistaVerba(PensionistaVerbaRequest request) {

		// Pensionista em questão.
		Pensionista pensionista = pensionistaRepository.findById(request.getPensionista().getId()).orElseThrow(
				() -> new ResourceNotFoundException("Pensionista", "id", request.getPensionista().getId()));

		// Lista que vai armazenar todos os ids de pensionista verba, para ser usada na
		// exclusão posteriormente.
		ArrayList<Long> funcinarioVerbaIds = new ArrayList<Long>();

		// Varrendo a lista de pensionista verba para salvar os novos itens adicionados
		request.getVerbasPensionista().forEach(pensionistaVerba -> {

			if (Objects.isNull(pensionistaVerba.getId())) {

				// Criando pensionista verba novos.
				PensionistaVerba pv = new PensionistaVerba();

				pv.setPensionista(pensionista);
				pv.setVerba(new Verba(pensionistaVerba.getVerbaId()));
				pv.setTipoValor(TipoValorEnum.getEnumByString(pensionistaVerba.getTipo().toString()));
				pv.setRecorrencia(RecorrenciaEnum.getEnumByString(pensionistaVerba.getRecorrencia().toString()));
				pv.setValor(pensionistaVerba.getValor());
				pv.setAtivo(true);

				if (pv.getRecorrencia().equals(RecorrenciaEnum.EM_PARCELA)) {
					if (Objects.isNull(pensionistaVerba.getParcelas()))
						throw new BadRequestException(
								"Verba de Recorrência 'Em Parcelas' tem que ter número de parcelas!");
					pv.setParcelas(pensionistaVerba.getParcelas());
					if (Objects.isNull(pensionistaVerba.getParcelasPagas()))
						pv.setParcelasPagas(0);
					else
						pv.setParcelasPagas(pensionistaVerba.getParcelasPagas());
				}

				// Salvando.
				pensionistaVerbaRepository.save(pv);

				// Pegando o id dos itens para excluir os items deletados da lista.
				funcinarioVerbaIds.add(pv.getId());

			} else {

				// Pegando o id dos itens para excluir os items deletados da lista.
				funcinarioVerbaIds.add(pensionistaVerba.getId());
			}

		});

		// Apagando os pensionistas verbas que foram excluídos - Não pertencem mais a
		// lista atual de itens.
		Optional<List<PensionistaVerba>> pensVerbaListToDeleteOpt = pensionistaVerbaRepository
				.findByPensionistaAndIdNotIn(pensionista, funcinarioVerbaIds);

		// Checa se existem itens a serem excluídos.
		if (pensVerbaListToDeleteOpt.isPresent()) {

			// Varreo os itens para checar efetuar a exclusão normal ou lógica.
			for (PensionistaVerba pensVerbaToDelete : pensVerbaListToDeleteOpt.get()) {

				// Checa se existem lançamentos realizados para o pensionista verba.
				if (lancamentoRepository.existsByPensionistaVerba(pensVerbaToDelete)) {

					// Efetua a exclusão lógica
					pensVerbaToDelete.setAtivo(false);
					pensionistaVerbaRepository.save(pensVerbaToDelete);
				} else {

					// Efetua a exclusao normal
					pensionistaVerbaRepository.delete(pensVerbaToDelete);
				}

			}

		}

	}

}
