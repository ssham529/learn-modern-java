package chap06;

import java.util.Optional;
import java.util.stream.Collectors;

import myutil.MyUtil;

public class Reducing {

	public static void main(String[] args) {

		Reducing rd = new Reducing();
		
		rd.p205_reducing_sum();
		
		rd.p206_reducing_max();
		
		rd.p207_reducing_sum_WithMethodReference();
		
		rd.p208_sum_WithoutCollectors();
		
		rd.p208_sum_UsingSum();
	}
	
	/**
	 *  p205_reducing_sum
	 */
	private void p205_reducing_sum() {
		MyUtil.printMethodName();
		
		int totalCal = Dish.menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i+j));
		
		System.out.println(totalCal);
	}

	/**
	 * p206_reducing_max
	 */
	private void p206_reducing_max() {
		MyUtil.printMethodName();
		
		Optional<Dish> mostCal = Dish.menu.stream().collect(Collectors.reducing(
				(d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2 ));
		
		System.out.println(mostCal.get());
	}
	
	/**
	 * p207_reducing_sum_WithMethodReference
	 */
	private void p207_reducing_sum_WithMethodReference() {
		MyUtil.printMethodName();
		
		int totalCal = Dish.menu.stream().collect(Collectors.reducing(0, Dish::getCalories, Integer::sum));
		
		System.out.println(totalCal);
	}

	/**
	 * p208_sum_WithoutCollectors
	 */
	private void p208_sum_WithoutCollectors() {
		MyUtil.printMethodName();
		int totalCal = Dish.menu.stream().map(Dish::getCalories).reduce(Integer::sum).get();
		
		System.out.println(totalCal);
	}
	
	/**
	 * p208_sum_UsingSum
	 */
	private void p208_sum_UsingSum() {
		MyUtil.printMethodName();
		
		int totalCal = Dish.menu.stream().mapToInt(Dish::getCalories).sum();
		System.out.println(totalCal);
	}

}
