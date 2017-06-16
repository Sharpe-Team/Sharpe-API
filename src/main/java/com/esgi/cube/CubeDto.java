package com.esgi.cube;

import com.esgi.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by thomasfouan on 16/06/2017.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CubeDto {

	private Long id;

	@NotNull
	private Long idLine;

	@NotNull
	private Long idUser;

	@NotNull
	@NotEmpty
	private String url;

	private Date created;
	private Date updated;

	private UserDto user;
}
