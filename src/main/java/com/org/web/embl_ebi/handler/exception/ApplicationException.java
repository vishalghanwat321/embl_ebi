package com.org.web.embl_ebi.handler.exception;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote ApplicationException custom exception.
 * @since 2020
 */
public class ApplicationException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private LocalDateTime dateTime;
	private String message;
	private List<String> errors;

	public ApplicationException(LocalDateTime dateTime, String message, List<String> errors) {
		super();
		this.dateTime = dateTime;
		this.message = message;
		this.errors = errors;
	}

	public ApplicationException(LocalDateTime dateTime, String message, String error) {
		super();
		this.dateTime = dateTime;
		this.message = message;
		errors = Arrays.asList(error);
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

}