package com.techelevator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuizMaker {

	// Use this scanner for all user input. Don't create additional Scanners with System.in
	private final Scanner userInput = new Scanner(System.in);

	public static void main(String[] args) {
		QuizMaker quizMaker = new QuizMaker();
		quizMaker.run();
	}

	public void run() {
		/* Your code goes here */
		List<Question> questions = loadQuestionsFromFile("questions.txt");

		if (questions.isEmpty()) {
			System.out.println("No questions found. Exiting.");
			return;
		}

		System.out.println("Welcome to the Quiz!\n");

		for (Question question : questions) {
			presentQuestion(question);
		}

		System.out.println("Quiz complete. Thanks for playing!");
	}

	private List<Question> loadQuestionsFromFile(String filePath) {
		List<Question> questions = new ArrayList<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (parts.length >= 3) {
					String questionText = parts[0];
					List<String> choices = new ArrayList<>();
					String correctAnswer = null;

					for (int i = 1; i < parts.length; i++) {
						choices.add(parts[i]);
						if (parts[i].endsWith("*")) {
							correctAnswer = parts[i].substring(0, parts[i].length() - 1);
						}
					}

					if (correctAnswer != null) {
						Question question = new Question(questionText, choices, correctAnswer);
						questions.add(question);
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Error reading the file: " + e.getMessage());
		}

		return questions;
	}

	private void presentQuestion(Question question) {
		System.out.println(question.getQuestionText());
		List<String> choices = question.getChoices();

		for (int i = 0; i < choices.size(); i++) {
			System.out.println((char) ('A' + i) + ". " + choices.get(i));
		}

		System.out.print("Your answer: ");
		String userAnswer = userInput.nextLine().toUpperCase();

		if (userAnswer.equals(question.getCorrectAnswer())) {
			System.out.println("Correct!\n");
		} else {
			System.out.println("Incorrect. The correct answer was: " + question.getCorrectAnswer() + "\n");
		}
	}

	private static class Question {
		private final String questionText;
		private final List<String> choices;
		private final String correctAnswer;

		public Question(String questionText, List<String> choices, String correctAnswer) {
			this.questionText = questionText;
			this.choices = choices;
			this.correctAnswer = correctAnswer;
		}

		public String getQuestionText() {
			return questionText;
		}

		public List<String> getChoices() {
			return choices;
		}

		public String getCorrectAnswer() {
			return correctAnswer;
		}
	}
}


