package com.techelevator;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;
public class Exercises {

	/*
	 * Given the name of an animal, return the name of a group of that animal
	 * (e.g. "Elephant" -> "Herd", "Rhino" - "Crash").
	 *
	 * The animal name should be case insensitive so "elephant", "Elephant", and
	 * "ELEPHANT" should all return "Herd".
	 *
	 * If the name of the animal is not found, null, or empty, return "unknown".
	 *
	 * Rhino -> Crash
	 * Giraffe -> Tower
	 * Elephant -> Herd
	 * Lion -> Pride
	 * Crow -> Murder
	 * Pigeon -> Kit
	 * Flamingo -> Pat
	 * Deer -> Herd
	 * Dog -> Pack
	 * Crocodile -> Float
	 *
	 * animalGroupName("giraffe") → "Tower"
	 * animalGroupName("") -> "unknown"
	 * animalGroupName("walrus") -> "unknown"
	 * animalGroupName("Rhino") -> "Crash"
	 * animalGroupName("rhino") -> "Crash"
	 * animalGroupName("elephants") -> "unknown"
	 *
	 */
	public String animalGroupName(String animalName) {
		Map<String, String> animals = new HashMap<>();
		animals.put("elephant", "Herd");
		animals.put("lion", "Pride");
		animals.put("crow", "Murder");
		animals.put("pigeon", "Kit");
		animals.put("flamingo", "Pat");
		animals.put("deer", "Herd");
		animals.put("dog", "Pack");
		animals.put("crocodile", "Float");
		animals.put("giraffe", "Tower");

		if (animalName == null || animalName.isEmpty() || !animals.containsKey(animalName.toLowerCase())) {
			return "unknown";
		} else {
			return animals.get(animalName.toLowerCase());
		}
	}

	/*
	 * Given a String item number (a.k.a. SKU), return the discount percentage if the item is on sale.
	 * If the item is not on sale, return 0.00.
	 *
	 * If the item number is empty or null, return 0.00.
	 *
	 * "KITCHEN4001" -> 0.20
	 * "GARAGE1070" -> 0.15
	 * "LIVINGROOM"	-> 0.10
	 * "KITCHEN6073" -> 0.40
	 * "BEDROOM3434" -> 0.60
	 * "BATH0073" -> 0.15
	 *
	 * The item number should be case insensitive so "kitchen4001", "Kitchen4001", and "KITCHEN4001"
	 * should all return 0.20.
	 *
	 * isItOnSale("kitchen4001") → 0.20
	 * isItOnSale("") → 0.00
	 * isItOnSale("GARAGE1070") → 0.15
	 * isItOnSale("dungeon9999") → 0.00
	 *
	 */
	public double isItOnSale(String itemNumber) {
		Map<String, Double> sale = new HashMap<>();
		sale.put("KITCHEN4001",0.20);
		sale.put("GARAGE1070",0.15);
		sale.put("LIVINGROOM",0.10);
		sale.put("KITCHEN6073",0.40);
		sale.put("BEDROOM3434",0.60);
		sale.put("BATH0073",0.15);
		if (itemNumber==null|| itemNumber.isEmpty() || !sale.containsKey(itemNumber.toUpperCase())){
			return 0.00;
		}else {
			return sale.get(itemNumber.toUpperCase());
		}
	}

	/*
	 * Modify and return the given Map as follows: if "Peter" has more than 0 money, transfer half of it to "Paul",
	 * but only if Paul has less than $10s.
	 *
	 * Note, monetary amounts are specified in cents: penny=1, nickel=5, ... $1=100, ... $10=1000, ...
	 *
	 * robPeterToPayPaul({"Peter": 2000, "Paul": 99}) → {"Peter": 1000, "Paul": 1099}
	 * robPeterToPayPaul({"Peter": 2000, "Paul": 30000}) → {"Peter": 2000, "Paul": 30000}
	 * robPeterToPayPaul({"Peter": 101, "Paul": 500}) → {"Peter": 51, "Paul": 550}
	 * robPeterToPayPaul({"Peter": 0, "Paul": 500}) → {"Peter": 0, "Paul": 500}
	 *
	 */
	public Map<String, Integer> robPeterToPayPaul(Map<String, Integer> peterPaul) {
		Set<String>keys = peterPaul.keySet(); //create a set to loop

		for(String money: keys) {
			if((peterPaul.get("Peter") > 0) && (peterPaul.get("Paul") < 1000)){
				if(peterPaul.get("Peter") % 2 == 1) {
					peterPaul.put("Paul", (peterPaul.get("Paul") + peterPaul.get("Peter") / 2) ); //takes 1/2 peter's money and gives it to paul and fixes rounding issue
					peterPaul.put("Peter", (peterPaul.get("Peter") / 2) + 1); // sets peter's new halved value and fixes rounding issue by adding 1
				} else {
					peterPaul.put("Paul", (peterPaul.get("Paul") + peterPaul.get("Peter") / 2)); //takes 1/2 peter's money and gives it to paul
					peterPaul.put("Peter", (peterPaul.get("Peter") / 2) ); // sets peter's new halved value
				}
			}
		}

		return peterPaul;
	}

	/*
	 * Modify and return the given Map as follows: if "Peter" has $50 or more, AND "Paul" has $100 or more,
	 * then create a new "PeterPaulPartnership" worth a combined contribution of a quarter of each partner's
	 * current worth.
	 *
	 * peterPaulPartnership({"Peter": 50000, "Paul": 100000}) → {"Peter": 37500, "Paul": 75000, "PeterPaulPartnership": 37500}
	 * peterPaulPartnership({"Peter": 3333, "Paul": 1234567890}) → {"Peter": 3333, "Paul": 1234567890}
	 *
	 */
	public Map<String, Integer> peterPaulPartnership(Map<String, Integer> peterPaul) {
		int petersMoney = peterPaul.get("Peter");
		int paulsMoney = peterPaul.get("Paul");
		int partnershipMoney = 0;
		int petersContribution = 0;
		int paulsContribution = 0;

		if (petersMoney >= 5000 && paulsMoney >= 10000) {

			petersContribution = petersMoney / 4;
			petersMoney = petersMoney - petersContribution;
			paulsContribution = paulsMoney / 4;
			paulsMoney = paulsMoney - paulsContribution;

			partnershipMoney = paulsContribution + petersContribution;
			peterPaul.put("Peter", petersMoney);
			peterPaul.put("Paul", paulsMoney);
			peterPaul.put("PeterPaulPartnership", partnershipMoney);
		}
		return peterPaul;
	}

	/*
	 * Given an array of non-empty strings, return a Map<String, String> where, for every String in the array,
	 * there is an entry whose key is the first character of the string.
	 *
	 * The value of the entry is the last character of the String. If multiple Strings start with the same letter, then the
	 * value for that key should be the later String's last character.
	 *
	 * beginningAndEnding(["code", "bug"]) → {"b": "g", "c": "e"}
	 * beginningAndEnding(["code", "bug", "cat"]) → {"b": "g", "c": "t"}
	 * beginningAndEnding(["muddy", "good", "moat", "good", "night"]) → {"g": "d", "m": "t", "n": "t"}
	 */
	public Map<String, String> beginningAndEnding(String[] words) {
		Map<String, String> beginningAndEnding = new HashMap<String, String>();



		for (int i =0; i < words.length; i++) {

			String word = words[i];
			String key = word.substring(0,1);
			String value = word.substring(word.length() -1);
			//System.out.println("value is " + value);
			beginningAndEnding.put(key, value);
		}

		return beginningAndEnding;
	}

	/*
	 * Given an array of Strings, return a Map<String, Integer> with a key for each different String, with the value the
	 * number of times that String appears in the array.
	 *
	 * ** A CLASSIC **
	 *
	 * wordCount(["ba", "ba", "black", "sheep"]) → {"ba" : 2, "black": 1, "sheep": 1 }
	 * wordCount(["a", "b", "a", "c", "b"]) → {"b": 2, "c": 1, "a": 2}
	 * wordCount([]) → {}
	 * wordCount(["c", "b", "a"]) → {"b": 1, "c": 1, "a": 1}
	 *
	 */
	public Map<String, Integer> wordCount(String[] words) {
		Map<String, Integer>wordCount = new HashMap<String, Integer>();

		for(int i = 0; i < words.length; i++) {  				// loops through array
			String key = words[i];								// saves string from array[i] into key
			int counter = 0;									// initialize counter
			for(int j = 0; j < words.length; j++) {				// loop grabs words[i] and compares it to rest of array
				if(words[i].contentEquals(words[j])) {
					counter++;									// if .equals then we add 1 to the counter
				}
			}
			wordCount.put(key, counter);						// we return words[i] as the key and the counter as the value
		}
		return wordCount;
	}

	/*
	 * Given an array of int values, return a Map<Integer, Integer> with a key for each int, with the value the
	 * number of times that int appears in the array.
	 *
	 * ** The lesser known cousin of the the classic wordCount **
	 *
	 * intCount([1, 99, 63, 1, 55, 77, 63, 99, 63, 44]) → {1: 2, 44: 1, 55: 1, 63: 3, 77: 1, 99:2}
	 * intCount([107, 33, 107, 33, 33, 33, 106, 107]) → {33: 4, 106: 1, 107: 3}
	 * intCount([]) → {}
	 *
	 */
	public Map<Integer, Integer> integerCount(int[] ints) {
		Map<Integer, Integer>integerCount = new HashMap<Integer, Integer>();   //create <Integer, Integer> map

		for(int i = 0; i < ints.length; i++) {
			Integer key = ints[i];
			int counter = 0;
			for(int j = 0; j < ints.length; j++) {
				if(ints[i] == ints[j]) {
					counter++;
				}
			}
			integerCount.put(key, counter);
		}
		return integerCount;
	}

	/*
	 * Given an array of Strings, return a Map<String, Boolean> where each different String is a key and value
	 * is true only if that String appears 2 or more times in the array.
	 *
	 * wordMultiple(["a", "b", "a", "c", "b"]) → {"b": true, "c": false, "a": true}
	 * wordMultiple(["c", "b", "a"]) → {"b": false, "c": false, "a": false}
	 * wordMultiple(["c", "c", "c", "c"]) → {"c": true}
	 *
	 */
	public Map<String, Boolean> wordMultiple(String[] words) {
		Map<String, Boolean> wordMultiple = new HashMap<String, Boolean>();

		for(int i = 0; i < words.length; i++) {
			String key = words[i];
			boolean value = false;
			int counter = 0;

			for(int j = 0; j < words.length; j++) {
				if (words[i].equals(words[j])) {
					counter++;
				}
			}
			if(counter >= 2) {
				value = true;
			}
			wordMultiple.put(key, value);
		}
		return wordMultiple;
	}

	/*
	 * Given two Maps, Map<String, Integer>, merge the two into a new Map, Map<String, Integer> where keys in Map2,
	 * and their int values, are added to the int values of matching keys in Map1. Return the new Map.
	 *
	 * Unmatched keys and their int values in Map2 are simply added to Map1.
	 *
	 * consolidateInventory({"SKU1": 100, "SKU2": 53, "SKU3": 44} {"SKU2":11, "SKU4": 5})
	 * 	 → {"SKU1": 100, "SKU2": 64, "SKU3": 44, "SKU4": 5}
	 *
	 */
	public Map<String, Integer> consolidateInventory(Map<String, Integer> mainWarehouse,
			Map<String, Integer> remoteWarehouse) {
		Map<String, Integer> consolidateInventory = new HashMap<>();
		Set<String> keysMain = mainWarehouse.keySet();
		Set<String> keysRemote = remoteWarehouse.keySet();

		// Iterate through keysMain
		for (String main : keysMain) {
			Integer value = mainWarehouse.get(main);

			// Check if the key is present in remoteWarehouse
			if (keysRemote.contains(main)) {
				value += remoteWarehouse.get(main);
			}

			consolidateInventory.put(main, value);
		}

		// Iterate through keysRemote to add unmatched keys
		for (String remote : keysRemote) {
			if (!keysMain.contains(remote)) {
				consolidateInventory.put(remote, remoteWarehouse.get(remote));
			}
		}

		return consolidateInventory;
	}

	/*
	 * Just when you thought it was safe to get back in the water --- last2Revisited!!!!
	 *
	 * Given an array of Strings, for each String, the count of the number of times that a subString length 2 appears
	 * in the String and also as the last 2 chars of the String, so "hixxxhi" yields 1.
	 *
	 * We don't count the end subString, but the subString may overlap a prior position by one.  For instance, "xxxx"
	 * has a count of 2, one pair at position 0, and the second at position 1. The third pair at position 2 is the
	 * end subString, which we don't count.
	 *
	 * Return Map<String, Integer>, where the key is String from the array, and its last2 count.
	 *
	 * last2Revisited(["hixxhi", "xaxxaxaxx", "axxxaaxx"]) → {"hixxhi": 1, "xaxxaxaxx": 1, "axxxaaxx": 2}
	 *
	 */
	public Map<String, Integer> last2Revisited(String[] words) {
		Map<String, Integer>last2Revisited = new HashMap<String, Integer>();
		for (int i = 0; i < words.length; i++) {
			int counter = 0;                               //must be within the for loop to reset

			if(words[i].length() < 2) {						//if less than 2 then impossible return 0
				last2Revisited.put(words[i],0);
			}else {
				String end = words[i].substring(words[i].length() - 2);    // takes the last two of the string so that we can compare later
				//System.out.println("the words end is " + end);
				for(int j = 0; j < words[i].length() -2; j++) {			   // this loops through each string at words[i]
					if(words[i].substring(j, j + 2).equals(end)) {		   // determines whether or not to increase the counter
						counter++;
					}
				}
			}
			last2Revisited.put(words[i], counter);						    // add to map before restarting loop
		}

		return last2Revisited;
	}

}
