package com.esgi.ruc;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RucDto {

	private Long id;

	@NotNull
	private Long idRole;

	@NotNull
	private Long idUser;

	@NotNull
	private Long idCircle;
}
