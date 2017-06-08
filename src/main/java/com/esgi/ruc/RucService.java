package com.esgi.ruc;

import com.esgi.role.RoleNotFoundException;
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

	@Transactional
	RucDto insertLink(RucDto rucDto);

	@Transactional
	void deleteLink(Long idUser, Long idCircle);

	@Transactional
	RucDto updateRole(Long idUser, Long idCircle, Long newIdRole) throws RucNotFoundException;

	@Transactional
	RucDto updateRole(Long idUser, Long idCircle, String newRoleName) throws RucNotFoundException, RoleNotFoundException;
}
