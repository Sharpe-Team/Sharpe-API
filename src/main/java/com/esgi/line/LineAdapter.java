package com.esgi.line;

/**
 * Created by thomasfouan on 29/03/2017.
 *
 * Class Adapter to convert Line classes.
 */
public class LineAdapter {

	/**
	 * Convert a LineEntity to a LineDto.
	 * @param lineEntity the LineEntity to convert
	 * @return LineDto the result of the conversion
	 */
	public static LineDto convertToDto(LineEntity lineEntity) {
		return LineDto.builder()
				.id(lineEntity.getId())
				.idCircle(lineEntity.getIdCircle())
				.name(lineEntity.getName())
				.announcement(lineEntity.getAnnouncement())
				.build();
	}

	/**
	 * Convert a LineDto to a LineEntity.
	 * @param lineDto the LineDto to convert
	 * @return LineEntity the result of the conversion
	 */
	public static LineEntity convertToEntity(LineDto lineDto) {
		return LineEntity.builder()
				.id(lineDto.getId())
				.idCircle(lineDto.getIdCircle())
				.name(lineDto.getName())
				.announcement(lineDto.getAnnouncement())
				.build();
	}
}
