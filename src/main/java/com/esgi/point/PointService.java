package com.esgi.point;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by rfruitet on 08/03/2017.
 */
public interface PointService {

    @Transactional(readOnly = true)
    List<PointDto> getAllMessages();

    @Transactional(readOnly = true)
    List<PointDto> getPointForCercle(Long idTopic) throws PointException;

    @Transactional(readOnly = true)
    PointDto insertMessage(PointDto PointDto);

    @Transactional(readOnly = true)
    void deleteMessage(Long idPoint);
}
