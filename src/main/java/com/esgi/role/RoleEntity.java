package com.esgi.role;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

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
@Table(name = "role")
public class RoleEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	@NotNull
	@NotEmpty
	private String name;
}
