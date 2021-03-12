package com.rhlinkcon.payload.contracheque;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContrachequeRecalcularDto {

	@NotEmpty
	@NotNull
	private List<Long> contrachequeIds;

}
