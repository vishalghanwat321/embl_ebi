package com.org.web.embl_ebi.handler.exception;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.org.web.embl_ebi.person.util.exception.DuplicateRecordException;
import com.org.web.embl_ebi.person.util.exception.RecordNotFoundException;

/**
 * @author Vishal Ghanwat
 * @version 1.0.0
 * @implNote CustomRestApiExceptionHandler is to handle exception.
 * @since 2020
 */
@ControllerAdvice
public class CustomRestApiExceptionHandler extends ResponseEntityExceptionHandler {

	// User Defined Exception
	// 404

	@ExceptionHandler({ RecordNotFoundException.class })
	public final ResponseEntity<Object> handleItemNotFoundException(RuntimeException ex, WebRequest request) {
		logger.info(ex.getClass().getName());
		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), "Record not found...");
		return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
	}

	// 409
	@ExceptionHandler({ DuplicateRecordException.class })
	public final ResponseEntity<Object> handleDuplicateRequestException(RuntimeException ex, WebRequest request) {
		logger.info(ex.getClass().getName());
		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), "Record already exists...");
		return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
	}

	// Generic Exception
	// 400

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), errors);
		return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();

		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getRequestPartName() + " part is missing";
		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getParameterName() + " parameter is missing";
		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), errors);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	// 404

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), error);
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	// 405

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), builder.toString());
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	// 415

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	// 500

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		logger.info(ex.getClass().getName());
		logger.error("error", ex);
		//
		final ApplicationException apiError = new ApplicationException(LocalDateTime.now(ZoneOffset.UTC),
				ex.getLocalizedMessage(), "error occurred");
		return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}