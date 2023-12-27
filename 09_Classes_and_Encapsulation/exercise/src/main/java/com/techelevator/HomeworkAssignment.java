package com.techelevator;
import java.util.Scanner;

public class HomeworkAssignment {
    private int earnedMarks;
    private int possibleMarks;
    private String submitterName;

    public HomeworkAssignment(int earnedMarks, String submitterName) {
        this.earnedMarks = earnedMarks;
        this.submitterName = submitterName;
        this.possibleMarks = 100; // Defaulting to 100 for possible marks, adjust as needed
    }

    public int getPossibleMarks() {
        return possibleMarks;
    }

    public int getEarnedMarks() {
        return earnedMarks;
    }

    public void setEarnedMarks(int earnedMarks) {
        this.earnedMarks = earnedMarks;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public String getLetterGrade() {
        double percentage = (double) earnedMarks / possibleMarks * 100;

        if (percentage >= 90) {
            return "A";
        } else if (percentage >= 80) {
            return "B";
        } else if (percentage >= 70) {
            return "C";
        } else if (percentage >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
}


