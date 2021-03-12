package com.rhlinkcon.audit;

import static javax.transaction.Transactional.TxType.MANDATORY;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.hibernate.collection.internal.PersistentBag;
import org.hibernate.collection.internal.PersistentList;
import org.hibernate.collection.internal.PersistentSet;
import org.jrimum.utilix.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.rhlinkcon.model.AcaoAuditoriaEnum;
import com.rhlinkcon.model.Auditoria;
import com.rhlinkcon.service.AuditoriaService;

@Component
public class AuditListener {

	protected Logger log = LoggerFactory.getLogger(AuditListener.class);

	@PostPersist
	public void postPersist(Object object) {
		insertAuditoria(object, AcaoAuditoriaEnum.INSERT);
	}

	@PostUpdate
	public void postUpdate(Object object) {
		insertAuditoria(object, AcaoAuditoriaEnum.UPDATE);
	}

	@PostRemove
	public void postRemove(Object object) {
		insertAuditoria(object, AcaoAuditoriaEnum.DELETE);
	}

	@Transactional(MANDATORY)
	private void insertAuditoria(Object object, AcaoAuditoriaEnum acao) {
		if (object instanceof PersistentId) {
			Auditoria auditoria = extractAuditoria((PersistentId) object, acao, object.getClass().getName());

			AuditoriaService auditoriaService = BeanUtil.getBean(AuditoriaService.class);
			auditoriaService.insert(auditoria);
		}
	}

	/***
	 * Método que monta um objeto auditoria para ser cadastrado no banco de dados.
	 * 
	 * @param object
	 * @param acao
	 * @param entidade
	 * @return
	 */
	private Auditoria extractAuditoria(PersistentId object, AcaoAuditoriaEnum acao, String entidade) {

		Gson gson = new Gson();
		Auditoria auditoria = new Auditoria();
		auditoria.setAcao(acao);
		auditoria.setDescricao(gson.toJson(extractAuditoria(object)));
		auditoria.setEntidade(entidade);
		auditoria.setIdObjectAuditado(object.getId());
		return auditoria;

	}

	/***
	 * Método que cria o conteúdo do texto.
	 * 
	 * @param persistence
	 * @return
	 */
	private AuditoriaDescricaoDto extractAuditoria(PersistentId auditable) {

		AuditoriaDescricaoDto result = new AuditoriaDescricaoDto();

		Iterator<Field> itCampos = Arrays.asList(auditable.getClass().getDeclaredFields()).iterator();
		while (itCampos.hasNext()) {
			Field campo = itCampos.next();

			if (!campo.isAnnotationPresent(ExcludeFromAudit.class) && !(Modifier.isStatic(campo.getModifiers()))
					&& !campo.isAnnotationPresent(Transient.class)) {
				campo.setAccessible(true);

				AuditoriaDescricaoItemDto item = new AuditoriaDescricaoItemDto();
				item.setCampo(campo.getName());

				try {
					Object object = campo.get(auditable);
					if (null != object) {

						if (object instanceof PersistentList) {
							// Mapeia objetos do tipo List de entidade.
							PersistentList persList = (PersistentList) object;
							persList.forceInitialization();
							if (persList.wasInitialized()) {

								List<String> objectIdList = new ArrayList<String>();
								for (Object subObject : persList) {
									objectIdList.add(extractAuditoriaObjectId(subObject));
								}
								item.setValor(objectIdList);
							}

						} else if (object instanceof PersistentSet) {
							// Mapeia objetos do tipo Set de entidade.
							PersistentSet persSet = (PersistentSet) object;
							persSet.forceInitialization();
							if (persSet.wasInitialized()) {

								List<String> objectIdList = new ArrayList<String>();
								for (Object subObject : persSet) {
									objectIdList.add(extractAuditoriaObjectId(subObject));
								}
								item.setValor(objectIdList);

							}

						} else if (object instanceof PersistentBag) {
							// Mapeia objetos do tipo Bag de entidade.
							PersistentBag persBag = (PersistentBag) object;
							persBag.forceInitialization();
							if (persBag.wasInitialized()) {

								List<String> objectIdList = new ArrayList<String>();
								for (Object subObject : persBag) {
									objectIdList.add(extractAuditoriaObjectId(subObject));
								}
								item.setValor(objectIdList);

							}

						}

						// Mapeia objetos do tipo entidade.
						for (Annotation annotation : object.getClass().getDeclaredAnnotations()) {
							if (annotation instanceof Entity) {
								item.setValor(extractAuditoriaObjectId(object));
								break;
							}
						}

						if (Objects.isNull(item.getValor())) {
							// Mapeia objetos dos demais tipos, primitivos ou não.
							item.setValor(object.toString());
						}

						// Item é adicionado na lista
						result.getItemList().add(item);

					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					log.error(e.getMessage(), e);
				}

			}

		}
		return result;

	}

	private String extractAuditoriaObjectId(Object object) {
		try {
			List<Field> fields = Arrays.asList(object.getClass().getDeclaredFields());
			Field choosenField = null;
			for (Field field : fields) {
				if (field.isAnnotationPresent(Id.class)) {
					choosenField = field;
					break;
				}
			}
			choosenField.setAccessible(true);
			if (null != choosenField) {
				Object objectId = choosenField.get(object);
				return objectId.toString();
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error(e.getMessage(), e);
		}
		return null;

	}
}
