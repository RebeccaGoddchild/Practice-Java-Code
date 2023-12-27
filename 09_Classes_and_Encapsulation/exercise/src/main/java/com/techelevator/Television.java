package com.techelevator;

public class Television {
    private boolean isOn;
    private int currentChannel;
    private int currentVolume;

    public Television() {
        this.isOn = false;
        this.currentChannel = 3;
        this.currentVolume = 2;
    }

    public boolean isOn() {
        return isOn;
    }

    public int getCurrentChannel() {
        return currentChannel;
    }

    public int getCurrentVolume() {
        return currentVolume;
    }

    public void turnOn() {

        isOn = true;
    }

    public void turnOff() {
        isOn = false;
    }

    public void changeChannel(int newChannel) {
        if (isOn) {
            if (newChannel >= 3 && newChannel <= 18) {
                currentChannel = newChannel;
            }
        }
    }

    public void channelUp() {
        if (isOn) {
            currentChannel = currentChannel  + 1;
            if (currentChannel == 19) {
                currentChannel = 3; // Reset to 3 if channel was 18
            }
        }
    }

        public void channelDown() {
            if (isOn) {
                if (currentChannel == 3) {
                    currentChannel = 18; // Reset to 18 if channel is 3
                } else {
                    currentChannel--;
                }
            }
        }


    public void raiseVolume() {
        if (isOn && currentVolume < 10) {
            currentVolume++;
        }
    }

    public void lowerVolume() {
        if (isOn && currentVolume > 0) {
            currentVolume--;
        }
    }
}


