package com.esgi.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@RestController
@RequestMapping("/points")
@CrossOrigin
@Transactional
public class PointController {

	private PointService pointService;

	@Autowired
	public PointController(PointService pointService) {
		this.pointService = pointService;
	}

	@GetMapping
	public List<PointDto> gePointOfCercle(@RequestParam("cercle") Long id) {
		return pointService.getPointInCercle(id);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public PointDto insertPointIntoCercle(@Valid @RequestBody PointEntity pointEntity,
										  BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new PointValidationException();
		}

		PointDto pointDto = pointService.insertPoint(pointEntity);

		return pointDto;
	}
}
