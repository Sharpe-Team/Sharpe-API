package com.esgi.point;

import com.esgi.line.LineNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rfruitet on 08/03/2017.
 */
public interface PointService {

	@Transactional(readOnly = true)
	List<PointDto> getPointsInLine(Long idLine) throws LineNotFoundException;

	@Transactional
    PointDto insertPoint(PointEntity pointEntity);

    @Transactional(readOnly = true)
    void deletePoint(Long idPoint);
}
