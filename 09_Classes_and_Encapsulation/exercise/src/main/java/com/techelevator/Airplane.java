package com.techelevator;

public class Airplane {
    private String planeNumber;
    private int totalFirstClassSeats;
    private int totalCoachSeats;
    private int bookedFirstClassSeats;
    private int bookedCoachSeats;

    public Airplane(String planeNumber, int totalFirstClassSeats, int totalCoachSeats) {
        this.planeNumber = planeNumber;
        this.totalFirstClassSeats = totalFirstClassSeats;
        this.totalCoachSeats = totalCoachSeats;
        this.bookedFirstClassSeats = 0;
        this.bookedCoachSeats = 0;
    }

    public String getPlaneNumber() {
        return planeNumber;
    }

    public int getBookedFirstClassSeats() {
        return bookedFirstClassSeats;
    }

    public int getAvailableFirstClassSeats() {
        return totalFirstClassSeats - bookedFirstClassSeats;
    }

    public int getTotalFirstClassSeats() {
        return totalFirstClassSeats;
    }

    public int getBookedCoachSeats() {
        return bookedCoachSeats;
    }

    public int getAvailableCoachSeats() {
        return totalCoachSeats - bookedCoachSeats;
    }

    public int getTotalCoachSeats() {
        return totalCoachSeats;
    }

    public boolean reserveSeats(boolean forFirstClass, int totalNumberOfSeats) {
        if (forFirstClass) {
            if (totalNumberOfSeats <= getAvailableFirstClassSeats()) {
                bookedFirstClassSeats += totalNumberOfSeats;
                return true;
            } else {
                return false;
            }
        } else {
            if (totalNumberOfSeats <= getAvailableCoachSeats()) {
                bookedCoachSeats += totalNumberOfSeats;
                return true;
            } else {
                return false;
            }
        }
    }
}
