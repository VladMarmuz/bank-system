package com.banksystem.tech.handler;

import com.banksystem.loan.controller.LoanController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@RestControllerAdvice
public class ControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanController.class);

    private static final String DEFAULT_ERROR_MESSAGE = "Problems on the server. Please try later or contact admin";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionBody> handleException(Exception e) {
        return createExceptionBody(e, DEFAULT_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionBody> handleRuntimeException(RuntimeException e) {
        return createExceptionBody(e, DEFAULT_ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionBody> handleMethodArgumentNotValidException(final MethodArgumentNotValidException exp){
        final String errorMsg = exp.getBindingResult().getAllErrors().stream()
                .findFirst()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .orElse(exp.getMessage());
        return createExceptionBody(exp, errorMsg, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ExceptionBody> createExceptionBody(Exception e, String errorMsg, HttpStatus status) {
        LOGGER.error(e.getMessage(), e.hashCode() + ": " + e);
        return new ResponseEntity<>(new ExceptionBody(ZonedDateTime.now(), status, errorMsg), status);
    }

}
