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
                .cercle(1L)
                .point("message1")
                .build();

        point2 = PointEntity.builder().id(2L)
                .cercle(1L)
                .point("message2")
                .build();

        point3 = PointEntity.builder().id(3L)
                .cercle(1L)
                .point("message3")
                .build();

        point4 = PointEntity.builder().id(4L)
                .cercle(1L)
                .point("message11")
                .build();

        point5 = PointEntity.builder().id(5L)
                .cercle(1L)
                .point("message5")
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

        when(pointRepository.findByCercle(1L)).thenReturn(pointEntities);
    }

    @Test
    public void should_return_all_point_of_topic_one() {
        List<PointDto> allPoints = pointService.getPointInCercle(1L);

        assertThat(allPoints).hasSize(5);
    }

    @Test
    public void should_insert_one_point() {
        PointEntity pointEntity = PointEntity.builder()
                .id(6L)
                .cercle(1L)
                .username("myUsername")
                .point("my message")
                .build();

        PointDto pointDto2 = pointService.insertPoint(pointEntity);

        assertThat(pointDto2.getId()).isEqualTo(6L);
        assertThat(pointDto2.getCercle()).isEqualTo(1);
        assertThat(pointDto2.getUsername()).isEqualTo("myUsername");
        assertThat(pointDto2.getPoint()).isEqualTo("my message");
    }
}