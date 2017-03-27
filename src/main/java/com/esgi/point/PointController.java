package com.esgi.point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by thomasfouan on 04/03/2017.
 */
@RestController
@RequestMapping("/point")
@CrossOrigin
public class PointController {

	private PointService pointService;

	@Autowired
	public PointController(PointService pointService) {
		this.pointService = pointService;
	}

	@GetMapping
	public List<PointDto> getMessageOfTopic(@RequestParam("cercle") Long id) {
		return pointService.getPointForCercle(id);
	}
}
