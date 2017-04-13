package com.esgi.circle;

import com.esgi.line.LineAdapter;
import com.esgi.line.LineDto;
import com.esgi.line.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
	 * Class to access to Line data in the Repository.
	 */
	private final LineRepository lineRepository;

	/**
	 * Constructor of the Service.
	 * @param circleRepository an instance of CircleRepository provided by SpringBoot
	 */
	@Autowired
	public CircleServiceImpl(CircleRepository circleRepository, LineRepository lineRepository) {
		this.circleRepository = circleRepository;
		this.lineRepository = lineRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CircleDto> getAllCircles() {
		return circleRepository.findAll()
				.stream()
				.map(this::getCompleteCircleDto)
				.collect(Collectors.toList());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CircleDto getCircle(Long id) throws CircleNotFoundException {

		CircleEntity circle = circleRepository.findOne(id);

		if(circle == null) {
			throw new CircleNotFoundException();
		}

		return getCompleteCircleDto(circle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CircleEntity insertCircle(CircleEntity circleEntity) {
		return circleRepository.save(circleEntity);
	}

	private CircleDto getCompleteCircleDto(CircleEntity circleEntity) {

		CircleDto circleDto = CircleAdapter.convertToDto(circleEntity);
		List<LineDto> lines = lineRepository.findByIdCircle(circleDto.getId())
				.stream()
				.map(LineAdapter::convertToDto)
				.collect(Collectors.toList());

		circleDto.setLines(lines);

		return circleDto;
	}
}
