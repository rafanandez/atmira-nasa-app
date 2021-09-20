package com.atmira.nasa.fernandez.rafa.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.atmira.nasa.fernandez.rafa.model.Asteroid;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class AtmiraNasaServiceImpl implements AtmiraNasaService {

	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private static Logger log = LoggerFactory.getLogger(AtmiraNasaServiceImpl.class);

	@Value("${atmira.nasa.url}")
	private String url;

	@Value("${atmira.nasa.apiKey}")
	private String apiKey;

	@Override
	public List<Asteroid> getAsteroidList(String name, LocalDateTime from, LocalDateTime to) {

		List<Asteroid> asteroidList = new ArrayList<Asteroid>();
		List<Asteroid> allAsteroidList = new ArrayList<Asteroid>();

		List<Asteroid> asteroids;

		String jsonResult = new RestTemplate().getForObject(getUrlNasaService(from, to), String.class);

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsonNode = objectMapper.readTree(jsonResult);

			Asteroid asteroid = null;

			JsonNode fechas = jsonNode.at("/near_earth_objects");

			JsonNode nodeFecha = null;
			JsonNode asteroidJson = null;
			ArrayNode asteroidAproachArray = null;
			JsonNode asteroidAproachData = null;

			Iterator<Map.Entry<String, JsonNode>> iteratorFecha = fechas.fields();
			Iterator<JsonNode> iteratorAsteroid = null;
			Iterator<JsonNode> iteratorAproachData = null;

			while (iteratorFecha.hasNext()) {

				Entry<String, JsonNode> nodeFechaEntry = iteratorFecha.next();
				nodeFecha = nodeFechaEntry.getValue();

				iteratorAsteroid = nodeFecha.elements();

				while (iteratorAsteroid.hasNext()) {

					asteroidJson = iteratorAsteroid.next();

					asteroidAproachArray = (ArrayNode) asteroidJson.get("close_approach_data");

					iteratorAproachData = asteroidAproachArray.elements();

					while (iteratorAproachData.hasNext()) {

						asteroidAproachData = iteratorAproachData.next();

						asteroid = getAsteroidInfo(asteroidAproachData.get("orbiting_body").asText(), asteroidJson,
								nodeFechaEntry, asteroidAproachData);

						if (name.equalsIgnoreCase(asteroidAproachData.get("orbiting_body").asText())
								&& asteroidJson.get("is_potentially_hazardous_asteroid").asBoolean()) {
							asteroidList.add(asteroid);
						}
					}
					allAsteroidList.add(asteroid);
				}
			}

		} catch (JsonProcessingException e) {
			log.error("Se ha producido un error tratando el json devuelto por el servicio", e);
		}

		if (asteroidList.size() > 2) {
			Collections.sort(asteroidList);
			asteroids = asteroidList.subList(0, 3);
		} else {
			asteroids = allAsteroidList;
		}

		return asteroids;

	}

	private Asteroid getAsteroidInfo(String name, JsonNode asteroidJson, Entry<String, JsonNode> nodeFechaEntry,
			JsonNode asteroidAproachData) {
		Asteroid asteroid = new Asteroid();
		asteroid.setNombre(asteroidJson.get("name").textValue());
		asteroid.setPlaneta(name);
		asteroid.setFecha(nodeFechaEntry.getKey());
		asteroid.setVelocidad(asteroidAproachData.get("relative_velocity").get("kilometers_per_hour").textValue());

		Double minDiameter = asteroidJson.get("estimated_diameter").get("kilometers").get("estimated_diameter_min")
				.asDouble();
		Double maxDiameter = asteroidJson.get("estimated_diameter").get("kilometers").get("estimated_diameter_max")
				.asDouble();

		Double diameter = (minDiameter + maxDiameter) / 2;

		asteroid.setDiametro(String.valueOf(diameter));

		return asteroid;
	}

	private String getUrlNasaService(LocalDateTime from, LocalDateTime to) {

		String fromString = DATE_TIME_FORMAT.format(from);
		String toString = DATE_TIME_FORMAT.format(to);

		StringBuilder stringBuilder = new StringBuilder(url);
		stringBuilder.append("?start_date=" + fromString);
		stringBuilder.append("&end_date=" + toString);
		stringBuilder.append("&api_key=" + apiKey);

		return stringBuilder.toString();

	}

}
