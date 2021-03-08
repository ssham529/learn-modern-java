package chap06;


import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import myutil.MyUtil;

public class Grouping {

	enum CaloricLevel { DIET, NORMAL, FAT };
	  
	/**
	 * p210_groupingBy
	 */
	private void p210_groupingBy() {
		MyUtil.printMethodName();
		
		Map<Dish.Type, List<Dish>> dishesByType = 
				Dish.menu.stream().collect(Collectors.groupingBy(Dish::getType));
		
		System.out.println(dishesByType);
	}
	
	/**
	 * p211_dishesByCaloricLevel
	 */
	private void p211_dishesByCaloricLevel() {
		MyUtil.printMethodName();
		
		Map<CaloricLevel, List<Dish>> dishesByCaloricLevel =
			Dish.menu.stream().collect(
				Collectors.groupingBy( dish -> {
					if(dish.getCalories() <= 400) {
						return CaloricLevel.DIET;
					} else if (dish.getCalories() <= 700) {
						return CaloricLevel.NORMAL;
					} else {
						return CaloricLevel.FAT;
					}
			}));
		
		System.out.println(dishesByCaloricLevel);
	}
	
	/**
	 * p212_필터링이_먼저_되면_FISH_키가_없어진_상태로_그룹화_되는_문제
	 */
	private void p212_필터링이_먼저_되면_FISH_키가_없어진_상태로_그룹화_되는_문제() {
		MyUtil.printMethodName();
		
		Map<Dish.Type, List<Dish>> caloricDishesByType =
			Dish.menu.stream().filter(dish -> dish.getCalories() > 500)
							  .collect(Collectors.groupingBy(Dish::getType));
		
		System.out.println(caloricDishesByType);
		
		
		// groupingBy 에 인자로 filtering 
		Map<Dish.Type, List<Dish>> caloricDishesByType2 =
			Dish.menu.stream()
				.collect(Collectors.groupingBy(Dish::getType
											 , Collectors.filtering(dish -> dish.getCalories() > 500
													 				, Collectors.toList())
											   )
						);
		
		System.out.println(caloricDishesByType2);
	}
	
	/**
	 * p212_Collectors_mapping 
	 */
	private void p212_Collectors_mapping() {
		MyUtil.printMethodName();
		
		Map<Dish.Type, List<String>> dishNamesByType =
			Dish.menu.stream()
				.collect(Collectors.groupingBy(Dish::getType
											 , Collectors.mapping(Dish::getName , Collectors.toList())
											  )
						);
		
		System.out.println(dishNamesByType);
	}
	
	/**
	 * p213_Collectors_flatMapping
	 */
	private void p213_Collectors_flatMapping() {
		MyUtil.printMethodName();
		
		Map<Dish.Type, Set<String>> dishNamesByType =
			Dish.menu.stream()
				.collect(Collectors.groupingBy(Dish::getType 
											 , Collectors.flatMapping(dish -> Dish.dishTags.get(dish.getName()).stream()
													                 , Collectors.toSet()
													                 )
											 )
						);
		System.out.println(dishNamesByType);
	}
	
	/**
	 * p213_예제6_2_다수준_그룹화
	 */
	private void p213_예제6_2_다수준_그룹화() {
		MyUtil.printMethodName();
		
		Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = Dish.menu.stream()
				.collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy(dish -> {
					if (dish.getCalories() <= 400) {
						return CaloricLevel.DIET;
					} else if (dish.getCalories() <= 700) {
						return CaloricLevel.NORMAL;
					} else {
						return CaloricLevel.FAT;
					}
				})));
		
		System.out.println(dishesByTypeCaloricLevel);
	}
	
	/**
	 * p215_groupingBy_counting
	 */
	private void p215_groupingBy_counting() {
		MyUtil.printMethodName();
		
		Map<Dish.Type, Long> typescount =
			Dish.menu.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
		
		System.out.println(typescount);
	}
	
	/**
	 * p216_groupingBy_maxBy
	 */
	private void p216_groupingBy_maxBy() {
		MyUtil.printMethodName();

		Map<Dish.Type, Optional<Dish>> mostCaloricByType =
			Dish.menu.stream().collect(
				Collectors.groupingBy(Dish::getType
						, Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
		
		System.out.println(mostCaloricByType);
	}
	
	/**
	 * p216_예제6_2_collectingAndThen
	 */
	private void p216_예제6_2_collectingAndThen() {
		MyUtil.printMethodName();
		
		Map<Dish.Type, Dish> mostCaloricByType =
			Dish.menu.stream()
				.collect(Collectors.groupingBy(Dish::getType 
									, Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))
											                     , Optional::get
											                       )
									           )
						);
		System.out.println(mostCaloricByType);
	}
	
	/**
	 * p218_groupingBy_summingInt
	 */
	private void p218_groupingBy_summingInt() {
		MyUtil.printMethodName();
		Map<Dish.Type, Integer> sumCaloriesByType = 
			Dish.menu.stream()
				.collect(Collectors.groupingBy(Dish::getType
						                      , Collectors.summingInt(Dish::getCalories)
						                      )
						);
		
		System.out.println(sumCaloriesByType);
	}

	/**
	 * p219_groupingBy_mapping
	 */
	private void p219_groupingBy_mapping() {
		MyUtil.printMethodName();
		
		Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
		Dish.menu.stream().collect(
		        Collectors.groupingBy(Dish::getType, Collectors.mapping(
		            dish -> {
		              if (dish.getCalories() <= 400) {
		                return CaloricLevel.DIET;
		              }
		              else if (dish.getCalories() <= 700) {
		                return CaloricLevel.NORMAL;
		              }
		              else {
		                return CaloricLevel.FAT;
		              }
		            },
		            Collectors.toSet()
		        ))
		    );
		
		System.out.println(caloricLevelsByType);
	}
	
	/**
	 * p219_groupingBy_toCollection
	 */
	private void p219_groupingBy_toCollection() {
		MyUtil.printMethodName();
		
		Map<Dish.Type, Set<CaloricLevel>> caloricLevelsByType =
		Dish.menu.stream().collect(
				Collectors.groupingBy(Dish::getType
						, Collectors.mapping((Dish dish) -> {
							if (dish.getCalories() <= 400) {
								return CaloricLevel.DIET;
							} else if(dish.getCalories() <= 700) {
								return CaloricLevel.NORMAL;
							} else {  
								return CaloricLevel.FAT;
							}
						}, Collectors.toCollection(HashSet::new))));
		
		System.out.println(caloricLevelsByType);
	}
	
	public static void main(String[] args) {
		Grouping gp = new Grouping();
		
		gp.p210_groupingBy();
		gp.p211_dishesByCaloricLevel();
		gp.p212_필터링이_먼저_되면_FISH_키가_없어진_상태로_그룹화_되는_문제();
		gp.p212_Collectors_mapping();
		gp.p213_Collectors_flatMapping();
		gp.p213_예제6_2_다수준_그룹화();
		gp.p215_groupingBy_counting();
		gp.p216_groupingBy_maxBy();
		gp.p216_예제6_2_collectingAndThen();
		gp.p218_groupingBy_summingInt();
		gp.p219_groupingBy_mapping();
		gp.p219_groupingBy_toCollection();
	}
}
