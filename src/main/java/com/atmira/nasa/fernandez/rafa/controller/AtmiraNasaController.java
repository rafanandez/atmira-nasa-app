package com.atmira.nasa.fernandez.rafa.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atmira.nasa.fernandez.rafa.exception.AtmiraNasaException;
import com.atmira.nasa.fernandez.rafa.model.Asteroid;
import com.atmira.nasa.fernandez.rafa.service.AtmiraNasaService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(path = "/api")
public class AtmiraNasaController {

	@Autowired
	private AtmiraNasaService atmiraNasaService;

	@GetMapping("/asteroids")
	@ApiOperation("Obtiene los asteroides que pueden impactar con el planeta indicado por parámetro")
	public List<Asteroid> getAsteroids(@RequestParam(required = true) String planet) throws AtmiraNasaException {

		verifyPlanet(planet);

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime withinAWeek = today.plusDays(7);

		return atmiraNasaService.getAsteroidList(planet, today, withinAWeek);

	}

	private void verifyPlanet(String planet) throws AtmiraNasaException {
		if (planet == null || "".equals(planet)) {
			throw new AtmiraNasaException("Se debe indicar el nombre del planeta");
		}
	}

}
