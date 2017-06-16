package com.esgi.cube;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by thomasfouan on 16/06/2017.
 */
public interface CubeService {

	@Transactional(readOnly = true)
	List<CubeDto> getCubesInLine(Long idLine);

	@Transactional
	CubeDto insertCube(CubeDto cubeDto);

	@Transactional
	void deleteCube(Long idCube);
}
