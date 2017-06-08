package com.esgi.ruc;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "link_role_user_circle")
public class RucEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "id_role")
	@NotNull
	private Long idRole;

	@Column(name = "id_user")
	@NotNull
	private Long idUser;

	@Column(name = "id_circle")
	@NotNull
	private Long idCircle;
}
