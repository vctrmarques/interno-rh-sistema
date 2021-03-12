package com.rhlinkcon.repository.regraAposentadoria;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rhlinkcon.model.ModalidadeAposentadoriaEnum;
import com.rhlinkcon.model.RegraAposentadoria;

@Repository
public interface RegraAposentadoriaRepository
		extends JpaRepository<RegraAposentadoria, Long>, RegraAposentadoriaRepositoryCustom {

	List<RegraAposentadoria> findByModalidadeAposentadoriaAndModalidadeAposentadoriaNomeNotNull(
			ModalidadeAposentadoriaEnum modalidadeAposentadoriaEnum);

	List<RegraAposentadoria> findByModalidadeAposentadoria(ModalidadeAposentadoriaEnum modalidadeAposentadoriaEnum);

	List<RegraAposentadoria> findByModalidadeAposentadoriaAndModalidadeAposentadoriaNome(
			ModalidadeAposentadoriaEnum modalidadeAposentadoriaEnum, String modalidadeAposentadoriaNome);

}
