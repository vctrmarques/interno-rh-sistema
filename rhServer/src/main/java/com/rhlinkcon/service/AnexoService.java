package com.rhlinkcon.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.jrimum.texgit.FlatFile;
import org.jrimum.texgit.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.Anexo;
import com.rhlinkcon.model.AnexoPastaEnum;
import com.rhlinkcon.payload.anexo.AnexoRequest;
import com.rhlinkcon.payload.anexo.AnexoResponse;
import com.rhlinkcon.repository.AnexoRepository;
import com.rhlinkcon.util.ModelMapper;

@Service
public class AnexoService {

	@Autowired
	private AnexoRepository anexoRepository;

	private Path localizacaoArquivo;

	public void loadLocalizacaoArquivoPath(AnexoPastaEnum pastaAnexo) {
		this.localizacaoArquivo = Paths.get(getAnexoPath(pastaAnexo.getPasta())).toAbsolutePath().normalize();

		try {
			Files.createDirectories(this.localizacaoArquivo);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not create the directory where the uploaded files will be stored.",
					ex);
		}
	}

	public Anexo createAnexo(byte[] content, String originalFilename, String contentType, String pasta,
			String description) {

		InputStream targetStream = new ByteArrayInputStream(content);

		// Cria o anexo com os dados iniciais.
		Anexo anexo = new Anexo();
		anexo.setSize(Long.valueOf(content.length));
		anexo.setFileType(contentType);

		anexoRepository.save(anexo);

		AnexoPastaEnum pastaAnexo = AnexoPastaEnum.getEnumByString(pasta);

		// Armazena o arquivo
		String fileName = armazenarArquivo(targetStream, originalFilename, pastaAnexo, anexo);

		// Gera a URI para download/visualização
		String fileDownloadUri = "/api/publico/anexo/downloadFile/" + fileName.trim();

		// Atualiza o anexo com o filename e a URI
		anexo.setFileDownloadUri(fileDownloadUri);
		anexo.setFileName(fileName);

		if (Objects.nonNull(description) && description.length() > 0)
			anexo.setDescription(description);
		else
			anexo.setDescription(fileName.substring(0, fileName.lastIndexOf('.')));

		anexoRepository.save(anexo);

		return anexo;

	}

	private String armazenarArquivo(InputStream targetStream, String originalFilename, AnexoPastaEnum pastaAnexo,
			Anexo anexo) {

		// Normalize file name
		String fileName = StringUtils.cleanPath(originalFilename);

		// O padrão e a concatenação do id do anexo criado + "_" + pasta do anexo + "_"
		// + filename.
		fileName = anexo.getId() + "_" + pastaAnexo.getPasta() + "_" + fileName;

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			loadLocalizacaoArquivoPath(pastaAnexo);

			Path targetLocation = this.localizacaoArquivo.resolve(fileName);
			Files.copy(targetStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public AnexoResponse createAnexo(MultipartFile file, String pasta, String description) {

		// Cria o anexo com os dados iniciais.
		Anexo anexo = new Anexo();
		anexo.setSize(file.getSize());
		anexo.setFileType(file.getContentType());
		anexoRepository.save(anexo);

		AnexoPastaEnum pastaAnexo = AnexoPastaEnum.getEnumByString(pasta);

		// Armazena o arquivo
		String fileName = armazenarArquivo(file, pastaAnexo, anexo);

		// Gera a URI para download/visualização
		String fileDownloadUri = "/api/publico/anexo/downloadFile/" + fileName.trim();

		// Atualiza o anexo com o filename e a URI
		anexo.setFileDownloadUri(fileDownloadUri);
		anexo.setFileName(fileName);

		if (Objects.nonNull(description) && description.length() > 0) {
			anexo.setDescription(description);
		} else {
			anexo.setDescription(fileName.substring(0, fileName.lastIndexOf('.')));
		}

		anexoRepository.save(anexo);

		// Gera o response payload para retorno
		AnexoResponse anexoResponse = ModelMapper.mapAnexoToAnexoResponse(anexo);

		return anexoResponse;

	}

	private String armazenarArquivo(MultipartFile arquivo, AnexoPastaEnum pastaAnexo, Anexo anexo) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(arquivo.getOriginalFilename());

		// O padrão e a concatenação do id do anexo criado + "_" + pasta do anexo + "_"
		// + filename.
		fileName = anexo.getId() + "_" + pastaAnexo.getPasta() + "_" + fileName;

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			loadLocalizacaoArquivoPath(pastaAnexo);

			Path targetLocation = this.localizacaoArquivo.resolve(fileName);
			Files.copy(arquivo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public static String getAnexoPath(String pasta) {
		String mensagem = ResourceBundle.getBundle("application").getString("file.uploaddir");
		return mensagem + File.separator + pasta;

	}

	public Resource loadFileAsResource(String fileName) {
		try {

			// Split necessário para capturar a pasta onde o mesmo se encontra
			String fileNameSplit[] = fileName.split("_");
			String pastaAnexo = fileNameSplit[1];

			// substituindo o load da pasta para algo mais simples - @rodrigo.leite
			AnexoPastaEnum pasta = AnexoPastaEnum.getEnumByString(pastaAnexo);
			if (Objects.nonNull(pasta)) {
				loadLocalizacaoArquivoPath(pasta);
			}

			Path filePath = this.localizacaoArquivo.resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new ResourceNotFoundException("Anexo ", "fileName", fileName);

			}
		} catch (Exception ex) {
			throw new ResourceNotFoundException("Anexo ", "fileName", fileName);
		}
	}

	public void updateAnexo(AnexoRequest anexoRequest) {
		Anexo anexo = anexoRepository.findById(anexoRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", anexoRequest.getId()));

		anexo.setDescription(anexoRequest.getDescription());

		anexoRepository.save(anexo);

	}

	public void deleteAnexo(Long id) {
		Anexo anexo = anexoRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", id));

		try {

			// Split necessário para capturar a pasta onde o mesmo se encontra
			String fileNameSplit[] = anexo.getFileName().split("_");
			String pastaAnexo = fileNameSplit[1];

			// substituindo o load da pasta para algo mais simples - @rodrigo.leite
			AnexoPastaEnum pasta = AnexoPastaEnum.getEnumByString(pastaAnexo);
			if (Objects.nonNull(pasta)) {
				loadLocalizacaoArquivoPath(pasta);
			}

			Path filePath = this.localizacaoArquivo.resolve(anexo.getFileName()).normalize();

			Files.deleteIfExists(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}

		anexoRepository.delete(anexo);

	}

	public AnexoResponse getAnexoById(Long anexoId) {
		Anexo anexo = anexoRepository.findById(anexoId)
				.orElseThrow(() -> new ResourceNotFoundException("Anexo", "id", anexoId));

		AnexoResponse anexoResponse = new AnexoResponse(anexo);

		return anexoResponse;
	}

	public Anexo createAnexo(Anexo obj, AnexoPastaEnum pasta) throws IOException {

		loadLocalizacaoArquivoPath(pasta);
		Path filePath = this.localizacaoArquivo.resolve(obj.getFileName()).normalize();
		File file = new File(filePath.toString());

		// Cria o anexo com os dados iniciais.
		Anexo anexo = new Anexo();
		anexo.setSize(obj.getSize());
		anexo.setFileType(obj.getFileType());
		anexoRepository.save(anexo);

		// Armazena o arquivo
		String fileName = armazenarArquivo(file, pasta, anexo);

		// Gera a URI para download/visualização
		String fileDownloadUri = "/api/publico/anexo/downloadFile/" + fileName.trim();

		// Atualiza o anexo com o filename e a URI
		anexo.setFileDownloadUri(fileDownloadUri);
		anexo.setFileName(fileName);
		anexo.setDescription(fileName.substring(0, fileName.lastIndexOf('.')));

		anexoRepository.save(anexo);

		return anexo;

	}

	private String armazenarArquivo(File arquivo, AnexoPastaEnum pastaAnexo, Anexo anexo) {
		// Normalize file name
		String fileName = StringUtils.cleanPath(arquivo.getName());
		String name = fileName.substring(fileName.lastIndexOf("_") + 1, fileName.length());

		// O padrão e a concatenação do id do anexo criado + "_" + pasta do anexo + "_"
		// + filename.
		fileName = anexo.getId() + "_" + pastaAnexo.getPasta() + "_" + name;

		try {
			// Check if the file's name contains invalid characters
			if (fileName.contains("..")) {
				throw new BadRequestException("Sorry! Filename contains invalid path sequence " + fileName);
			}

			// Copy file to the target location (Replacing existing file with the same name)
			loadLocalizacaoArquivoPath(pastaAnexo);

			Path targetLocation = this.localizacaoArquivo.resolve(fileName);
			Files.copy(new FileInputStream(arquivo), targetLocation, StandardCopyOption.REPLACE_EXISTING);

			return fileName;
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new BadRequestException("Could not store file " + fileName + ". Please try again!", ex);
		}
	}

	public Anexo createAnexo(FlatFile<Record> ff, AnexoPastaEnum pasta, Integer numero) throws IOException {

		loadLocalizacaoArquivoPath(pasta);
		Path filePath = this.localizacaoArquivo.resolve(numero.toString() + ".txt");
		File file = new File(filePath.toString());
		try {
			FileUtils.writeLines(file, ff.write(), "\r\n");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		// Cria o anexo com os dados iniciais.
		Anexo anexo = new Anexo();
		anexo.setSize(file.length());
		anexo.setFileType("text/plain");
		anexoRepository.save(anexo);

		// Armazena o arquivo
		String fileName = armazenarArquivo(file, pasta, anexo);

		// Gera a URI para download/visualização
		String fileDownloadUri = "/api/publico/anexo/downloadFile/" + fileName.trim();

		// Atualiza o anexo com o filename e a URI
		anexo.setFileDownloadUri(fileDownloadUri);
		anexo.setFileName(fileName);
		anexo.setDescription(fileName.substring(0, fileName.lastIndexOf('.')));

		anexoRepository.save(anexo);

		return anexo;
	}

}
