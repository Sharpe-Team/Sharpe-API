package com.esgi.point;

import com.esgi.line.LineNotFoundException;
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
    private PointAdapter pointAdapter;

    @Autowired
    public PointServiceImpl(PointRepository pointRepository,PointAdapter pointAdapter) {
        this.pointRepository = pointRepository;
        this.pointAdapter = pointAdapter;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PointDto> getPointInLine(Long idLine) throws LineNotFoundException {
        List<PointDto> pointDtoList = pointRepository.findByIdLine(idLine)
                .stream()
                .map(PointAdapter::convertToDto)
                .collect(Collectors.toList());

        if (pointDtoList.size() == 0)
            throw new LineNotFoundException();

        return pointDtoList;
    }

    @Override
    @Transactional
    public PointDto insertPoint(PointDto pointDto) {

        PointEntity pointEntity = PointAdapter.convertToEntity(pointDto);

        pointEntity = pointRepository.save(pointEntity);

        return pointAdapter.convertToDto(pointEntity);
    }

    @Override
    @Transactional
    public void deletePoint(Long idMessage) {
        pointRepository.delete(idMessage);
    }
}
