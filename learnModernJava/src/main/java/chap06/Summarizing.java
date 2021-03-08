package chap06;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.stream.Collectors;

import myutil.MyUtil;

/**
 * packageName : chap06
 * fileName    : Summarizing.java
 * @author     : HSS
 * date        : 2020.10.04
 * description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.10.04        HSS          최초 생성
 **/
public class Summarizing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Summarizing sm = new Summarizing();
		
		sm.p202_max();
	
		sm.p203_summintInt();
		
		sm.p204_averagingInt();
		
		sm.p204_summarizingInt();
		
		sm.p203_joining();
	}
	
	/**
	 * Collectors.maxBy
	 */
	private void p202_max() {
		MyUtil.printMethodName("Collectors.maxBy");
		
		Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
		
		Optional<Dish> mostCalorieDish = Dish.menu.stream().collect(Collectors.maxBy(dishCaloriesComparator));
		
		System.out.println(mostCalorieDish.get());
	}

	/**
	 * summintInt
	 */
	private void p203_summintInt() {
		MyUtil.printMethodName();
		
		int totalCalories = Dish.menu.stream().collect(Collectors.summingInt(Dish::getCalories));
		
		System.out.println(totalCalories);
	}
	
	/**
	 * p204_averagingInt
	 */
	private void p204_averagingInt() {
		MyUtil.printMethodName();
		
		double avgCalories = Dish.menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
		
		System.out.println(avgCalories);
	}
	
	/**
	 * p204_summarizingInt
	 */
	private void p204_summarizingInt() {
		MyUtil.printMethodName();
		
		IntSummaryStatistics menuStatistics = 
				Dish.menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
		
		System.out.println(menuStatistics);
	}
	
	/**
	 * p203_joining
	 */
	private void p203_joining() {
		MyUtil.printMethodName();
		
		String shortMenu = Dish.menu.stream().map(Dish::getName).collect(Collectors.joining());
		System.out.println(shortMenu);
		
		String shortMenu2 = Dish.menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
		System.out.println(shortMenu2);
	}
	
}
