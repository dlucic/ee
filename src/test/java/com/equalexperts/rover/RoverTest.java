package com.equalexperts.rover;

import com.equalexperts.exception.InvalidArgumentsException;
import com.equalexperts.exception.InvalidDirectionArgumentException;
import com.equalexperts.exception.InvalidMoveCommandException;
import com.equalexperts.exception.PositionOutsideMapException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RoverTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private Rover rover;

    @BeforeEach
    void setUp() {
        rover = new Rover();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void cleanUp() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    void shouldLandRoverWhenValidPositionIsGiven() throws Exception {
        rover.land("1, 1, NORTH");

        assertEquals(1, rover.getPositionX());
        assertEquals(1, rover.getPositionY());
        assertEquals(Direction.NORTH, rover.getDirection());
        assertEquals("Touchdown! Rover has landed!\n", outContent.toString());
    }

    @Test
    void shouldThrowErrorWhenLandingRoverBeyondMapBoundary() {

        Exception exception = assertThrows(PositionOutsideMapException.class, () -> rover.land("22, 1, NORTH"));
        String expectedMessage = "Rover position is outside the map!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowErrorWhenInvalidDirectionProvided() {

        Exception exception = assertThrows(InvalidDirectionArgumentException.class, () -> rover.land("1, 1, SOUTHEAST"));
        String expectedMessage = "Invalid direction argument";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowErrorWhenInvalidNumberOfArgumentsProvided() {

        Exception exception = assertThrows(InvalidArgumentsException.class, () -> rover.land("1, 1"));
        String expectedMessage = "Invalid number of arguments provided";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @ParameterizedTest
    @ValueSource(strings = {"a, 1, WEST", "1, a, WEST"})
    void shouldThrowErrorWhenNumberNotProvidedForPosition(String location) {

        Exception exception = assertThrows(InvalidArgumentsException.class, () -> rover.land(location));
        String expectedMessage = "X and Y arguments should be a number between -10 and 10";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldSucceedMovementWhenValidMovementCommandGiven() throws InvalidArgumentsException {

        rover.land("0, 0, NORTH");
        String commandOutput = rover.executeMovementCommand("FFRFFRFFL");

        assertEquals("(2, 0) EAST", commandOutput);
    }

    @Test
    void shouldThrowErrorWhenRoverGoesBeyondMapBoundary() throws InvalidArgumentsException {

        rover.land("10, 10, NORTH");
        Exception exception = assertThrows(PositionOutsideMapException.class, () -> rover.executeMovementCommand("F"));
        String expectedMessage = "Rover position is outside the map!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowErrorWhenInvalidMoveCommandIsProvided() throws InvalidArgumentsException {

        rover.land("1, 1, NORTH");
        Exception exception = assertThrows(InvalidMoveCommandException.class, () -> rover.executeMovementCommand("H"));
        String expectedMessage = "Invalid move command";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}