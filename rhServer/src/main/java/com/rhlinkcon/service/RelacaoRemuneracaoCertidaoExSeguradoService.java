package com.rhlinkcon.service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.RelacaoRemuneracaoCertidaoExSegurado;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.repository.RelacaoRemuneracaoCertidaoExSeguradoRepository;

@Service
public class RelacaoRemuneracaoCertidaoExSeguradoService {
	@Autowired
	private RelacaoRemuneracaoCertidaoExSeguradoRepository relacaoRemuneracaoCertidaoExSeguradoRepository;

	@Autowired
	private UsuarioService usuarioService;

	public void create(RelacaoRemuneracaoCertidaoExSegurado relacaoRemuneracaoCertidaoExSegurado) {
		relacaoRemuneracaoCertidaoExSeguradoRepository.save(relacaoRemuneracaoCertidaoExSegurado);
	}

	public void delete(Long id) {
		RelacaoRemuneracaoCertidaoExSegurado relacaoRemuneracaoCertidaoExSegurado = relacaoRemuneracaoCertidaoExSeguradoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("RelacaoRemuneracaoCertidaoExSegurado", "id", id));
		relacaoRemuneracaoCertidaoExSeguradoRepository.delete(relacaoRemuneracaoCertidaoExSegurado);
	}
	
} 