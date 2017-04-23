package com.esgi.circle;

import com.esgi.line.LineAdapter;
import com.esgi.line.LineDto;
import com.esgi.line.LineEntity;
import com.esgi.line.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
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

		circleDto = circleService.insertCircle(CircleAdapter.convertToEntity(circleDto));
		LineDto lineDto = LineDto.builder()
				.idCircle(circleDto.getId())
				.name(circleDto.getName())
				.announcement("")
				.build();

		// Add a default line for the circle
		lineDto = lineService.insertLine(LineAdapter.convertToEntity(lineDto));
		List<LineDto> listLines = new ArrayList<>();
		listLines.add(lineDto);
		circleDto.setLines(listLines);

		return circleDto;
		/*
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{circle_id}")
				.buildAndExpand(circleEntity.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
		*/
	}
}
