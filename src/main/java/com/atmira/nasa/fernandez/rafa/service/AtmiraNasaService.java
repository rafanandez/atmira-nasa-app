package com.atmira.nasa.fernandez.rafa.service;

import java.time.LocalDateTime;
import java.util.List;

import com.atmira.nasa.fernandez.rafa.model.Asteroid;

public interface AtmiraNasaService {

	List<Asteroid> getAsteroidList(String name, LocalDateTime from, LocalDateTime to);

}
