package com.rhlinkcon.payload.experienciaProfissional;

import javax.validation.constraints.Null;
import java.util.List;

public class ExperienciaProfissionalRequest {
    private Long id;

    private Long funcaoId;

    private Long funcionarioId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuncaoId() {
        return funcaoId;
    }

    public void setFuncaoId(Long funcaoId) {
        this.funcaoId = funcaoId;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }
}
