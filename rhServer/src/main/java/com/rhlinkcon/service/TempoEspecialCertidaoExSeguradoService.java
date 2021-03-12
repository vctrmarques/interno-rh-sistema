package com.rhlinkcon.service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.TempoEspecialCertidaoExSegurado;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.repository.TempoEspecialCertidaoExSeguradoRepository;

@Service
public class TempoEspecialCertidaoExSeguradoService {
	@Autowired
	private TempoEspecialCertidaoExSeguradoRepository tempoEspecialCertidaoExSeguradoRepository;

	@Autowired
	private UsuarioService usuarioService;

	public void create(TempoEspecialCertidaoExSegurado tempoEspecialCertidaoExSegurado) {
		tempoEspecialCertidaoExSeguradoRepository.save(tempoEspecialCertidaoExSegurado);
	}

	public void delete(Long id) {
		TempoEspecialCertidaoExSegurado tempoEspecialCertidaoExSegurado = tempoEspecialCertidaoExSeguradoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TempoEspecialCertidaoExSegurado", "id", id));
		tempoEspecialCertidaoExSeguradoRepository.delete(tempoEspecialCertidaoExSegurado);
	}
	
} 