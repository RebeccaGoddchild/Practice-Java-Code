package com.techelevator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FindAndReplace {

    // Use this scanner for all user input. Don't create additional Scanners with System.in
    private final Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {
        FindAndReplace findAndReplace = new FindAndReplace();
        findAndReplace.run();
    }

    public void run() {
        /* Your code goes here */
        System.out.println("What is the search word?");
        String searchWord = userInput.nextLine();

        // Prompt user for replacement word
        System.out.println("What is the replacement word?");
        String replacementWord = userInput.nextLine();

        // Prompt user for source file
        System.out.println("What is the source file?");
        String sourceFilePath = userInput.nextLine();

        // Prompt user for destination file
        System.out.println("What is the destination file?");
        String destinationFilePath = userInput.nextLine();

        try {
            // Validate source file existence
            validateFileExists(sourceFilePath);

            // Replace words and copy to destination file
            replaceAndCopyFile(sourceFilePath, destinationFilePath, searchWord, replacementWord);

            System.out.println("Replacement successful. Copied to " + destinationFilePath);
        } catch (FileValidationException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error during file operation: " + e.getMessage());
        }
    }

    private static void validateFileExists(String filePath) throws FileValidationException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new FileValidationException("Invalid file path or file does not exist: " + filePath);
        }
    }

    private static void replaceAndCopyFile(String sourceFilePath, String destinationFilePath,
                                           String searchWord, String replacementWord) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Replace the search word with the replacement word
                line = line.replaceAll(searchWord, replacementWord);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error during file replacement: " + e.getMessage());
        }
    }

    private static class FileValidationException extends Exception {
        public FileValidationException(String message) {
            super(message);
        }

    }

}
