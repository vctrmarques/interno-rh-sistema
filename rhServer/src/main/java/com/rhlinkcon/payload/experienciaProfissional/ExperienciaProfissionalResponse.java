package com.rhlinkcon.payload.experienciaProfissional;

import com.rhlinkcon.model.ExperienciaProfissional;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.payload.funcao.FuncaoResponse;
import com.rhlinkcon.payload.funcionario.FuncionarioResponse;

import java.time.Instant;
import java.util.Date;

public class ExperienciaProfissionalResponse extends DadoCadastralResponse {
    private Long id;

    private FuncaoResponse funcao;

    private FuncionarioResponse funcionario;

    public ExperienciaProfissionalResponse(ExperienciaProfissional experienciaProfissional) {
        this.id = experienciaProfissional.getId();
        this.funcao = new FuncaoResponse(experienciaProfissional.getFuncao());
        this.funcionario = new FuncionarioResponse(experienciaProfissional.getFuncionario().getId(), experienciaProfissional.getFuncionario().getMatricula(), new EmpresaFilialResponse(experienciaProfissional.getFuncionario().getEmpresa()), null, experienciaProfissional.getFuncionario().getNome());
    }

    public void setDadosAutditoria(Instant alteradoEm, String alteradoPor, Instant criadoEm, String criadoPor){
        this.setAlteradoEm(alteradoEm);
        this.setAlteradoPor(alteradoPor);
        this.setCriadoEm(criadoEm);
        this.setCriadoPor(criadoPor);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FuncionarioResponse getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioResponse funcionario) {
        this.funcionario = funcionario;
    }

    public FuncaoResponse getFuncao() {
        return funcao;
    }

    public void setFuncao(FuncaoResponse funcao) {
        this.funcao = funcao;
    }
}
