package com.org.web.embl_ebi.person.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote DuplicateRecordException
 * @since 2020
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class DuplicateRecordException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Desired constructor.
	 *
	 * @param errorMessage The error message describing the exception.
	 */
	public DuplicateRecordException(String errorMessage) {
		super(errorMessage);
	}
}