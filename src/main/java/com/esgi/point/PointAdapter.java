package com.esgi.point;

/**
 * Created by rfruitet on 08/03/2017.
 */

import org.springframework.stereotype.Component;

@Component
public class PointAdapter {

    public static PointDto convertToDto(PointEntity pointEntity) {
        return PointDto.builder()
                .id(pointEntity.getId())
                .idLine(pointEntity.getIdLine())
                .idUser(pointEntity.getIdUser())
                .content(pointEntity.getContent())
                .created(pointEntity.getCreated())
                .updated(pointEntity.getUpdated())
                .build();
    }

    public static PointEntity convertToEntity(PointDto pointDto) {
        return PointEntity.builder()
                .id(pointDto.getId())
                .idLine(pointDto.getIdLine())
                .idUser(pointDto.getIdUser())
                .content(pointDto.getContent())
                .created(pointDto.getCreated())
                .updated(pointDto.getUpdated())
                .build();
    }
}
