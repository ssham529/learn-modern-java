package chap16;

import static chap16.Util.delay;
import static chap16.Util.format;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class Shop {

	private final String name;
	private final Random random;

	public Shop(String name) {
		this.name = name;
		random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
	}

	public String getPrice(String product) {
		double price = calculatePrice(product);
		Discount.Code code = Discount.Code.values()[random.nextInt(Discount.Code.values().length)];
		return name + ":" + price + ":" + code;
	}

	public double calculatePrice(String product) {
		delay();
		return format(random.nextDouble() * product.charAt(0) + product.charAt(1));
	}

	public String getName() {
		return name;
	}

	/**
	 * p503 예제 16-4 getPriceAsync 메서드 구현
	 */
	public Future<Double> getPriceAsync2(String product) {

		// 계산 결과를 포함할 CompletableFuture
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();

		new Thread(() -> {
			// 다른 스레드에서 비동기적으로 계산을 수행한다.
			double price = calculatePrice(product);
			// 오랜 시간이 걸리는 계산이 완료되면 Future에 값을 설정한다.
			futurePrice.complete(price);
		}).start();

		// 계산 결과가 완료되길 기다리지 않고 Future를 반환한다.
		return futurePrice;
	}
	
	/**
	 * p505 예제 16-6 CompletableFuture 내부에서 발생한 에러 전파
	 */
	public Future<Double> getPriceAsync(String product) {
		
		CompletableFuture<Double> futurePrice = new CompletableFuture<>();
		new Thread(() -> {
			try {
				double price = calculatePrice(product);
				
				// 계산이 정상적으로 종료되면 Future에 가격 정보를 저장한채로 Future를 종료한다.
				futurePrice.complete(price);
				
			} catch (Exception ex) {
				// 도중에 문제가 발생하면 발생한 에러를 포함시켜 Future를 종료한다.
				futurePrice.completeExceptionally(ex);
			}

		}).start();
		return futurePrice;
	}
	
	/**
	 * p506 예제 16-7 팩토리 메서드 supplyAsync로 CompletableFuture 만들기
	 */
	public Future<Double> getPriceAsync3(String product) {
		return CompletableFuture.supplyAsync(() -> calculatePrice(product));
	}

}
