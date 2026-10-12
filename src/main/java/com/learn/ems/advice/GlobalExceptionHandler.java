package com.learn.ems.advice;

import com.learn.ems.dto.response.ErrorResponse;
import com.learn.ems.exception.DepartmentException;
import com.learn.ems.exception.EmployeeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(exception = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception,
                                                                               HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorCode("Invalid Input");
        errorResponse.setMessage(exception.getBindingResult().getFieldError().getDefaultMessage());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(exception = EmployeeException.class)
    public ResponseEntity<ErrorResponse> handleEmployeeException(EmployeeException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorCode(exception.getError().getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(exception.getError().getStatus()).body(errorResponse);
    }

    @ExceptionHandler(exception = DepartmentException.class)
    public ResponseEntity<ErrorResponse> handleDepartmentException(DepartmentException exception, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();

        errorResponse.setErrorCode(exception.getError().getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setTimestamp(LocalDateTime.now());

        return ResponseEntity.status(exception.getError().getStatus()).body(errorResponse);
    }

}
