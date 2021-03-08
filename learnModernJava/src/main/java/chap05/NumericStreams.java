package chap05;

import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import chap04.Dish;
import myutil.MyUtil;

/**
 * packageName : chap05 fileName : NumericStreams.java
 * 
 * @author : HSS date : 2020.09.29 description : p181 5.7.1 기본형 특화 스트림
 *         =========================================================== DATE
 *         AUTHOR NOTE
 *         -----------------------------------------------------------
 *         2020.09.29 HSS 최초 생성
 **/
public class NumericStreams {

	public static void main(String[] args) {
		
		NumericStreams ns = new NumericStreams();
		
		ns.p183_OptionalInt();
		
		ns.p186_pythagoreanTriples();

		ns.p187_pythagoreanTriples2();
	}

	/**
	 * 기본값 : OptionalInt
	 */
	private void p183_OptionalInt() {
		MyUtil.printMethodName("기본값 : OptionalInt");

		OptionalInt maxCalories = Dish.menu.stream().mapToInt(Dish::getCalories).max();

		int max;
//	    if (maxCalories.isPresent()) {
//	      max = maxCalories.getAsInt();
//	    }
//	    else {
//	      // 기본값을 선택할 수 있음
//	      max = 1;
//	    }

		max = maxCalories.orElse(1);
		System.out.println(max);
	}

	/**
	 * p184 5.7.3 숫자 스트림 활용 : 피타고라스 수
	 */
	public void p186_pythagoreanTriples() {
		MyUtil.printMethodName();
		
		Stream<int[]> pythagoreanTriples =
				IntStream.rangeClosed(1, 100).boxed()
				.flatMap(a -> 
					IntStream.rangeClosed(a, 100)
						.filter(b -> Math.sqrt(a*a + b*b) % 1 == 0)
						.mapToObj(b ->
								new int[] {a, b, (int)Math.sqrt(a*a + b*b)})
				);
		
		pythagoreanTriples.limit(5)
			.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
	}

	/**
	 * p187 제곱근을 두 번 계산하는 문제 해결. 
	 */
	public void p187_pythagoreanTriples2() {
		MyUtil.printMethodName();
		
		Stream<double[]> pythagoreanTriples2 =
				IntStream.rangeClosed(1, 100).boxed()
					.flatMap(a -> IntStream.rangeClosed(a, 100)
										.mapToObj( b -> 
												new double[] {a, b, Math.sqrt(a*a + b*b) } )
										.filter(t -> t[2] % 1 == 0 )
							);
		
		pythagoreanTriples2.limit(5)
		.forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));
	}
}
