package com.learn.ems.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private String errorCode;
    private String message;
    private String path;
    private LocalDateTime timestamp;
}
