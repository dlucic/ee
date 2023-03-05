package com.equalexperts.rover;


import com.equalexperts.exception.InvalidArgumentsException;
import com.equalexperts.exception.InvalidDirectionArgumentException;
import com.equalexperts.exception.InvalidMoveCommandException;
import com.equalexperts.exception.PositionOutsideMapException;

import static com.equalexperts.map.Map.MAP_BOUNDARY_MAX;
import static com.equalexperts.map.Map.MAP_BOUNDARY_MIN;
import static com.equalexperts.utils.LocationInputValidationUtil.getIntValue;
import static com.equalexperts.utils.LocationInputValidationUtil.validateInput;

public class Rover {

    private int positionX;
    private int positionY;
    private Direction direction;

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPositionX(int positionX) throws PositionOutsideMapException {
        if (positionX <= MAP_BOUNDARY_MAX && positionX >= MAP_BOUNDARY_MIN) {
            this.positionX = positionX;
        } else {
            throw new PositionOutsideMapException("Rover position is outside the map!");
        }
    }

    public void setPositionY(int positionY) throws PositionOutsideMapException {
        if (positionY <= MAP_BOUNDARY_MAX && positionY >= MAP_BOUNDARY_MIN) {
            this.positionY = positionY;
        } else {
            throw new PositionOutsideMapException("Rover position is outside the map!");
        }
    }

    public void setDirection(String direction) throws InvalidDirectionArgumentException {
        try {
            this.direction = Direction.valueOf(direction);
        } catch (Exception e) {
            throw new InvalidDirectionArgumentException("Invalid direction argument");
        }
    }

    public void move(Character command) throws InvalidArgumentsException {
        switch (command) {
            case 'F' -> moveForward();
            case 'B' -> moveBackwards();
            case 'L' -> turnLeft();
            case 'R' -> turnRight();
            default -> throw new InvalidMoveCommandException("Invalid move command");
        }
    }

    public void moveForward() throws PositionOutsideMapException {
        if (Direction.NORTH == this.direction) {
            this.setPositionY(this.positionY + 1);
        } else if (Direction.SOUTH == this.direction) {
            this.setPositionY(this.positionY - 1);
        } else if (Direction.WEST == this.direction) {
            this.setPositionX(this.positionX - 1);
        } else {
            this.setPositionX(this.positionX + 1);
        }
    }

    public void moveBackwards() throws PositionOutsideMapException {
        if (Direction.NORTH == this.direction) {
            this.setPositionY(this.positionY - 1);
        } else if (Direction.SOUTH == this.direction) {
            this.setPositionY(this.positionY + 1);
        } else if (Direction.WEST == this.direction) {
            this.setPositionX(this.positionX + 1);
        } else {
            this.setPositionX(this.positionX - 1);
        }
    }

    public void turnLeft() {
        if (Direction.NORTH == this.direction) {
            this.direction = Direction.WEST;
        } else if (Direction.SOUTH == this.direction) {
            this.direction = Direction.EAST;
        } else if (Direction.WEST == this.direction) {
            this.direction = Direction.SOUTH;
        } else {
            this.direction = Direction.NORTH;
        }
    }

    public void turnRight() {
        if (Direction.NORTH == this.direction) {
            this.direction = Direction.EAST;
        } else if (Direction.SOUTH == this.direction) {
            this.direction = Direction.WEST;
        } else if (Direction.WEST == this.direction) {
            this.direction = Direction.NORTH;
        } else {
            this.direction = Direction.SOUTH;
        }
    }

    public String executeMovementCommand(String command) throws InvalidArgumentsException {
        char[] chars = command.toCharArray();

        for (char ch : chars) {
            move(Character.toUpperCase(ch));
        }
        return String.format("(%d, %d) %s", this.positionX, this.positionY, this.direction);
    }

    public void land(String landingLocation) throws InvalidArgumentsException {
        String[] parameters = landingLocation.split(",");
        validateInput(parameters);
        this.setPositionX(getIntValue(parameters[0]));
        this.setPositionY(getIntValue(parameters[1]));
        this.setDirection(parameters[2].strip().toUpperCase());
        System.out.println("Touchdown! Rover has landed!");
    }
}
