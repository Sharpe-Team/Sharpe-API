package com.esgi.circle;

import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by thomasfouan on 29/03/2017.
 *
 * Class of Circle. A Circle represents the room where people can discuss, share files, etc.
 */
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "circle")
public class CircleEntity {

	/**
	 * The unique identifier of the Circle.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The name of the Circle.
	 */
	@Column(name = "circle_name")
	@NotEmpty
	@NotNull
	private String name;

	/**
	 * The URL of the main picture of the Circle.
	 */
	@Column(name = "picture_url")
	private String pictureUrl;

	/**
	 * The URL of the banner picture of the Circle.
	 */
	@Column(name = "banner_picture_url")
	private String bannerPictureUrl;
}
