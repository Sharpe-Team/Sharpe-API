package com.esgi.ruc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@Repository
public interface RucRepository extends JpaRepository<RucEntity, Long> {

	List<RucEntity> findByIdRole(Long id);

	List<RucEntity> findByIdUser(Long id);

	List<RucEntity> findByIdCircle(Long id);

	List<RucEntity> findByIdRoleAndIdUser(Long idRole, Long idUser);

	List<RucEntity> findByIdRoleAndIdCircle(Long idRole, Long idCircle);

	List<RucEntity> findByIdUserAndIdCircle(Long idUser, Long idCircle);

	@Transactional
	List<RucEntity> deleteAllByIdUserAndIdCircle(Long idUser, Long idCircle);
}
