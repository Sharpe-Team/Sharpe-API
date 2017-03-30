package com.esgi.line;

import com.esgi.circle.CircleNotFoundException;
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
 *
 * Controller for Line Entity.
 */
@RestController
@RequestMapping("/lines")
@CrossOrigin
public class LineController {

	/**
	 * The Line service.
	 */
	private LineService lineService;

	/**
	 * Constructor of the Controller.
	 * @param lineService an instance of LineService provided by SpringBoot
	 */
	@Autowired
	public LineController(LineService lineService) {
		this.lineService = lineService;
	}

	/**
	 * Retrieve all the Lines of a Circle.
	 * @param idCircle the id of the Circle
	 * @return the Lines of the Circle
	 * @throws CircleNotFoundException exception
	 */
	@RequestMapping(params = {"idCircle"})
	public List<LineDto> getAllLinesInCircle(@RequestParam("idCircle") Long idCircle) throws CircleNotFoundException {
		return lineService.getAllLinesInCircle(idCircle)
				.stream()
				.map(LineAdapter::convertToDto)
				.collect(Collectors.toList());
	}

	/**
	 * Retrieve a Line by its id.
	 * @param id the id of the Line
	 * @return the Line
	 * @throws LineNotFoundException exception
	 */
	@RequestMapping("/{line_id}")
	public LineDto getLine(@PathVariable("line_id") Long id) throws LineNotFoundException {
		return LineAdapter.convertToDto(lineService.getLine(id));
	}

	/**
	 * Add a new Line in the database.
	 * @param lineDto the Line to add
	 * @param bindingResult the result of Line validation
	 * @return ResponseEntity that contains a link to the details of the new Line
	 */
	@PostMapping
	@ResponseStatus(CREATED)
	public ResponseEntity insertLine(@RequestBody @Valid LineDto lineDto,
							  BindingResult bindingResult) {

		if(bindingResult.hasErrors()) {
			throw new LineValidationException();
		}

		LineEntity lineEntity = lineService.insertLine(LineAdapter.convertToEntity(lineDto));

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{line_id}")
				.buildAndExpand(lineEntity.getId())
				.toUri();

		return ResponseEntity.created(uri).build();
	}
}
