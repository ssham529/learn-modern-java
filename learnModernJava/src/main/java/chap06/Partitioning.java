package chap06;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import myutil.MyUtil;

public class Partitioning {

	/**
	 * p219_partitioningBy 
	 * 분할 함수
	 */
	private void p219_partitioningBy() {
		MyUtil.printMethodName();
		Map<Boolean, List<Dish>> partitionByVegeterian = 
			Dish.menu.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
		 
		System.out.println(partitionByVegeterian);
		
		List<Dish> vegetarianDishes = partitionByVegeterian.get(true);
		
		System.out.println(vegetarianDishes);
		
		// 2 번째 방법
		List<Dish> vegetarianDishes2 = 
			Dish.menu.stream()
				.filter(Dish::isVegetarian)
				.collect(Collectors.toList());
		
		System.out.println(vegetarianDishes2);
	}
	
	/**
	 * p220_partitioningBy_groupingBy
	 */
	private void p220_partitioningBy_groupingBy() {
		MyUtil.printMethodName();
		
		Map<Boolean, Map<Dish.Type, List<Dish>>> vegetarianDishesByType =
			Dish.menu.stream()
				.collect(Collectors.partitioningBy(Dish::isVegetarian
						, Collectors.groupingBy(Dish::getType)));
		
		System.out.println(vegetarianDishesByType);
	}
	
	/**
	 *
	 */
	private void p221_partitionaingBy_maxBy() {
		MyUtil.printMethodName();
		
		Map<Boolean, Dish> mostCaloricPartitionedByVegetarian =
		Dish.menu.stream().collect(
		    Collectors.partitioningBy(Dish::isVegetarian,
		    	Collectors.collectingAndThen(
		    			Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
		        				Optional::get)));
		
		System.out.println(mostCaloricPartitionedByVegetarian);
		
	}
	
	/**
	 *
	 */
	private void p_() {
		MyUtil.printMethodName();
		
	}
	
	public static void main(String[] args) {
		Partitioning pt = new Partitioning();
		
		pt.p219_partitioningBy();
		pt.p220_partitioningBy_groupingBy();
		pt.p221_partitionaingBy_maxBy();
		
	}

}
