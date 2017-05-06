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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CircleDto getPrivateCircle(Long idUser1, Long idUser2) throws CircleNotFoundException {
		List<PrivateCircleEntity> list = privateCircleRepository.findByIdUser1AndAndIdUser2(idUser1, idUser2);

		if(list.isEmpty()) {
			// Switch the order of the IDs
			list = privateCircleRepository.findByIdUser1AndAndIdUser2(idUser2, idUser1);
		}

		if(list.isEmpty()) {
			// Add a new private circle for the 2 users
			return addPrivateCircle(idUser1, idUser2);
		} else {
			return getCircle(list.get(0).getIdCircle());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<CircleDto> getAllPublicCircles() {
		return circleRepository.findAllByType((short) 1)
				.stream()
				.map(this::getCompleteCircleDto)
				.collect(Collectors.toList());
	}

	/**
	 * Complete the CircleEntity with its lines.
	 * @param circleEntity the CircleEntity to complete
	 * @return a CircleDto from the CircleEntity data
	 */
	private CircleDto getCompleteCircleDto(CircleEntity circleEntity) {

		CircleDto circleDto = CircleAdapter.convertToDto(circleEntity);
		List<LineDto> lines = lineRepository.findByIdCircle(circleDto.getId())
				.stream()
				.map(LineAdapter::convertToDto)
				.collect(Collectors.toList());

		circleDto.setLines(lines);

		return circleDto;
	}

	/**
	 * Add a new PrivateCircle between 2 users.
	 * @param idUser1 the first user
	 * @param idUser2 the second user
	 * @return a new Circle for their PrivateCircle
	 */
	private CircleDto addPrivateCircle(Long idUser1, Long idUser2) {
		CircleEntity circleEntity = CircleEntity.builder()
				.name("Cercle privé")
				.type((short) 2)
				.build();

		CircleDto circleDto = insertCircle(circleEntity);

		PrivateCircleEntity privateCircleEntity = PrivateCircleEntity.builder()
				.idCircle(circleDto.getId())
				.idUser1(idUser1)
				.idUser2(idUser2)
				.build();

		privateCircleRepository.save(privateCircleEntity);

		return circleDto;
	}
}
