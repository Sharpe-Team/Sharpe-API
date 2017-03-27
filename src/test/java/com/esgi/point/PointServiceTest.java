package com.esgi.point;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.fail;
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
        pointEntities.add(point5);

        when(pointRepository.findByIdLine(1L)).thenReturn(pointEntities);
    }

    @Test
    public void should_return_all_point_of_cercle_one() {
        List<PointDto> allPoints = null;

        Long idCercle = 1L;

        try {
            allPoints = pointService.getPointInCercle(idCercle);
        } catch (CercleNotFoundException e) {
            fail("Test failed : an unexpected exception has been thrown when trying to access cercle with id = " + idCercle);
        }

        assertThat(allPoints).hasSize(5);
    }

    @Test
    public void should_throw_CircleNotFoundException_with_unknown_id() {

        Long idCercle = 62L;

        try {
            pointService.getPointInCercle(idCercle);

            fail("Test failed : an exception should have been thrown when trying to acceszs cercle with id = " + idCercle);
        } catch (CercleNotFoundException e) {
        }
    }

    @Test
    public void should_insert_one_point() {
        PointEntity pointEntity = PointEntity.builder()
                .id(6L)
                .idLine(1L)
                .idUser(8L)
                .content("my message")
                .build();

        PointDto pointDto2 = pointService.insertPoint(pointEntity);

        assertThat(pointDto2.getId()).isEqualTo(6L);
        assertThat(pointDto2.getIdLine()).isEqualTo(1);
        assertThat(pointDto2.getIdUser()).isEqualTo(8L);
        assertThat(pointDto2.getContent()).isEqualTo("my message");
    }
}