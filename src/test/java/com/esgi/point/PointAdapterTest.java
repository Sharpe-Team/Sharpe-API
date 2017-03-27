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
                .idLine(2L)
                .idUser(8L)
                .content("message")
                .build();

        PointEntity pointEntity = pointAdapter.convertToEntity(pointDto);

        assertThat(pointEntity.getId()).isEqualTo(1L);
        assertThat(pointEntity.getIdLine()).isEqualTo(2L);
        assertThat(pointEntity.getIdUser()).isEqualTo(8L);
        assertThat(pointEntity.getContent()).isEqualTo("message");
    }

    @Test
    public void should_return_an_point_dto() {
        PointEntity pointEntity = PointEntity.builder()
                .id(1L)
                .idLine(2L)
                .idUser(8L)
                .content("message")
                .build();

        PointDto pointDto = pointAdapter.convertToDto(pointEntity);

        assertThat(pointDto.getId()).isEqualTo(1L);
        assertThat(pointDto.getIdLine()).isEqualTo(2L);
        assertThat(pointDto.getIdUser()).isEqualTo(8L);
        assertThat(pointDto.getContent()).isEqualTo("message");
    }
}
