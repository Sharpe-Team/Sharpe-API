package com.esgi.circle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by thomasfouan on 29/03/2017.
 *
 * {@inheritDoc}
 */
@Service
public class CircleServiceImpl implements CircleService {

	/**
	 * Class to access to Circle data in the Repository.
	 */
	private final CircleRepository circleRepository;

	/**
	 * Constructor of the Service.
	 * @param circleRepository an instance of CircleRepository provided by SpringBoot
	 */
	@Autowired
	public CircleServiceImpl(CircleRepository circleRepository) {
		this.circleRepository = circleRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CircleEntity> getAllCircles() {
		return circleRepository.findAll();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CircleEntity getCircle(Long id) throws CircleNotFoundException {

		CircleEntity circle = circleRepository.findOne(id);

		if(circle == null) {
			throw new CircleNotFoundException();
		}

		return circle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CircleEntity insertCircle(CircleEntity circleEntity) {
		return circleRepository.save(circleEntity);
	}
}
