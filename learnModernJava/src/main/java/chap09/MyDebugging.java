package chap09;

import java.util.Arrays;
import java.util.List;

/**
 * packageName : chap09
 * fileName    : MyDebugging.java
 * @author     : HSS
 * date        : 2020.11.29
 * description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.11.29        HSS          최초 생성
 **/
public class MyDebugging {

	public static void main(String[] args) {

		// null 을 추가하여 일부로 에러 일으킴.
		List<Point> points = Arrays.asList(new Point(12, 2), null);
		points.stream().map(p -> p.getX()).forEach(System.out::println);
	}

	private static class Point {

		private int x;
		private int y;

		private Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

	}

}
