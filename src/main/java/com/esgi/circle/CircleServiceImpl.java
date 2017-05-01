package com.esgi.circle;

import com.esgi.line.LineAdapter;
import com.esgi.line.LineDto;
import com.esgi.line.LineEntity;
import com.esgi.line.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
	 * Class to access to PrivateCircle data in the Repository.
	 */
	private final PrivateCircleRepository privateCircleRepository;

	/**
	 * Constructor of the Service.
	 * @param circleRepository an instance of CircleRepository provided by SpringBoot
	 */
	@Autowired
	public CircleServiceImpl(CircleRepository circleRepository, LineRepository lineRepository, PrivateCircleRepository privateCircleRepository) {
		this.circleRepository = circleRepository;
		this.lineRepository = lineRepository;
		this.privateCircleRepository = privateCircleRepository;
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

	@Override
	public CircleDto getPrivateCircle(Long userId1, Long userId2) throws CircleNotFoundException {
		PrivateCircleEntity privateCircleEntity;
		List<PrivateCircleEntity> list = privateCircleRepository.findByIdUser1AndAndIdUser2(userId1, userId2);

		if(list.isEmpty()) {
			list = privateCircleRepository.findByIdUser1AndAndIdUser2(userId2, userId1);
		}

		if(list.isEmpty()) {
			// Add a new private circle for the 2 users
			CircleEntity circleEntity = CircleEntity.builder()
					.name("Cercle privé")
					.build();

			CircleDto circleDto = insertCircle(circleEntity);

			privateCircleEntity = PrivateCircleEntity.builder()
					.idCircle(circleDto.getId())
					.idUser1(userId1)
					.idUser2(userId2)
					.build();

			privateCircleRepository.save(privateCircleEntity);

			return circleDto;
		} else {
			privateCircleEntity = list.get(0);
			return getCircle(privateCircleEntity.getIdCircle());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CircleDto insertCircle(CircleEntity circleEntity) {
		CircleDto circleDto = CircleAdapter.convertToDto(circleRepository.save(circleEntity));

		LineEntity lineEntity = LineEntity.builder()
				.idCircle(circleDto.getId())
				.name(circleDto.getName())
				.announcement("")
				.build();

		// Add a default line for the circle
		LineDto lineDto = LineAdapter.convertToDto(lineRepository.save(lineEntity));
		List<LineDto> listLines = new ArrayList<>();
		listLines.add(lineDto);
		circleDto.setLines(listLines);

		return circleDto;
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
