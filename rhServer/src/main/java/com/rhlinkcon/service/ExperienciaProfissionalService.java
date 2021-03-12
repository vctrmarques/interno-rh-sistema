package com.rhlinkcon.service;

import com.rhlinkcon.exception.BadRequestException;
import com.rhlinkcon.exception.ResourceNotFoundException;
import com.rhlinkcon.model.*;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.experienciaProfissional.ExperienciaProfissionalRequest;
import com.rhlinkcon.payload.experienciaProfissional.ExperienciaProfissionalResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;
import com.rhlinkcon.payload.generico.PagedResponse;
import com.rhlinkcon.repository.ExperienciaProfissionalRepository;
import com.rhlinkcon.repository.FuncaoRepository;
import com.rhlinkcon.repository.funcionario.FuncionarioRepository;
import com.rhlinkcon.repository.usuario.UsuarioRepository;
import com.rhlinkcon.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExperienciaProfissionalService {

    @Autowired
    private ExperienciaProfissionalRepository experienciaProfissionalRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncaoRepository funcaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public PagedResponse<ExperienciaProfissionalResponse> getAllExperienciasProfissionais(int page, int size, String nomeBusca, String order) {
        validatePageNumberAndSize(page, size);

        String orderBy = order == null || order.isEmpty() ? "createdAt" : order;

        Sort.Direction direction = Sort.Direction.ASC;
        if (orderBy.startsWith("-")) {
            orderBy = orderBy.replace("-", "");
            direction = Sort.Direction.DESC;
        }

        Pageable pageable = PageRequest.of(page, size, direction, order);

        Page<ExperienciaProfissional> experienciasProfissionais = experienciaProfissionalRepository.findByFuncionario_NomeIgnoreCaseContaining(nomeBusca, pageable);

        if (experienciasProfissionais.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), experienciasProfissionais.getNumber(), experienciasProfissionais.getSize(),
                    experienciasProfissionais.getTotalElements(), experienciasProfissionais.getTotalPages(), experienciasProfissionais.isLast());
        }

        List<ExperienciaProfissionalResponse> experienciasProfissionaisResponse = experienciasProfissionais.map(faixaSalarial -> {
            return new ExperienciaProfissionalResponse(faixaSalarial);
        }).getContent();

        return new PagedResponse<>(experienciasProfissionaisResponse, experienciasProfissionais.getNumber(), experienciasProfissionais.getSize(),
                experienciasProfissionais.getTotalElements(), experienciasProfissionais.getTotalPages(), experienciasProfissionais.isLast());

    }

    public ExperienciaProfissionalResponse getExperienciaProfissionalById(Long experienciaId) {
        ExperienciaProfissional experienciaProfissional = experienciaProfissionalRepository.findById(experienciaId)
                .orElseThrow(() -> new ResourceNotFoundException("ExperienciaProfissional", "id", experienciaId));

        Usuario userCreated = usuarioRepository.findById(experienciaProfissional.getCreatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", experienciaProfissional.getCreatedBy()));

        Usuario userUpdated = usuarioRepository.findById(experienciaProfissional.getUpdatedBy())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario", "id", experienciaProfissional.getUpdatedBy()));

        FuncionarioResponse funcionarioResponse = new FuncionarioResponse(experienciaProfissional.getFuncionario().getId(), experienciaProfissional.getFuncionario().getMatricula(), new EmpresaFilialResponse(experienciaProfissional.getFuncionario().getEmpresa()), null, experienciaProfissional.getFuncionario().getNome());

        ExperienciaProfissionalResponse experienciaProfissionalResponse = new ExperienciaProfissionalResponse(experienciaProfissional);

        experienciaProfissionalResponse.setFuncionario(funcionarioResponse);
        experienciaProfissionalResponse.setDadosAutditoria(experienciaProfissional.getUpdatedAt(), userUpdated.getNome(), experienciaProfissional.getCreatedAt(), userCreated.getNome());

        return experienciaProfissionalResponse;
    }

    public void createExperienciaProfissional(ExperienciaProfissionalRequest experienciaProfissionalRequest) {

        Funcionario funcionario = funcionarioRepository.findById(experienciaProfissionalRequest.getFuncionarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id",
                        experienciaProfissionalRequest.getFuncionarioId()));

        Funcao funcao = funcaoRepository.findById(experienciaProfissionalRequest.getFuncaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Funcao", "id",
                        experienciaProfissionalRequest.getFuncaoId()));

        ExperienciaProfissional experienciaProfissional = new ExperienciaProfissional(funcao, funcionario);

        experienciaProfissionalRepository.save(experienciaProfissional);

    }

    public void updateExperienciaProfissional(ExperienciaProfissionalRequest experienciaProfissionalRequest) {
        ExperienciaProfissional experienciaProfissional = experienciaProfissionalRepository.findById(experienciaProfissionalRequest.getId())
				.orElseThrow(() -> new ResourceNotFoundException("ExperienciaProfissional", "id", experienciaProfissionalRequest.getId()));

        Funcionario funcionario = funcionarioRepository.findById(experienciaProfissionalRequest.getFuncionarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Funcionario", "id",
                        experienciaProfissionalRequest.getFuncionarioId()));

        Funcao funcao = funcaoRepository.findById(experienciaProfissionalRequest.getFuncaoId())
                .orElseThrow(() -> new ResourceNotFoundException("Funcao", "id",
                        experienciaProfissionalRequest.getFuncaoId()));

        experienciaProfissional.setFuncao(funcao);
        experienciaProfissional.setFuncionario(funcionario);

        experienciaProfissionalRepository.save(experienciaProfissional);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }

    public void deleteExperienciaProfissional(Long id) {
        experienciaProfissionalRepository.deleteById(id);
    }
}
