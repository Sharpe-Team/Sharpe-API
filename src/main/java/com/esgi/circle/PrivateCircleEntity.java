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

	@Column
	@NotEmpty
	@NotNull
	private Long idCircle;

	@Column
	@NotEmpty
	@NotNull
	private Long idUser1;

	@Column
	@NotEmpty
	@NotNull
	private Long idUser2;
}
