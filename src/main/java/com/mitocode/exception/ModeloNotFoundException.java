package com.mitocode.exception;

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class ModeloNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ModeloNotFoundException(String mensaje) {
		super(mensaje);
	}
}
