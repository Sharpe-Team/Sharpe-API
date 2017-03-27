package com.esgi.point;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by rfruitet on 07/03/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "point")
public class PointEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "idline")
    private Long idLine;

    @Column(name = "iduser")
    @NotNull
    private Long idUser;

    @Column
    @NotNull
    @NotEmpty
    @Length(min = 1)
    private String content;
}
