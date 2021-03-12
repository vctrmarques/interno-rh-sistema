package com.rhlinkcon.service;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.AssinaturaCertidaoExSegurado;
import com.rhlinkcon.model.Usuario;
import com.rhlinkcon.repository.AssinaturaCertidaoExSeguradoRepository;

@Service
public class AssinaturaCertidaoExSeguradoService {
	@Autowired
	private AssinaturaCertidaoExSeguradoRepository assinaturaCertidaoExSeguradoRepository;

	@Autowired
	private UsuarioService usuarioService;

	public void create(AssinaturaCertidaoExSegurado assinaturaCertidaoExSegurado) {
		assinaturaCertidaoExSeguradoRepository.save(assinaturaCertidaoExSegurado);
	}

	public void delete(Long id) {
		AssinaturaCertidaoExSegurado assinaturaCertidaoExSegurado = assinaturaCertidaoExSeguradoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AssinaturaCertidaoExSegurado", "id", id));
		assinaturaCertidaoExSeguradoRepository.delete(assinaturaCertidaoExSegurado);
	}
	
} 