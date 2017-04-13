package com.esgi.circle;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomasfouan on 29/03/2017.
 *
 * Service Class of a Circle.
 */
public interface CircleService {

	/**
	 * Get all Circle saved in the Repository.
	 * @return List of Circles
	 */
	@Transactional(readOnly = true)
	List<CircleDto> getAllCircles();

	/**
	 * Get a Circle by its id.
	 * @param id the id of the Circle
	 * @return the Circle represented by the id
	 */
	@Transactional(readOnly = true)
	CircleDto getCircle(Long id) throws CircleNotFoundException;

	/**
	 * Insert a new Circle in the Repository.
	 * @param circleEntity the Circle to insert
	 * @return the CircleEntity with the id provided by the Repository
	 */
	@Transactional
	CircleEntity insertCircle(CircleEntity circleEntity);
}
