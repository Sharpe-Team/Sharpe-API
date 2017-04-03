package com.esgi.line;

import com.esgi.circle.CircleNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
	public List<LineEntity> getAllLinesInCircle(Long idCircle) throws CircleNotFoundException {

		List<LineEntity> lines = lineRepository.findByIdCircle(idCircle);

		if(lines.isEmpty()) {
			throw new CircleNotFoundException();
		}

		return lines;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LineEntity getLine(Long id) throws LineNotFoundException {

		LineEntity line = lineRepository.findOne(id);

		if(line == null) {
			throw new LineNotFoundException();
		}

		return line;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public LineEntity insertLine(LineEntity lineEntity) {
		return lineRepository.save(lineEntity);
	}
}
