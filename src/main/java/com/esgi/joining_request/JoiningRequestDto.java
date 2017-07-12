package com.esgi.joining_request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by thomasfouan on 10/07/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoiningRequestDto {

	private Long id;

	@NotNull
	private Long idUser;

	@NotNull
	private Long idCircle;
}
