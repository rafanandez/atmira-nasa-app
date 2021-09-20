package com.atmira.nasa.fernandez.rafa.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.atmira.nasa.fernandez.rafa.exception.AtmiraNasaException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class AtmiraNasaUnitTest {

	private final static String URL_ASTEROIDS_EARTH = "/api/asteroids?planet=earth";
	private final static String URL_BAD_REQUEST = "/api/asteroids?planet=";

	@Autowired
	private MockMvc mockMvc;

	@Test
	void shouldReturnThreeAsteroidsForEarth() throws Exception {

		ResultActions resultActions = mockMvc.perform(get(URL_ASTEROIDS_EARTH));

		MvcResult result = resultActions.andDo(print()).andExpect(status().isOk()).andReturn();

		String respuesta = result.getResponse().getContentAsString();

		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode = objectMapper.readTree(respuesta);

		assertTrue(jsonNode.isArray());
		assertEquals(jsonNode.size(), 3);

	}

	@Test
	void shouldReturnError() throws Exception {
		mockMvc.perform(get(URL_BAD_REQUEST, "Bad Request"))
				.andExpect(result -> assertTrue(result.getResolvedException() instanceof AtmiraNasaException));
	}

}
