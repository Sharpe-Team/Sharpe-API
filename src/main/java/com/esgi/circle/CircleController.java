package com.esgi.circle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Created by thomasfouan on 29/03/2017.
 */
@RestController
@RequestMapping("/circles")
@CrossOrigin
public class CircleController {

	private CircleService circleService;

	@Autowired
	public CircleController(CircleService circleService) {
		this.circleService = circleService;
	}

	@RequestMapping
	public List<CircleDto> getAllCircles() {
		return circleService.getAllCircles();
	}

	@RequestMapping("/{circle_id}")
	public CircleDto getCircle(@PathVariable("circle_id") Long id) throws CircleNotFoundException {
		return circleService.getCircle(id);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public CircleDto insertCircle(@RequestBody @Valid CircleDto circleDto,
									   BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			throw new CircleValidationException();
		}

		return circleService.insertCircle(CircleAdapter.convertToEntity(circleDto));

		/*
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{circle_id}")
				.buildAndExpand(circleEntity.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
		*/
	}

	@RequestMapping(path = "/private")
	public CircleDto getPrivateCircle(@RequestParam("idUser1") Long id1, @RequestParam("idUser2") Long id2) throws CircleNotFoundException {
		return circleService.getPrivateCircle(id1, id2);
	}

	@RequestMapping(path = "/publics")
	public List<CircleDto> getAllPublicCircle() {
		return circleService.getAllPublicCircles();
	}
}
