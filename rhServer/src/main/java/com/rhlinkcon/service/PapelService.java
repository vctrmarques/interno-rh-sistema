package com.rhlinkcon.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Menu;
import com.rhlinkcon.model.Papel;
import com.rhlinkcon.payload.papel.PapelResponse;
import com.rhlinkcon.repository.MenuRepository;
import com.rhlinkcon.repository.PapelRepository;
import com.rhlinkcon.util.Projecao;

@Service
public class PapelService {

	@Autowired
	private PapelRepository papelRepository;

	@Autowired
	private MenuRepository menuRepository;

	public List<PapelResponse> listPapeis(Long idMenu) {

		Menu menu = menuRepository.findById(idMenu)
				.orElseThrow(() -> new ResourceNotFoundException("Menu", "id", idMenu));

		Optional<List<Papel>> papeis = papelRepository.findByMenu(menu);

		List<PapelResponse> listPapelResponse = new ArrayList<>();
		if (papeis.isPresent())
			for (Papel papel : papeis.get()) {
				listPapelResponse.add(new PapelResponse(papel, Projecao.MEDIA));
			}
		return listPapelResponse;
	}

	public List<PapelResponse> listPapeisUsuario(Long usuarioId) {

		List<BigInteger> papelIds = papelRepository.listPapelIdsUsuarioLogado(usuarioId);

		List<PapelResponse> listPapelResponse = new ArrayList<>();

		List<Long> menuLongIds = new ArrayList<Long>();
		papelIds.forEach(menu -> menuLongIds.add(Long.parseLong(menu.toString())));

		for (Papel papel : papelRepository.findByIdIn(menuLongIds)) {
			listPapelResponse.add(new PapelResponse(papel, Projecao.MEDIA));
		}
		return listPapelResponse;
	}

	public List<PapelResponse> listPapeisUsuarioMenu(Long usuarioId, Long idMenu) {

		List<BigInteger> papelIds = papelRepository.listPapelIdsUsuarioLogadoMenu(usuarioId, idMenu);

		List<PapelResponse> listPapelResponse = new ArrayList<>();

		List<Long> menuLongIds = new ArrayList<Long>();
		papelIds.forEach(menu -> menuLongIds.add(Long.parseLong(menu.toString())));

		for (Papel papel : papelRepository.findByIdIn(menuLongIds)) {
			listPapelResponse.add(new PapelResponse(papel, Projecao.MEDIA));
		}
		return listPapelResponse;
	}

}
