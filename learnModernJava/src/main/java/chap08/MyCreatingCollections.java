package chap08;

import static java.util.Map.entry;

import java.util.Map;

public class MyCreatingCollections {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    Map<String, Integer> ageOfFriends2 = Map.ofEntries(
	            entry("Raphael", 30),
	            entry("Olivia", 25),
	            entry("Thibaut", 26));
	}

}
