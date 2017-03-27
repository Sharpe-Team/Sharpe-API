package com.esgi.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rfruitet on 08/03/2017.
 */

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {

    private Long id;
    private Long cercle;
    private String username;
    private String point;
}
