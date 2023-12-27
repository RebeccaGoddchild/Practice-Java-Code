package com.techelevator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FizzWriter {

	// Use this scanner for all user input. Don't create additional Scanners with System.in
	private final Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) {
		FizzWriter fizzWriter = new FizzWriter();
		fizzWriter.run();
	}

	public void run() {
		/* Your code goes here */
		try {
			// Specify the destination file path
			String destinationFilePath = "src/test/resources/fizzbuzz.txt";

			// Create a BufferedWriter for writing to the file
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFilePath))) {
				// Perform FizzBuzz and write to the file
				performFizzBuzz(writer);
			}

			System.out.println("FizzBuzz result written to " + destinationFilePath);
		} catch (IOException e) {
			System.out.println("Error during file operation: " + e.getMessage());
		}
	}

	private static void performFizzBuzz(BufferedWriter writer) throws IOException {
		for (int i = 1; i <= 300; i++) {
			if (i % 3 == 0 && i % 5 == 0) {
				writer.write("FizzBuzz");
			} else if (i % 3 == 0) {
				writer.write("Fizz");
			} else if (i % 5 == 0) {
				writer.write("Buzz");
			} else {
				writer.write(String.valueOf(i));
			}
			writer.newLine();
		}
	}
}



