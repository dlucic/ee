package com.equalexperts;

import com.equalexperts.rover.Rover;

import java.util.Scanner;

public class Application {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		System.out.print("Please enter rover initial coordinates and direction using format (x, y, direction): ");

		try {
			Rover rover = new Rover();
			rover.land(scanner.nextLine());
			System.out.print("Enter movement command: ");
			String movement = rover.executeMovementCommand(scanner.nextLine());
			System.out.println(movement);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			scanner.close();
		}
	}
}