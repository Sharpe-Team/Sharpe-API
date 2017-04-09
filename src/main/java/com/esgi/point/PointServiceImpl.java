package com.esgi.point;

import com.esgi.line.LineNotFoundException;
import com.esgi.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rfruitet on 08/03/2017.
 */

@Service
public class PointServiceImpl implements PointService {

    private PointRepository pointRepository;

    @Autowired
    public PointServiceImpl(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PointDto> getPointsInLine(Long idLine) throws LineNotFoundException {
        List<PointDto> pointDtoList = pointRepository.findByIdLine(idLine)
                .stream()
                .map(PointAdapter::convertToDto)
                .collect(Collectors.toList());

        return pointDtoList;
    }

    @Override
    @Transactional
    public PointEntity insertPoint(PointDto pointDto) {

        PointEntity pointEntity = PointAdapter.convertToEntity(pointDto);

        pointEntity = pointRepository.save(pointEntity);

        return pointEntity;
    }

    @Override
    @Transactional
    public void deletePoint(Long idMessage) {
        pointRepository.delete(idMessage);
    }
}
