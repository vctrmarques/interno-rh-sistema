package com.rhlinkcon.service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.PeriodoCertidaoExSegurado;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.repository.PeriodoCertidaoExSeguradoRepository;

@Service
public class PeriodoCertidaoExSeguradoService {
	@Autowired
	private PeriodoCertidaoExSeguradoRepository periodoCertidaoExSeguradoRepository;

	@Autowired
	private UsuarioService usuarioService;

	public void create(PeriodoCertidaoExSegurado periodoCertidaoExSegurado) {
		periodoCertidaoExSeguradoRepository.save(periodoCertidaoExSegurado);
	}

	public void delete(Long id) {
		PeriodoCertidaoExSegurado periodoCertidaoExSegurado = periodoCertidaoExSeguradoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("PeriodoCertidaoExSegurado", "id", id));
		periodoCertidaoExSeguradoRepository.delete(periodoCertidaoExSegurado);
	}
	
} 