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
                .cercle(pointEntity.getCercle())
                .username(pointEntity.getUsername())
                .point(pointEntity.getPoint())
                .build();
    }

    public static PointEntity convertToEntity(PointDto pointDto) {
        return PointEntity.builder()
                .id(pointDto.getId())
                .cercle(pointDto.getCercle())
                .username(pointDto.getUsername())
                .point(pointDto.getPoint())
                .build();
    }
}
