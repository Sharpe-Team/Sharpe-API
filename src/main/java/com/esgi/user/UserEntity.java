package com.esgi.user;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
public class UserEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column
	@NotNull
	@NotEmpty
	private String username;

	@Column
	@NotNull
	@NotEmpty
	@Length(min = 8)
	private String password;
}
