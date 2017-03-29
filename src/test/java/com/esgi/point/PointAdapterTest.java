package com.esgi.point;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

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
        Date date = new Date();

        PointDto pointDto = PointDto.builder()
                .id(1L)
                .idLine(2L)
                .idUser(8L)
                .content("message")
                .created(date)
                .updated(date)
                .build();

        PointEntity pointEntity = pointAdapter.convertToEntity(pointDto);

        assertThat(pointEntity.getId()).isEqualTo(pointDto.getId());
        assertThat(pointEntity.getIdLine()).isEqualTo(pointDto.getIdLine());
        assertThat(pointEntity.getIdUser()).isEqualTo(pointDto.getIdUser());
        assertThat(pointEntity.getContent()).isEqualTo(pointDto.getContent());
        assertThat(pointEntity.getCreated()).isEqualTo(pointDto.getCreated());
        assertThat(pointEntity.getUpdated()).isEqualTo(pointDto.getUpdated());
    }

    @Test
    public void should_return_an_point_dto() {
        Date date = new Date();

        PointEntity pointEntity = PointEntity.builder()
                .id(1L)
                .idLine(2L)
                .idUser(8L)
                .content("message")
                .created(date)
                .updated(date)
                .build();

        PointDto pointDto = pointAdapter.convertToDto(pointEntity);

        assertThat(pointDto.getId()).isEqualTo(pointEntity.getId());
        assertThat(pointDto.getIdLine()).isEqualTo(pointEntity.getIdLine());
        assertThat(pointDto.getIdUser()).isEqualTo(pointEntity.getIdUser());
        assertThat(pointDto.getContent()).isEqualTo(pointEntity.getContent());
        assertThat(pointDto.getCreated()).isEqualTo(pointEntity.getCreated());
        assertThat(pointDto.getUpdated()).isEqualTo(pointEntity.getUpdated());
    }
}
