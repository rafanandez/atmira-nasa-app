package com.atmira.nasa.fernandez.rafa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AtmiraNasaException extends Exception {

	private static final long serialVersionUID = 1L;

	private static final String DEFAUL_MESSAGE = "Operación no válida";

	public AtmiraNasaException() {
		super(DEFAUL_MESSAGE);
	}

	public AtmiraNasaException(String cause) {
		super(cause);
	}

}
