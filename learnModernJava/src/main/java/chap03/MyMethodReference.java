package chap03;

import java.util.Arrays;
import java.util.List;

public class MyMethodReference {

	public static void main(String[] args) {

		List<String> str = Arrays.asList("a", "B", "A", "B");
//		str.sort((s1, s2) -> s1.compareToIgnoreCase(s2));
		str.sort(String::compareToIgnoreCase);
		
		System.out.println(str);
	}

}
