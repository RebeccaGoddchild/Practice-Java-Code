package com.techelevator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class WordSearch {

	// Use this scanner for all user input. Don't create additional Scanners with System.in
	private final Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) {
		WordSearch wordSearch = new WordSearch();
		wordSearch.run();
	}

	public void run() {
		/* Your code goes here */
		System.out.println("What is the fully qualified name of the file that should be searched?");
		String filePath = userInput.nextLine();

		System.out.println("What is the search word you are looking for?");
		String searchWord = userInput.nextLine();

		// Prompt user for case sensitivity
		System.out.println("Do you want the search to be case-sensitive? (Y/N)");
		boolean caseSensitive = userInput.nextLine().equalsIgnoreCase("Y");

		// Search for the word in the file with specified case sensitivity
		searchWordInFile(filePath, searchWord, caseSensitive);
	}

	private void searchWordInFile(String filePath, String searchWord, boolean caseSensitive) {
		try (Scanner fileScanner = new Scanner(new FileReader(filePath))) {
			int lineNumber = 0;

			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine();
				lineNumber++;

				// Apply case sensitivity based on user input
				String lineToCheck = caseSensitive ? line : line.toLowerCase();
				String searchWordToCheck = caseSensitive ? searchWord : searchWord.toLowerCase();

				if (lineToCheck.contains(searchWordToCheck)) {
					System.out.println(lineNumber + ") " + line);
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading the file: " + e.getMessage());
		}
	}
}





