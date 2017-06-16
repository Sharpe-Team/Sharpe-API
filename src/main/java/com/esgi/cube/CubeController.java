package com.esgi.cube;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

/**
 * Created by thomasfouan on 16/06/2017.
 */
@RestController
@RequestMapping("/cubes")
@CrossOrigin
public class CubeController {

	private CubeService cubeService;

	@Autowired
	public CubeController(CubeService cubeService) {
		this.cubeService = cubeService;
	}

	@GetMapping
	public List<CubeDto> getAllCubesInLine(@RequestParam("line_id") Long idLine) {
		return cubeService.getCubesInLine(idLine);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public CubeDto insertCube(@RequestBody @Valid CubeDto cubeDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			throw new CubeValidationException();
		}

		return cubeService.insertCube(cubeDto);
	}

	@DeleteMapping("/{cube_id}")
	@ResponseStatus(NO_CONTENT)
	public void deleteCube(@PathVariable("cube_id") Long idCube) {
		cubeService.deleteCube(idCube);
	}
}
