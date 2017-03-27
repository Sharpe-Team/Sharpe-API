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

	List<PointEntity> findByCercle(Long cercle);

	void delete(Long idPoint);
}