package com.esgi.cube;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by thomasfouan on 16/06/2017.
 */
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cube")
public class CubeEntity {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "id_line")
	@NotNull
	private Long idLine;

	@Column(name = "id_user")
	@NotNull
	private Long idUser;

	@Column
	@NotNull
	@NotEmpty
	@Length(min = 1, max = 250)
	private String url;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	private Date created;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column()
	private Date updated;
}
