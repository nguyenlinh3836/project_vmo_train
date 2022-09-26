package com.example.project_vmo.commons.exception;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    List<String> details = new ArrayList<String>();


    StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));

    details.add(builder.toString());

    ApiError err = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), "Invalid JSON" ,details);

    return ResponseEntityBuilder.build(err);

  }

  // handleHttpMessageNotReadable : triggers when the JSON is malformed
  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Malformed JSON request" ,details);

    return ResponseEntityBuilder.build(err);
  }

  // handleMethodArgumentNotValid : triggers when @Valid fails
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<String> details = new ArrayList<String>();
    details = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getObjectName()+ " : " +error.getDefaultMessage())
        .collect(Collectors.toList());

    ApiError err = new ApiError(LocalDateTime.now(),
        HttpStatus.BAD_REQUEST.value(),
        "Validation Errors" ,
        details);

    return ResponseEntityBuilder.build(err);
  }

  // handleMissingServletRequestParameter : triggers when there are missing parameters
  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex, HttpHeaders headers,
      HttpStatus status, WebRequest request) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getParameterName() + " parameter is missing");

    ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Missing Parameters" ,details);

    return ResponseEntityBuilder.build(err);
  }

  // handleMethodArgumentTypeMismatch : triggers when a parameter's type does not match
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
      WebRequest request) {
    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Mismatch Type" ,details);

    return ResponseEntityBuilder.build(err);
  }

  // handleConstraintViolationException : triggers when @Validated fails
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handleConstraintViolationException(Exception ex, WebRequest request) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Constraint Violation" ,details);

    return ResponseEntityBuilder.build(err);
  }
  @ExceptionHandler(ResourceAlreadyExistsException.class)
  public ResponseEntity<Object> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex){
    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());
    ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Resource already exist! " ,details);
    return ResponseEntityBuilder.build(err);
  }

  // handleResourceNotFoundException : triggers when there is not resource with the specified ID in BDD
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getMessage());

    ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.NOT_FOUND.value(), "Resource Not Found" ,details);

    return ResponseEntityBuilder.build(err);
  }

  // handleNoHandlerFoundException : triggers when the handler method is invalid
  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    List<String> details = new ArrayList<String>();
    details.add(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));

    ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Method Not Found" ,details);

    return ResponseEntityBuilder.build(err);

  }

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {

    List<String> details = new ArrayList<String>();
    details.add(ex.getLocalizedMessage());

    ApiError err = new ApiError(LocalDateTime.now(),HttpStatus.BAD_REQUEST.value(), "Error occurred" ,details);

    return ResponseEntityBuilder.build(err);
  }


}
