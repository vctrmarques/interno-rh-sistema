package com.rhlinkcon.payload.medico;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.rhlinkcon.model.EspecialidadeMedica;
import com.rhlinkcon.model.Medico;
import com.rhlinkcon.payload.DadoCadastralResponse;
import com.rhlinkcon.payload.empresaFilial.EmpresaFilialResponse;
import com.rhlinkcon.util.Projecao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedicoDto extends DadoCadastralResponse {

	private Long id;
	
	@NotNull
	private Integer matricula;
	
	@NotNull
	private boolean status;
	
	@NotNull
	private String nome;
	
	@NotNull
	private Integer crm;
	
	private String Especialidade;
	
	@NotNull
	private EmpresaFilialResponse empresa;
	
	private List<EspecialidadeMedica> especialidadeMedica;

	public MedicoDto() {}

	public MedicoDto(Medico obj) {
		mountDto(obj, Projecao.COMPLETA);
	}
	
	public MedicoDto(Medico obj, Projecao projecao) {
		mountDto(obj, projecao);
	}
	
	public void mountDto(Medico obj, Projecao projecao) {
		
		if (projecao.equals(Projecao.BASICA) || projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
			
			this.id = obj.getId();
			
			this.nome = obj.getNome();
			
			if (projecao.equals(Projecao.MEDIA) || projecao.equals(Projecao.COMPLETA)) {
				
				if(Objects.nonNull(obj.getEspecialidadesMedicas())){
					this.especialidadeMedica = obj.getEspecialidadesMedicas();
				}
				
				if (projecao.equals(Projecao.COMPLETA)) {
					
					this.matricula = obj.getMatricula();
					
					this.status = obj.isStatus();

					this.crm = obj.getCrm();
					
					if(Objects.nonNull(obj.getEmpresa())){
						this.empresa = new EmpresaFilialResponse(obj.getEmpresa());
					}
					
					this.setAlteradoEm(obj.getUpdatedAt());
					this.setCriadoEm(obj.getCreatedAt());
				}
			}
		}
	}
}
