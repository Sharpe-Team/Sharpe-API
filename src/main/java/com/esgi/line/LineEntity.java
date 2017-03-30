package com.esgi.line;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by thomasfouan on 29/03/2017.
 *
 * Class of Line. A Line represents a messages thread between members of the same Circle.
 */
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "line")
public class LineEntity {

	/**
	 * The unique identifier of the Line.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The unique identifier of the Circle which the Line belongs to.
	 */
	@Column(name = "id_circle")
	@NotNull
	private Long idCircle;

	/**
	 * The name of the Line.
	 */
	@Column(name = "line_name")
	@NotEmpty
	@NotNull
	private String name;

	/**
	 * The announcement message of the Line.
	 */
	@Column
	private String announcement;
}
