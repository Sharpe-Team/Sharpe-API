package com.esgi.point;

import com.esgi.line.LineNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/points")
@CrossOrigin
public class PointController {

	private PointService pointService;

	@Autowired
	public PointController(PointService pointService) {
		this.pointService = pointService;
	}

	@GetMapping
	public List<PointDto> getAllPointsInLine(@RequestParam("idLine") Long id) throws LineNotFoundException {
		return pointService.getPointsInLine(id);
	}

	@PostMapping
	@ResponseStatus(CREATED)
	public PointDto insertPointIntoLine(@Valid @RequestBody PointDto pointDto,
										BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			throw new PointValidationException();
		}

		return pointService.insertPoint(pointDto);
	}
}
