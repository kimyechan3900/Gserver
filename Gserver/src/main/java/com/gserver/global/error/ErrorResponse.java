package com.gserver.global.error;

import lombok.*;

@Getter
@Setter
@Builder
public class ErrorResponse {
    private int statusCode;
    private String errorCode;
    private String message;
}