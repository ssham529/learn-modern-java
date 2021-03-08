package chap03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyPredicate {

	interface Predicate<T> {
		boolean test(T t);
	}
	
	public static <T> List<T> filter(List<T> list, Predicate<T> p) {
		List<T> results = new ArrayList<>();
		for(T t: list) {
			if(p.test(t)) {
				results.add(t);
			}
		}
		return results;
	}
	
	
	public static void main(String[] args) {
		
		List<String> listOfStrings = Arrays.asList("a", "", "b", "c");
		
		Predicate<String> nonEmptyStringPredicate = (String s) -> !s.isEmpty();
		List<String> nonEmpty = filter(listOfStrings, nonEmptyStringPredicate);
		
		System.out.println(nonEmpty);
	}
	

}
