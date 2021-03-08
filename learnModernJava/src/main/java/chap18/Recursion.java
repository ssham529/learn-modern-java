package chap18;

import java.util.stream.LongStream;

public class Recursion {

	public static void main(String[] args) {
		System.out.println(factorialIterative(5));
		System.out.println(factorialRecursive(5));
		System.out.println(factorialStreams(5));
		System.out.println(factorialTailRecursive(5));
	}

	/*
	 * p580 예제 18-2 재귀 방식의 팩토리얼
	 */
	public static int factorialIterative(int n) {
		int r = 1;
		for (int i = 1; i <= n; i++) {
			r *= i;
		}
		return r;
	}

	/*
	 * p580 예제 18-2 재귀 방식의 팩토리얼
	 */
	public static long factorialRecursive(long n) {
		return n == 1 ? 1 : n * factorialRecursive(n - 1);
	}
	
	/*
	 * p581
	 */
	public static long factorialStreams(long n) {
		return LongStream.rangeClosed(1, n)
				.reduce(1, (long a, long b) -> a * b);
	}
	
	/*
	 * p581 예제 18-4 꼬리 재귀 팩토리얼
	 */
	public static long factorialTailRecursive(long n) {
		return factorialHelper(1, n);
	}
	public static long factorialHelper(long acc, long n) {
		return n == 1 ? acc : factorialHelper(acc * n, n - 1);
	}

}
