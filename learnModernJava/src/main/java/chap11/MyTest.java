package chap11;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyTest {

	public static void main(String[] args) {

		p379_1();
		
	}

	/**
	 * 비어 있는 Optional 을 stream 하면 걸러진다.
	 */
	public static void p379_1() {
		
		Optional<String> test = Optional.empty();
		Stream<String> test2 = test.stream();
		
		List<String> test3 = test2.collect(Collectors.toList());
		
		// 결과 : 0
		System.out.println(test3.size());
	}

}
