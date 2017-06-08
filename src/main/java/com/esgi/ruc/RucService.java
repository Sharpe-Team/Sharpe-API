package com.esgi.ruc;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomasfouan on 08/06/2017.
 */
public interface RucService {

	@Transactional(readOnly = true)
	List<RucDto> getAllLinks();

	@Transactional(readOnly = true)
	List<RucDto> getLinksByRole(Long id);

	@Transactional(readOnly = true)
	List<RucDto> getLinksByUser(Long id);

	@Transactional(readOnly = true)
	List<RucDto> getLinksByCircle(Long id);

	@Transactional(readOnly = true)
	List<RucDto> getLinksByRoleAndUser(Long idRole, Long idUser);

	@Transactional(readOnly = true)
	List<RucDto> getLinksByRoleAndCircle(Long idRole, Long idCircle);

	@Transactional(readOnly = true)
	RucDto getLinkByUserAndCircle(Long idUser, Long idCircle) throws RucNotFoundException;
}
