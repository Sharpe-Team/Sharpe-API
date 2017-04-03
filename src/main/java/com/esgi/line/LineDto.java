package com.esgi.line;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by thomasfouan on 29/03/2017.
 *
 * Class of LineDto. It contains certain fields of Line.
 * This is the object we send to et receive from outside.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineDto {

	/**
	 * The unique identifier of the Line.
	 */
	private Long id;

	/**
	 * The unique identifier of the Circle which the Line belongs to.
	 */
	@NotNull
	private Long idCircle;

	/**
	 * The name of the Line.
	 */
	@NotEmpty
	@NotNull
	private String name;

	/**
	 * The announcement message of the Line.
	 */
	private String announcement;
}
