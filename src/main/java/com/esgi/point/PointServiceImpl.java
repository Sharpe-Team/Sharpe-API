package com.esgi.point;

import com.esgi.line.LineNotFoundException;
import com.esgi.user.UserDto;
import com.esgi.user.UserNotFoundException;
import com.esgi.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by rfruitet on 08/03/2017.
 */
@Service
public class PointServiceImpl implements PointService {

    private PointRepository pointRepository;

    private UserService userService;

    @Autowired
    public PointServiceImpl(PointRepository pointRepository, UserService userService) {
        this.pointRepository = pointRepository;
        this.userService = userService;
    }

    @Override
    public List<PointDto> getPointsInLine(Long idLine) throws LineNotFoundException {
        return pointRepository.findByIdLine(idLine)
                .stream()
                .map(this::getCompletePointDto)
                .collect(Collectors.toList());
    }

    @Override
    public PointDto insertPoint(PointDto pointDto) {
        PointEntity pointEntity = PointAdapter.convertToEntity(pointDto);
		return this.getCompletePointDto(pointRepository.save(pointEntity));
    }

    @Override
    public void deletePoint(Long idPoint) {
        pointRepository.delete(idPoint);
    }

    private PointDto getCompletePointDto(PointEntity pointEntity) {
    	PointDto pointDto = PointAdapter.convertToDto(pointEntity);

    	// Get info of the sender of this point
        UserDto userDto = null;
        try {
            userDto = userService.getUser(pointDto.getIdUser());
            userDto.setPassword("");
        } catch (UserNotFoundException e) {
            userDto = UserDto.builder()
                    .firstname("Deleted user")
                    .lastname("")
                    .email("")
                    .build();
        } finally {
            pointDto.setUser(userDto);
        }
        return pointDto;
    }
}
