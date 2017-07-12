package com.esgi.joining_request;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by thomasfouan on 10/07/2017.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "joining_request")
public class JoiningRequestEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "id_user")
	@NotNull
	private Long idUser;

	@Column(name = "id_circle")
	@NotNull
	private Long idCircle;
}
