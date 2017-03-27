package com.esgi.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rfruitet on 08/03/2017.
 */

@Service
public class PointServiceImpl implements PointService {

    private PointRepository pointRepository;

    private HttpSession userSession;

    @Autowired
    public PointServiceImpl(PointRepository pointRepository, HttpSession userSession) {
        this.pointRepository = pointRepository;
        this.userSession = userSession;
    }

    @Override
    public List<PointDto> getAllMessages() {
        return pointRepository.findAll()
                .stream()
                .map(PointAdapter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PointDto> getPointForCercle(Long cercle) throws PointException {
        return pointRepository.findByCercle(cercle)
                .stream()
                .map(PointAdapter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PointDto insertMessage(PointDto messageDto) {
        messageDto.setUsername((String) userSession.getAttribute("username"));
        PointEntity messageEntity = pointRepository.save(PointAdapter.convertToEntity(messageDto));
        return messageDto;
    }

    @Override
    public void deleteMessage(Long idMessage) {
        pointRepository.delete(idMessage);
    }
}
