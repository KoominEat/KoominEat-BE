package com.koomineat.koomineat.global.exception;


import lombok.Getter;

@Getter
public class KookminEatException extends RuntimeException{
    private final ErrorCode errorCode;

    public KookminEatException(ErrorCode errorCode)
    {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}