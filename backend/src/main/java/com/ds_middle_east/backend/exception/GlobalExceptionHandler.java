package com.ds_middle_east.backend.exception;

import com.ds_middle_east.backend.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response<?>> handleNotFoundExceptions(NotFoundException ex) {
        return handleExceptions(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Response<?>> handleBusinessExceptionExceptions(BusinessException ex) {
        return handleExceptions(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<?>> handleAllUnknownExceptions(Exception ex) {
        return handleExceptions(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response<?>> handleValidation(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        Response<?> response = Response.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(errors.toString())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Response<?>> handleExceptions(Exception ex, HttpStatus status) {
        Response<?> response = Response.builder()
                .statusCode(status.value())
                .message(ex.getMessage())
                .build();
        return new ResponseEntity<>(response, status);
    }
}