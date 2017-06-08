package com.esgi.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by thomasfouan on 08/06/2017.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

	private Long id;

	@NotNull
	@NotEmpty
	private String name;
}
