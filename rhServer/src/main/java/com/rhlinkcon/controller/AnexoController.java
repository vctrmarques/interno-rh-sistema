package com.rhlinkcon.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rhlinkcon.payload.anexo.AnexoRequest;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.payload.generico.ApiResponse;
import com.rhlinkcon.service.AnexoService;
import com.rhlinkcon.util.Utils;

@RestController
@RequestMapping("/api")
public class AnexoController {

	private static final Logger logger = LoggerFactory.getLogger(AnexoController.class);

	@Autowired
	private AnexoService anexoService;

	@PostMapping("/anexo/{pasta}")
	public ResponseEntity<?> createAnexo(@RequestParam("file") MultipartFile file,
			@RequestParam(value = "description", required = false) String description, @PathVariable String pasta) {
		AnexoResponse anexoResponse = anexoService.createAnexo(file, pasta, description);
		return ResponseEntity.ok(new ApiResponse(true, "Anexo criado com sucesso.", anexoResponse));
	}

	@GetMapping("/publico/anexo/downloadFile/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		// Load file as Resource
		Resource resource = anexoService.loadFileAsResource(fileName);

		// Try to determine file's content type
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException ex) {
			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping("/anexo/{anexoId}")
	public AnexoResponse getAnexoById(@PathVariable Long anexoId) {
		return anexoService.getAnexoById(anexoId);
	}

	@PutMapping("/anexo")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<?> updateAnexo(@Valid @RequestBody AnexoRequest anexoRequest) {

		anexoService.updateAnexo(anexoRequest);

		return Utils.created(true, "Observação do Documento atualizado com sucesso.");
	}

}
