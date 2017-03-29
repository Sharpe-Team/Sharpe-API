package com.esgi.circle;

/**
 * Created by thomasfouan on 29/03/2017.
 *
 * Class Adapter to convert Circle classes.
 */
public class CircleAdapter {

	/**
	 * Convert a CircleEntity to a CircleDto.
	 * @param circleEntity the circleEntity to convert
	 * @return CircleDto the result of the conversion
	 */
	public static CircleDto convertToDto(CircleEntity circleEntity) {
		return CircleDto.builder()
				.id(circleEntity.getId())
				.name(circleEntity.getName())
				.pictureUrl(circleEntity.getPictureUrl())
				.bannerPictureUrl(circleEntity.getBannerPictureUrl())
				.build();
	}

	/**
	 * Convert a CircleDto to a CircleEntity.
	 * @param circleDto the circleDto to convert
	 * @return CircleEntity the result of the conversion
	 */
	public static CircleEntity convertToEntity(CircleDto circleDto) {
		return CircleEntity.builder()
				.id(circleDto.getId())
				.name(circleDto.getName())
				.pictureUrl(circleDto.getPictureUrl())
				.bannerPictureUrl(circleDto.getBannerPictureUrl())
				.build();
	}
}
