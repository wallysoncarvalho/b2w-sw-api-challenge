package info.wallyson.swc.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  private final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

  @ExceptionHandler({ApiException.class, ConstraintViolationException.class})
  protected ResponseEntity<Object> handleUserException(Exception ex) {
    LOG.error(ex.getMessage());

    return getErrorResponseEntity(400, ex.getMessage(), null);
  }

  @ExceptionHandler({RestClientException.class})
  protected ResponseEntity<Object> handleRestClientException(RestClientException e) {
    LOG.error("Error calling SW API");
    LOG.error("SW API ERROR: " + e.getMessage());
    return getErrorResponseEntity(500, "Error calling SW API!", null);
  }

  @Override
  protected ResponseEntity<Object> handleHttpMessageNotReadable(
      HttpMessageNotReadableException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return getErrorResponseEntity(status.value(), "Malformed JSON", null);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestPart(
      MissingServletRequestPartException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    return getErrorResponseEntity(
        status.value(), String.format("Param %s missing", ex.getRequestPartName()), null);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {

    var errors = new HashMap<String, String>();

    LOG.error("Constraint errors!");

    ex.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });

    return getErrorResponseEntity(400, "Constraints errors!", errors);
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

    var message =
        String.format(
            "No service found for '%s' request on resource '%s'",
            ex.getHttpMethod(), ex.getRequestURL());

    LOG.warn(message);

    return getErrorResponseEntity(404, message, null);
  }

  private ResponseEntity<Object> getErrorResponseEntity(int status, String message, Object errors) {
    var apiResponse = new ErrorResponse(status, message, errors);
    return ResponseEntity.status(apiResponse.getStatus()).body(apiResponse);
  }
}
