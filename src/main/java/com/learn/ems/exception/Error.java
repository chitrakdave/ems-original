package com.learn.ems.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum Error {

    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND, "EMP_01"),

    EMPLOYEE_ALREADY_EXISTS(HttpStatus.CONFLICT, "EMP_02"),

    DEPT_NOT_FOUND(HttpStatus.NOT_FOUND, "DEPT_01");

    private final HttpStatus status;
    private final String code;

}
