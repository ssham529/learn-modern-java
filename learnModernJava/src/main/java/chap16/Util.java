package chap16;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Util {

	private static final Random RANDOM = new Random(0);
	private static final DecimalFormat formatter = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));

	private static final Random randomDelay = new Random();

	/**
	 * p527 예제 16-21 0.5초에서 2.5초 사이의 임의의 지연을 흉내 내는 메서드
	 */
	public static void randomDelay() {
		int delay = 500 + randomDelay.nextInt(2000);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static void delay() {
		int delay = 1000;
//		 int delay = 500 + RANDOM.nextInt(2000);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public static double format(double number) {
		synchronized (formatter) {
			return new Double(formatter.format(number));
		}
	}

	public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
		/*
		 * CompletableFuture<Void> allDoneFuture =
		 * CompletableFuture.allOf(futures.toArray(new
		 * CompletableFuture[futures.size()])); return allDoneFuture.thenApply(v ->
		 * futures.stream() .map(future -> future.join())
		 * .collect(Collectors.<T>toList()) );
		 */
		return CompletableFuture
				.supplyAsync(() -> futures.stream().map(future -> future.join()).collect(Collectors.<T>toList()));
	}

}
