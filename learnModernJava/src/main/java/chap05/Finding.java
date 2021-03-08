package chap05;

import java.util.Optional;

import chap04.Dish;
import myutil.MyUtil;

public class Finding {

	public static void main(String[] args) {
		
		
		Finding fd = new Finding();
		
		fd.p167_anyMatch();
	    
		fd.p167_allMatch();

		fd.p167_noneMatch();

		fd.p168_findAny();
	}

	/**
	 * anyMatch
	 * 하나의 요소라도  함수가 true 이면 true 를 리턴
	 */
	private void p167_anyMatch() {
		MyUtil.printMethodName();
		boolean rtn = Dish.menu.stream().anyMatch(Dish::isVegetarian);
		System.out.println(rtn + " : anyMatch isVegetarian");
	}

	/**
	 * allMatchb
	 * 모든 요소가 true 면 true. 즉 하나라도 거짓이면 거짓이다.
	 */
	private void p167_allMatch() {
		MyUtil.printMethodName();
		boolean rtn = Dish.menu.stream().allMatch(d -> d.getCalories() < 1000);
		
		System.out.println(rtn);
	}

	/**
	 * noneMatch
	 * 모두 fasle 여야 true 이다. 즉 하나라도 true 이면 false 이다.
	 * noneMatch 는 allMatch 와 반대 연산을 수행한다.
	 */
	private void p167_noneMatch() {
		MyUtil.printMethodName();
		boolean rtn = Dish.menu.stream().noneMatch(d -> d.getCalories() >= 1000);
		
		System.out.println(rtn);
	}

	/**
	 * findAny
	 * 현재 스트림의 임의의 요소를 반환한다.
	 * 
	 * Optional<T>
	 * isPresent()  
	 * ifPresent(Consumer<T> block)  ⇒ 값이 있으면 주어진 블록을 실행한다.
	 * T get() : 값이 존재하면 값을 반환, 없으면 NoSuchElementException
	 * T orElse(T other) : 값이 있으면 값을 반환, 값이 없으면 기본값을 반환.
	 * 
	 */
	private void p168_findAny() {
		MyUtil.printMethodName();

		Optional<Dish> dish = Dish.menu.stream().filter(Dish::isVegetarian).findAny();
		dish.ifPresent(d -> System.out.println(d.getName()));
	}
}
