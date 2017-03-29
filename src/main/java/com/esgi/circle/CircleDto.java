package com.esgi.circle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by thomasfouan on 29/03/2017.
 *
 * Class of Circle Dto. It contains certain fields of Circle.
 * This is the object we send et receive from outside.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CircleDto {

	/**
	 * The unique identifier of the Circle.
	 */
	private Long id;

	/**
	 * The name of the Circle.
	 */
	@NotNull
	@NotEmpty
	private String name;

	/**
	 * The URL of the main picture of the Circle.
	 */
	private String pictureUrl;

	/**
	 * The URL of the banner picture of the Circle.
	 */
	private String bannerPictureUrl;
}
