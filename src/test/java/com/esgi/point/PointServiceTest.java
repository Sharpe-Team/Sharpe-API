package com.esgi.point;

import com.esgi.line.LineNotFoundException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * Created by rfruitet on 08/03/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class PointServiceTest {

    @InjectMocks
    PointServiceImpl pointService;

    @Mock
    PointRepository pointRepository;

    private PointEntity point1;
    private PointEntity point2;
    private PointEntity point3;
    private PointEntity point4;
    private PointEntity point5;

    public void init() {
        point1 = PointEntity.builder().id(1L)
                .idLine(1L)
                .content("message1")
                .build();

        point2 = PointEntity.builder().id(2L)
                .idLine(1L)
                .content("message2")
                .build();

        point3 = PointEntity.builder().id(3L)
                .idLine(1L)
                .content("message3")
                .build();

        point4 = PointEntity.builder().id(4L)
                .idLine(1L)
                .content("message11")
                .build();

        point5 = PointEntity.builder().id(5L)
                .idLine(1L)
                .content("message5")
                .build();
    }

    @Before
    public void configure() {
        init();

        ArrayList<PointEntity> pointEntities = new ArrayList<>();

        pointEntities.add(point1);
        pointEntities.add(point2);
        pointEntities.add(point3);
        pointEntities.add(point4);

        when(pointRepository.findByIdLine(1L)).thenReturn(pointEntities);
        when(pointRepository.save(any(PointEntity.class))).thenReturn(point5);
    }

    @Test
    public void should_return_all_point_of_line_one() {
        List<PointDto> allPoints = null;

        Long idLine = 1L;

        try {
            allPoints = pointService.getPointsInLine(idLine);
        } catch (LineNotFoundException e) {
            fail("Test failed : an unexpected exception has been thrown when trying to access cercle with id = " + idLine);
        }

        assertThat(allPoints).hasSize(4);
    }

    @Ignore
    @Test
    public void should_throw_LineNotFoundException_with_unknown_idline() {

        Long idLineUnknown = 62L;

        try {
            pointService.getPointsInLine(idLineUnknown);

            fail("Test failed : an exception should have been thrown when trying to acceszs cercle with id = " + idLineUnknown);
        } catch (LineNotFoundException e) {
        }
    }

    @Test
    public void should_insert_one_point() {
        PointDto pointDtoWithoutId = PointDto.builder()
                .idLine(1L)
                .idUser(8L)
                .content("my message")
                .build();

        PointEntity pointEntity = pointService.insertPoint(pointDtoWithoutId);

        assertThat(pointEntity.getId()).isEqualTo(5L);
    }
}