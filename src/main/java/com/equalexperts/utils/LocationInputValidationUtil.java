package com.equalexperts.utils;

import com.equalexperts.exception.InvalidArgumentsException;

public class LocationInputValidationUtil {

    public static void validateInput(String[] parameters) throws InvalidArgumentsException {
        if (3 != parameters.length) {
            throw new InvalidArgumentsException("Invalid number of arguments provided");
        }
    }

    public static int getIntValue(String input) throws InvalidArgumentsException {
        try {
            return Integer.parseInt(input.strip());
        } catch (NumberFormatException ex) {
            throw new InvalidArgumentsException("X and Y arguments should be a number between -10 and 10");
        }
    }
}
