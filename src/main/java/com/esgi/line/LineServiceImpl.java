package com.esgi.line;

import com.esgi.circle.CircleNotFoundException;
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
public class LineServiceImpl implements LineService {

	private LineRepository lineRepository;

	/**
	 * Constructor of the Service.
	 * @param lineRepository an instance of LineRepository provided by SpringBoot
	 */
	@Autowired
	public LineServiceImpl(LineRepository lineRepository) {
		this.lineRepository = lineRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<LineDto> getAllLinesInCircle(Long idCircle) throws CircleNotFoundException {

		List<LineDto> lines = lineRepository.findByIdCircle(idCircle)
				.stream()
				.map(LineAdapter::convertToDto)
				.collect(Collectors.toList());

		if(lines.isEmpty()) {
			throw new CircleNotFoundException();
		}

		return lines;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LineDto getLine(Long id) throws LineNotFoundException {

		LineEntity line = lineRepository.findOne(id);

		if(line == null) {
			throw new LineNotFoundException();
		}

		return LineAdapter.convertToDto(line);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LineDto insertLine(LineDto lineDto) {
		LineEntity lineEntity = LineAdapter.convertToEntity(lineDto);

		return LineAdapter.convertToDto(lineRepository.save(lineEntity));
	}
}
