package com.equalexperts.exception;

public class PositionOutsideMapException extends InvalidArgumentsException{
    public PositionOutsideMapException(String errorMessage) {
        super(errorMessage);
    }
}
