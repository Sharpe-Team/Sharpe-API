package com.esgi.line;

import com.esgi.circle.CircleNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomasfouan on 29/03/2017.
 *
 * Service class of a Line.
 */
public interface LineService {

	/**
	 * Get all Lines saved in the Repository.
	 * @return List of Line
	 */
	@Transactional(readOnly = true)
	List<LineEntity> getAllLinesInCircle(Long idCircle) throws CircleNotFoundException;

	/**
	 * Get a Line by its id.
	 * @param id the id of the Line
	 * @return the Line represented by the id
	 */
	@Transactional(readOnly = true)
	LineEntity getLine(Long id) throws LineNotFoundException;

	/**
	 * Insert a new Line in the Repository.
	 * @param lineEntity the Line to insert
	 * @return the LineEntity with the id provided by the Repository
	 */
	@Transactional
	LineEntity insertLine(LineEntity lineEntity);
}
