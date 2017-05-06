package com.esgi.circle;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by thomasfouan on 01/05/2017.
 */
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "private_circle")
public class PrivateCircleEntity {

	/**
	 * The unique identifier of the PrivateCircle.
	 */
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "id_circle")
	@NotNull
	private Long idCircle;

	@Column(name = "id_user_1")
	@NotNull
	private Long idUser1;

	@Column(name = "id_user_2")
	@NotNull
	private Long idUser2;
}
