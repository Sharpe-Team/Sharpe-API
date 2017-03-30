package com.esgi.line;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by thomasfouan on 29/03/2017.
 */
@Repository
public interface LineRepository extends JpaRepository<LineEntity, Long> {

	/**
	 * Find all the Lines of a Circle.
	 * @param idCircle the id of the Circle
	 * @return all the lines that belongs to the Circle
	 */
	List<LineEntity> findByIdCircle(Long idCircle);
}
