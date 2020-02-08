package com.org.web.embl_ebi.person.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote RecordNotFoundException
 * @since 2020
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Desired constructor.
	 *
	 * @param errorMessage The error message describing the exception.
	 */
	public RecordNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
