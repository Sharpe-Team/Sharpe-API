package com.esgi.point;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rfruitet on 08/03/2017.
 */
public interface PointService {

    @Transactional(readOnly = true)
    List<PointDto> getPointInCercle(Long idCercle) throws CercleNotFoundException;

    @Transactional
    PointDto insertPoint(PointEntity pointEntity);

    @Transactional(readOnly = true)
    void deletePoint(Long idPoint);
}
