package com.brunood.social_network.core.exception;

import com.brunood.social_network.core.exception.custom.BusinessException;
import com.brunood.social_network.core.exception.custom.GeneralException;
import com.brunood.social_network.core.exception.custom.RecordNotFoundException;
import com.brunood.social_network.core.exception.error.StandardError;
import com.brunood.social_network.core.exception.response.standard.ResponseDTO;
import com.brunood.social_network.core.exception.response.standard.ResponseType;
import com.brunood.social_network.core.exception.response.standard.StandardResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.EOFException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private String generalRequestExceptionMessage = "Verifique a requisição enviada.";
    private String generalRequestExceptionCode = "003-ERRO-REQUISICAO";

    /* Custom exceptions */

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseDTO handleBusinessException(BusinessException ex, WebRequest request) {

        return StandardResponse.generateObjectResponse(
                ex.getMessage(),
                null,
                ResponseType.ERROR,
                HttpStatus.UNPROCESSABLE_ENTITY,
                StandardError.builder()
                        .code(ex.getCode())
                        .description(ex.getMessage())
                        .build()
        );

    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseDTO handleRecordNotFoundException(RecordNotFoundException ex, WebRequest request) {

        return StandardResponse.generateObjectResponse(
                ex.getMessage(),
                null,
                ResponseType.ERROR,
                HttpStatus.NOT_FOUND,
                StandardError.builder()
                        .code(ex.getCode())
                        .description(ex.getMessage())
                        .build()
        );
    }

    @ExceptionHandler({GeneralException.class, Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseDTO handleGeneralException(GeneralException ex, WebRequest request) {

        return StandardResponse.generateObjectResponse(
                ex.getMessage(),
                null,
                ResponseType.ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,
                StandardError.builder()
                        .code(ex.getCode())
                        .description(ex.getMessage())
                        .build()
        );
    }

    /* End Custom exceptions */

    @ExceptionHandler({EOFException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseDTO handlePathGeneralException(GeneralException ex, WebRequest request) {

        return StandardResponse.generateObjectResponse(
                messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()),
                null,
                ResponseType.ERROR,
                HttpStatus.BAD_REQUEST,
                StandardError.builder()
                        .code(messageSource.getMessage(generalRequestExceptionCode, null, Locale.getDefault()))
                        .description(messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()))
                        .build()
        );
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ResponseDTO responseDTO = StandardResponse.generateObjectResponse(
                messageSource.getMessage("general.methodnotallowed.exception.message", null, Locale.getDefault()),
                null,
                ResponseType.ERROR,
                HttpStatus.METHOD_NOT_ALLOWED,
                StandardError.builder()
                        .code(messageSource.getMessage("general.methodnotallowed.exception.code", null, Locale.getDefault()))
                        .description(messageSource.getMessage("general.methodnotallowed.exception.message", null, Locale.getDefault()))
                        .build()
        );
        return super.handleExceptionInternal(ex, responseDTO, headers, status, request);
    }

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ResponseDTO responseDTO = StandardResponse.generateObjectResponse(
                messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()),
                null,
                ResponseType.ERROR,
                HttpStatus.BAD_REQUEST,
                StandardError.builder()
                        .code(messageSource.getMessage(generalRequestExceptionCode, null, Locale.getDefault()))
                        .description(messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()))
                        .build()
        );
        return super.handleExceptionInternal(ex, responseDTO, headers, status, request);
    }


    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        ResponseDTO responseDTO = StandardResponse.generateObjectResponse(
                messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()),
                null,
                ResponseType.ERROR,
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                StandardError.builder()
                        .code(messageSource.getMessage(generalRequestExceptionCode, null, Locale.getDefault()))
                        .description(messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()))
                        .build()
        );
        return super.handleExceptionInternal(ex, responseDTO, headers, status, request);
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ResponseDTO responseDTO = StandardResponse.generateObjectResponse(
                messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()),
                errors.toString(),
                ResponseType.ERROR,
                HttpStatus.BAD_REQUEST,
                StandardError.builder()
                        .code(messageSource.getMessage(generalRequestExceptionCode, null, Locale.getDefault()))
                        .description(messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()))
                        .build()
        );
        return super.handleExceptionInternal(ex, responseDTO, headers, status, request);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler({ConstraintViolationException.class, ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.CONFLICT)
    protected ResponseDTO handleConstraintViolation(
            ConstraintViolationException ex) {
        return StandardResponse.generateObjectResponse(
                messageSource.getMessage("general.constraintviolation.exception.message", null, Locale.getDefault()),
                null,
                ResponseType.ERROR,
                HttpStatus.CONFLICT,
                StandardError.builder()
                        .code(messageSource.getMessage("general.constraintviolation.exception.code", null, Locale.getDefault()))
                        .description(messageSource.getMessage("general.constraintviolation.exception.message", null, Locale.getDefault()))
                        .build()
        );
    }


    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ResponseDTO responseDTO = StandardResponse.generateObjectResponse(
                messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()),
                null,
                ResponseType.ERROR,
                HttpStatus.BAD_REQUEST,
                StandardError.builder()
                        .code(messageSource.getMessage(generalRequestExceptionCode, null, Locale.getDefault()))
                        .description(messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()))
                        .build()
        );
        return super.handleExceptionInternal(ex, responseDTO, headers, status, request);
    }

    /**
     * Handle HttpMessageNotWritableException.
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ResponseDTO responseDTO = StandardResponse.generateObjectResponse(
                messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()),
                null,
                ResponseType.ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR,
                StandardError.builder()
                        .code(messageSource.getMessage(generalRequestExceptionCode, null, Locale.getDefault()))
                        .description(messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()))
                        .build()
        );

        return super.handleExceptionInternal(ex, responseDTO, headers, status, request);
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ResponseDTO responseDTO = StandardResponse.generateObjectResponse(
                messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()),
                null,
                ResponseType.ERROR,
                HttpStatus.BAD_REQUEST,
                StandardError.builder()
                        .code(messageSource.getMessage(generalRequestExceptionCode, null, Locale.getDefault()))
                        .description(messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()))
                        .build()
        );
        return super.handleExceptionInternal(ex, responseDTO, headers, status, request);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    protected ResponseDTO handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                           WebRequest request) {
        return StandardResponse.generateObjectResponse(
                messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()),
                null,
                ResponseType.ERROR,
                HttpStatus.BAD_REQUEST,
                StandardError.builder()
                        .code(messageSource.getMessage(generalRequestExceptionCode, null, Locale.getDefault()))
                        .description(messageSource.getMessage(generalRequestExceptionMessage, null, Locale.getDefault()))
                        .build()
        );
    }


}

