package com.techelevator;

public class Elevator {
    private int currentFloor;
    private int numberOfFloors;
    private boolean doorOpen;

    public Elevator(int numberOfFloors) {
        this.currentFloor = 1;
        this.numberOfFloors = numberOfFloors;
        this.doorOpen = false;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public boolean isDoorOpen() {
        return doorOpen;
    }

    public void openDoor() {
        doorOpen = true;
    }

    public void closeDoor() {
        doorOpen = false;
    }

    public void goUp(int floor) {
        if (!doorOpen && floor > currentFloor && floor <= numberOfFloors) {
            currentFloor = floor;
        }
    }

    public void goDown(int floor) {
        if (!doorOpen && floor < currentFloor && floor >= 1) {
            currentFloor = floor;
        }
    }
}
