package com.rhlinkcon.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rhlinkcon.filtro.FuncionarioFiltro;
import com.rhlinkcon.filtro.PensionistaFiltro;
import com.rhlinkcon.model.Funcionario;
import com.rhlinkcon.model.Pensionista;
import com.rhlinkcon.payload.DadoBasicoDto;
import com.rhlinkcon.payload.servidorPagBloqueado.ServidorPagBloqueadoDto;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.pensionista.PensionistaRepository;
import com.rhlinkcon.util.Utils;

@Service
public class RelatorioServidorPagBloqueadoService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private PensionistaRepository pensionistaRepository;

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private PensionistaService pensionistaService;

	public List<ServidorPagBloqueadoDto> getRelatorioServidorPagBloqueadoList(ServidorPagBloqueadoDto requestFilter) {

		List<ServidorPagBloqueadoDto> servidorPagBloqDtoList = new ArrayList<ServidorPagBloqueadoDto>();
		if (Objects.isNull(requestFilter.getPensionista())) {

			List<ServidorPagBloqueadoDto> servidorPagBloqDtoListFunc = funcionarioService
					.getFuncionariosInaptosPraFolha(requestFilter);
			servidorPagBloqDtoList.addAll(servidorPagBloqDtoListFunc);

			List<ServidorPagBloqueadoDto> servidorPagBloqDtoListPens = pensionistaService
					.getPensionistasInaptosPraFolha(requestFilter);
			servidorPagBloqDtoList.addAll(servidorPagBloqDtoListPens);

		} else if (requestFilter.getPensionista()) {

			List<ServidorPagBloqueadoDto> servidorPagBloqDtoListPens = pensionistaService
					.getPensionistasInaptosPraFolha(requestFilter);
			servidorPagBloqDtoList.addAll(servidorPagBloqDtoListPens);

		} else {

			List<ServidorPagBloqueadoDto> servidorPagBloqDtoListFunc = funcionarioService
					.getFuncionariosInaptosPraFolha(requestFilter);
			servidorPagBloqDtoList.addAll(servidorPagBloqDtoListFunc);

		}

		return servidorPagBloqDtoList;
	}

	public List<DadoBasicoDto> getServidorSearchList(String search) {

		List<DadoBasicoDto> dtoList = new ArrayList<DadoBasicoDto>();

		List<Funcionario> funcionarioList = null;
		List<Pensionista> pensionistaList = null;

		if (Utils.checkStr(search)) {

			FuncionarioFiltro funcionarioFiltro = new FuncionarioFiltro();
			funcionarioFiltro.setSearch(search);
			funcionarioList = funcionarioRepository.filtro(funcionarioFiltro);

			funcionarioList.forEach(item -> {
				dtoList.add(new DadoBasicoDto(item.getId(), "Funcionário: " + item.getLabel() + " - Matrícula: "
						+ item.getMatricula().toString() + " - CPF: " + item.getCpf()));
			});

			PensionistaFiltro pensionistaFiltro = new PensionistaFiltro();
			pensionistaFiltro.setSearchPensionista(search);
			pensionistaList = pensionistaRepository.filtro(pensionistaFiltro);

			pensionistaList.forEach(item -> {
				String cpfStr = item.getCpf() == null ? "" : " - CPF: " + item.getCpf();
				dtoList.add(new DadoBasicoDto(item.getId(), "Pensionista: " + item.getLabel() + " - Matrícula: "
						+ item.getMatricula().toString() + cpfStr));
			});

		}

		return dtoList;
	}
}
