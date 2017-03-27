package com.esgi.point;

import lombok.Builder;
import lombok.Data;

/**
 * Created by rfruitet on 08/03/2017.
 */

@Builder
@Data
public class PointDto {

    private Long id;
    private Long cercle;
    private String username;
    private String point;
}
