package com.esgi.message;

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
@Table(name = "message")
public class MessageEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long topic;

    @Column
    @NotNull
    @NotEmpty
    private String username;

    @Column
    @NotNull
    @NotEmpty
    @Length(min = 1)
    private String message;
}
