package chap05;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;

import chap04.Dish;
import myutil.MyUtil;

/**
 * packageName : chap05
 * fileName    : Filtering.java
 * @author     : HSS
 * date        : 2020.09.24
 * description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.09.24        HSS          최초 생성
 */
public class Filtering {

	public static void main(String[] args) {

		Filtering ft = new Filtering();
		
	    List<Dish> specialMenu = Arrays.asList(
            new Dish("season fruit", true, 120, Dish.Type.OTHER),
            new Dish("prawns", false, 300, Dish.Type.FISH),
            new Dish("rice", true, 350, Dish.Type.OTHER),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 530, Dish.Type.OTHER));
	    
	    
	    ft.p159_1(specialMenu);
	    
	    ft.p159_2(specialMenu);
	    
	    ft.p159_3();
	    
	    ft.p160(specialMenu);
	    
	}

	/**
	 * takeWhile 테스트
	 * @param specialMenu
	 */
	public void p159_1(List<Dish> specialMenu) {
		MyUtil.printMethodName("takeWhile 테스트");
		
		List<Dish> slicedMenu1 = specialMenu.stream()
			.takeWhile(dish -> dish.getCalories() < 320)
			.collect(toList());
		
		slicedMenu1.forEach(System.out::println);
	}
	
	/**
	 * dropWhile 테스트
	 * @param specialMenu
	 */
	public void p159_2(List<Dish> specialMenu) {
		MyUtil.printMethodName("dropWhile 테스트");
		
	    List<Dish> slicedMenu2 = specialMenu.stream()
            .dropWhile(dish -> dish.getCalories() < 320)
            .collect(toList());
	    
	        slicedMenu2.forEach(System.out::println);
	}

	/**
	 * limit(n)
	 */
	public void p159_3() {
		MyUtil.printMethodName("limit(n)");
		
		Dish.menu.forEach(d -> {
			System.out.println(d.getName() + " : " +d.getCalories());
		});
				
		
	    List<Dish> dishesLimit = Dish.menu.stream()
            .filter(d -> d.getCalories() > 500)
            .limit(3)
            .collect(toList());
        
	    System.out.println("-------------");
	    
	    dishesLimit.forEach(System.out::println);
		
	    System.out.println("------- limit 을 먼저 한 후 filter 를 해 보았다 ------");
	    List<Dish> dishesLimit2 = Dish.menu.stream()
    		.limit(3)
            .filter(d -> d.getCalories() > 500)
            .collect(toList());
	    
	    dishesLimit2.forEach(System.out::println);
	}

	/**
	 * skip(n)
	 * @param specialMenu
	 */
	public void p160(List<Dish> specialMenu) {
		MyUtil.printMethodName("");
		
		List<Dish> dish = specialMenu.stream()
			.limit(3)
			.skip(1)
			.collect(toList());
		
		dish.forEach(System.out::println);
		
	}
}
