package com.esgi.circle;

import com.esgi.line.LineEntity;
import com.esgi.line.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Created by thomasfouan on 29/03/2017.
 */
@RestController
@RequestMapping("/circles")
@CrossOrigin
public class CircleController {

	private CircleService circleService;

	private LineService lineService;

	@Autowired
	public CircleController(CircleService circleService, LineService lineService) {
		this.circleService = circleService;
		this.lineService = lineService;
	}

	@RequestMapping
	public List<CircleDto> getAllCircles() {
		return circleService.getAllCircles()
				.stream()
				.map(CircleAdapter::convertToDto)
				.collect(Collectors.toList());
	}

	@RequestMapping("/{circle_id}")
	public CircleDto getCircle(@PathVariable("circle_id") Long id) throws CircleNotFoundException {
		return CircleAdapter.convertToDto(circleService.getCircle(id));
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public CircleDto insertCircle(@RequestBody @Valid CircleDto circleDto,
									   BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			throw new CircleValidationException();
		}

		CircleEntity circleEntity = circleService.insertCircle(CircleAdapter.convertToEntity(circleDto));
		LineEntity lineEntity = LineEntity.builder()
				.idCircle(circleEntity.getId())
				.name(circleEntity.getName())
				.announcement("")
				.build();

		lineEntity = lineService.insertLine(lineEntity);

		return CircleAdapter.convertToDto(circleEntity);
		/*
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{circle_id}")
				.buildAndExpand(circleEntity.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
		*/
	}
}
