package com.esgi.point;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rfruitet on 08/03/2017.
 */
public interface PointService {

    @Transactional(readOnly = true)
    List<PointDto> getPointInLine(Long idLine) throws LineNotFoundException;

    @Transactional
    PointDto insertPoint(PointDto pointDto);

    @Transactional(readOnly = true)
    void deletePoint(Long idPoint);
}
