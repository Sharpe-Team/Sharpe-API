package com.esgi.point;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rfruitet on 07/03/2017.
 */
@Repository
public interface PointRepository extends CrudRepository<PointEntity, Long> {

	List<PointEntity> findAll();

	// TODO: 29/03/2017 Retoruner les messages par ordre décroissant et ajouter une limite
	List<PointEntity> findByIdLine(Long idLine);

	List<PointEntity> findTop2ByIdLineOrderByCreatedDesc(Long idLine);

	void delete(Long idPoint);
}