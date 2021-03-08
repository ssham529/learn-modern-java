package chap03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyFunction {

	interface Function<T, R> {
		R apply(T t);
	}
	
	public static <T, R> List<R> map(List<T> list, Function<T, R> f) {
		List<R> result = new ArrayList<>();
		for(T t : list) {
			result.add(f.apply(t));
		}
		return result;
	}
	
	public static void main(String[] args) {

		List<String> list = Arrays.asList("lambdas", "in", "action");
		
		List<Integer> l = map(list, (String s) -> s.length() );
		
		System.out.println(l);
	}

}
