package com.koomineat.koomineat.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ErrorCode {
    private final HttpStatus status;
    private final String code;
    private final String message;
}
