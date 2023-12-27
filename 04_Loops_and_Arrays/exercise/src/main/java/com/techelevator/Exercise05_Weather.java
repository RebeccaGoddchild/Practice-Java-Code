package com.techelevator;

public class Exercise05_Weather {

    private final static int FREEZING_TEMPERATURE = 32;

    /*
    GaleForce Meteorologists recently developed a new weather station and need it to perform
    some common measurements for reporting.

    Note: Assume all temperatures are in Fahrenheit (°F) unless otherwise
    noted.
     */

    /*
    GaleForce needs to know the number of days in the upcoming forecast
    where the temperature is at or below freezing.

    Given an array of high temperatures, count the number of days when
    the high temperature is freezing (32° F) or below.

    Examples:
	belowFreezing([33, 30, 32, 37, 44, 31, 41]) → 3
	belowFreezing([-7, -3, 19, 35, 30])  → 4
	belowFreezing([]) → 0
    */
    public int belowFreezing(int[] dailyHighs) {
        int coldDays = 0;
        int place = dailyHighs.length - 1;
        while (place > -1) {
            if (dailyHighs[place] <= FREEZING_TEMPERATURE) {
                coldDays = coldDays + 1;
                place = place - 1;
            } else if (dailyHighs[place] > FREEZING_TEMPERATURE) {
                place = place - 1;
            }
        }
        return coldDays;
    }

    /*
    GaleForce also needs to determine the hottest day when given an upcoming forecast.

    Given an array of high temperatures, determine the hottest temperature.

    Note: The array of high temperatures is guaranteed to have at least one
    element.

    Examples:
	hottestDay([81, 93, 94, 105, 99, 95, 101, 90, 89, 92]) → 105
	hottestDay([23, 24] → 24
	hottestDay([34, 33] → 34
	hottestDay([55]) → 55
    */
    public int hottestDay(int[] dailyHighs) {
        int hottestDay = dailyHighs[0];
        int place = dailyHighs.length - 1;
        while (place > -1) {
            if (dailyHighs[place] > hottestDay) {
                hottestDay = dailyHighs[place];
                place = place - 1;
            } else if (dailyHighs[place] <= hottestDay) {
                place = place - 1;
            }
        }
        return hottestDay;
    }

    /*
    GaleForce discovered an equipment malfunction. Every other reading, starting with the first,
    was off by 2 degrees Fahrenheit (°F).

    Given an array of Fahrenheit temperatures, fix each of the incorrect readings by adding
    2 degrees to its current value.

    Examples:
	fixTemperatures([33, 30, 32, 37, 44, 31, 41]) → [35, 30, 34, 37, 46, 31, 43]
	fixTemperatures([-7, -33, 19, 35]) → [-5, -33, 21, 35]
	fixTemperatures([-1, 0, 1] → [1, 0, 3]
    fixTemperatures([-1] → [1]
	fixTemperatures([]) → []
     */
    public int[] fixTemperatures(int[] temperatures) {
        int place = temperatures.length - 1;
        while (place > -1) {
            if (place% 2 == 0){
                temperatures[place]=temperatures[place]+2;
                place = place - 1;
            } else if (place% 2== 1) {
                place = place - 1;
            } else if (place==1) {
                temperatures[place] = temperatures[place] + 2;
            } else if (place==0) {
                temperatures[place] = temperatures[place] + 2;
            }
        }return temperatures;
    }
}
