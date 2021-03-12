package com.rhlinkcon.repository.diaUtil;

import java.util.List;

import com.rhlinkcon.payload.diaUtil.DtoDiaUtilConsulta;

public interface DiaUtilRepositoryQuery {

	List<DtoDiaUtilConsulta> diasUteisDoAno(String ano);
	
}
