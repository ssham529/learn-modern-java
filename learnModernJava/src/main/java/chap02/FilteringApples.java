package chap02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class FilteringApples {

	public static void main(String[] args) {

		List<Apple> inventory = Arrays.asList(new Apple(80, Color.GREEN), new Apple(155, Color.GREEN),
				new Apple(120, Color.RED));

		// TODO Auto-generated method stub
		// [Apple{color=GREEN, weight=80}, Apple{color=GREEN, weight=155}]
		List<Apple> greenApples2 = filter(inventory, new AppleColorPredicate());
		System.out.println(greenApples2);

		// [Apple{color=GREEN, weight=155}]
		List<Apple> heavyApples = filter(inventory, new AppleWeightPredicate());
		System.out.println(heavyApples);

		// []
		List<Apple> redAndHeavyApples = filter(inventory, new AppleRedAndHeavyPredicate());
		System.out.println(redAndHeavyApples);
	}

	interface ApplePredicate {

		boolean test(Apple a);

	}

	public static List<Apple> filter(List<Apple> inventory, ApplePredicate p) {
		List<Apple> result = new ArrayList<>();
		for (Apple apple : inventory) {
			if (p.test(apple)) {
				result.add(apple);
			}
		}
		return result;
	}

	enum Color {
		RED, GREEN
	}

	static class AppleWeightPredicate implements ApplePredicate {

		@Override
		public boolean test(Apple apple) {
			return apple.getWeight() > 150;
		}

	}

	static class AppleColorPredicate implements ApplePredicate {

		@Override
		public boolean test(Apple apple) {
			return apple.getColor() == Color.GREEN;
		}

	}

	static class AppleRedAndHeavyPredicate implements ApplePredicate {

		@Override
		public boolean test(Apple apple) {
			return apple.getColor() == Color.RED && apple.getWeight() > 150;
		}

	}

	public static class Apple {

		private int weight = 0;
		private Color color;

		public Apple(int weight, Color color) {
			this.weight = weight;
			this.color = color;
		}

		public int getWeight() {
			return weight;
		}

		public void setWeight(int weight) {
			this.weight = weight;
		}

		public Color getColor() {
			return color;
		}

		public void setColor(Color color) {
			this.color = color;
		}

		@SuppressWarnings("boxing")
		@Override
		public String toString() {
			return String.format("Apple{color=%s, weight=%d}", color, weight);
		}

	}
}
