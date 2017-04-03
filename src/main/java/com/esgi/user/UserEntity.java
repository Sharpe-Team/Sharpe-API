package com.esgi.user;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
	private String firstname;

	@Column
	@NotNull
	@NotEmpty
	private String lastname;

	@Column
	@NotNull
	@NotEmpty
	@Email
	private String email;

	@Column
	@NotNull
	@NotEmpty
	@Length(min = 8)
	private String password;

	@Column(name = "user_profile_picture")
	private String profilePicture;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date created;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date updated;
}
