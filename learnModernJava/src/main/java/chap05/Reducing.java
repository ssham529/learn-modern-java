package chap05;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import chap04.Dish;
import myutil.MyUtil;

public class Reducing {

	public static void main(String[] args) {

		final List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
		
		Reducing rd = new Reducing();

		rd.p170_sum(numbers);
		
		rd.p170_max(numbers);
		
		rd.p170_min(numbers);
		
		rd.calTotCalories();
	}

	/**
	 * reduce 요소의 합
	 */
	private void p170_sum(List<Integer> numbers) {
		MyUtil.printMethodName("reduce 요소의 합");
		
		int sum = numbers.stream().reduce(0, (a, b) -> a + b);
	    System.out.println(sum);
	    
	    int sum2 = numbers.stream().reduce(0, Integer::sum);
	    System.out.println(sum2);

	}

	/**
	 * reduce 최대값 구하기
	 */
	private void p170_max(List<Integer> numbers) {
		MyUtil.printMethodName("reduce 최대값 구하기");
		
	    int max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
	    System.out.println(max);
	}

	/**
	 * reduce 최소값 구하기
	 */
	private void p170_min(List<Integer> numbers) {
		MyUtil.printMethodName("reduce 최소값 구하기");
		
	    Optional<Integer> min = numbers.stream().reduce(Integer::min);
	    min.ifPresent(System.out::println);
	}

	/**
	 * Dish 의 Calories 총합 구하기.
	 */
	private void calTotCalories() {
		MyUtil.printMethodName("Dish 의 Calories 총합 구하기.");
		
		int calories = Dish.menu.stream()
			.map(Dish::getCalories)
			.reduce(0, Integer::sum);
		System.out.println("Number of calories:" + calories);
	}
}
