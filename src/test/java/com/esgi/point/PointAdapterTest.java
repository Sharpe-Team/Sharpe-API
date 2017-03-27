package com.esgi.point;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rfruitet on 08/03/2017.
 */
@RunWith(SpringRunner.class)
public class PointAdapterTest {

    @InjectMocks
    PointAdapter pointAdapter;

    @Test
    public void should_return_an_point_entity() {
        PointDto pointDto = PointDto.builder()
                .id(1L)
                .cercle(2L)
                .username("first")
                .point("message")
                .build();

        PointEntity pointEntity = pointAdapter.convertToEntity(pointDto);

        assertThat(pointEntity.getUsername()).isEqualTo("first");
        assertThat(pointEntity.getId()).isEqualTo(1L);
        assertThat(pointEntity.getCercle()).isEqualTo(2L);
        assertThat(pointEntity.getPoint()).isEqualTo("message");
    }

    @Test
    public void should_return_an_point_dto() {
        PointEntity pointEntity = PointEntity.builder()
                .id(1L)
                .cercle(2L)
                .username("first")
                .point("message")
                .build();

        PointDto pointDto = pointAdapter.convertToDto(pointEntity);

        assertThat(pointDto.getId()).isEqualTo(1L);
        assertThat(pointDto.getCercle()).isEqualTo(2L);
        assertThat(pointDto.getUsername()).isEqualTo("first");
        assertThat(pointDto.getPoint()).isEqualTo("message");
    }
}
