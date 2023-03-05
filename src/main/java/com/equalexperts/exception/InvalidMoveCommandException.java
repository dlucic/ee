package com.equalexperts.exception;

public class InvalidMoveCommandException extends InvalidArgumentsException{
    public InvalidMoveCommandException(String errorMessage) {
        super(errorMessage);
    }
}
