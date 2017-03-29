package com.esgi.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by rfruitet on 08/03/2017.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {

    private Long id;
    private Long idLine;

    @NotNull
    private Long idUser;

    @NotNull
    @NotEmpty
    private String content;

    private Date created;
    private Date updated;
}
