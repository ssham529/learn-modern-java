package chap04.mytest;

import static chap04.Dish.menu;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import chap04.Dish;
import myutil.MyUtil;

public class MyTestChap04 {

	public static void main(String[] args) {

		MyTestChap04 mt = new MyTestChap04();
		
		mt.p142();
		mt.p146();
		mt.p151();
	}

	public void p142() {
		MyUtil.printMethodName();
		
		List<String> threeHighCaloricDishNames =
			menu.stream()
			.filter(dish -> dish.getCalories() > 300)
			.map(Dish::getName)
			.limit(3)
			.collect(toList());
		
		System.out.println(threeHighCaloricDishNames);
	}
	
	public void p146() {
		MyUtil.printMethodName();
		
		try {
			List<String> title = Arrays.asList("Java8", "In", "Action");
			Stream<String> s = title.stream();
			s.forEach(System.out::println);
			s.forEach(System.out::println);		
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void p151() {
		
		MyUtil.printMethodName("중간 연산의 게으른 연산을 확인해보자");
		
		List<String> names =
			menu.stream()
			.filter(dish -> {
				System.out.println("filtering : " + dish.getName());
				return dish.getCalories() > 300;
			})
			.map(dish -> {
				System.out.println("mapping : " + dish.getName());
				return dish.getName();
			})
			.limit(3)
			.collect(toList());
		
		System.out.println(names);
	}
	
}
