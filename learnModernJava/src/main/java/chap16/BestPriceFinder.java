package chap16;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import chap16.ExchangeService.Money;

public class BestPriceFinder {

	private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"), new Shop("LetsSaveBig"),
			new Shop("MyFavoriteShop"), new Shop("BuyItAll"), new Shop("ShopEasy"));

	private final Executor executor = Executors.newFixedThreadPool(shops.size(), (Runnable r) -> {
		Thread t = new Thread(r);
		t.setDaemon(true);
		return t;
	});

	/**
	 * p518 예제 16-15 Discount 서비스를 이용하는 가장 간단한 findPrices 구현
	 */
	public List<String> findPricesSequential(String product) {
		return shops.stream()
				// 각 상점에서 할인 전 가격 얻기
				.map(shop -> shop.getPrice(product)) // name + ":" + price + ":" + code
				// 상점에서 반환한 문자열을 Quote 객체로 변환한다.
				.map(Quote::parse)
				// Discount 서비스를 이용해서 각 Quote에 할인을 적용한아
				.map(Discount::applyDiscount) // name + " price is " + discount price
				.collect(Collectors.toList());
	}

	public List<String> findPricesParallel(String product) {
		return shops.parallelStream().map(shop -> shop.getPrice(product)).map(Quote::parse).map(Discount::applyDiscount)
				.collect(Collectors.toList());
	}

	/**
	 * p519 예제 16-16 CompletableFuture로 findPrices 메서드 구현하기
	 */
	public List<String> findPricesFuture(String product) {

		List<CompletableFuture<String>> priceFutures = shops.stream()

				// 1.변환의 결과는 Stream<CompletableFuture<String>> 이다.
				.map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor))
				// 2. CompletableFuture<Stiring> 을 CompletableFuture<Quote>로 변환
				.map(future -> future.thenApply(Quote::parse))
				// 3. quote 는 첫번째 CompletableFutre의 결과물을 람다의 인자로 전달 받은 것이다
				// 두 번째 CompletableFuture 는 첫 번째 CompletableFuture의 결과를 계산의 입력으로 사용한다.
				.map(future -> future.thenCompose(
						quote -> CompletableFuture.supplyAsync(() -> Discount.applyDiscount(quote), executor)))

				.collect(Collectors.toList());

		List<String> prices = priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());

		return prices;
	}

	public List<String> findPricesFuture2(String product) {
		List<CompletableFuture<String>> priceFutures = findPricesStream(product)
				.collect(Collectors.<CompletableFuture<String>>toList());

		return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
	}

	/**
	 * p527 예제 16-22 Future 스트림을 반환하도록 findPrices 메서드 리팩터링하기
	 */
	public Stream<CompletableFuture<String>> findPricesStream(String product) {
		return shops.stream()
				.map(shop -> CompletableFuture.supplyAsync(
					() -> shop.getPrice(product), executor))
				.map(future -> future.thenApply(Quote::parse))
				.map(future -> future.thenCompose(
					quote -> CompletableFuture.supplyAsync(
						() -> Discount.applyDiscount(quote), executor)));
	}

	
	/**
	 * p529 예제 16-23 CompletableFuture 종료에 반응하기
	 */
	public void printPricesStream_p529(String product) {
		CompletableFuture[] futures = 
			findPricesStream(product)
			// Stream<CompletableFuture<Void>>
			.map(f -> f.thenAccept(System.out::println)) 
			// 가장 느린 상점에서 응답 받고 출력 기회를 제공 하고 싶은 경우.
			// 배열 초기화 : String[] str = new String[5]; 
			// CompletableFuture[] 로 변환되어 return
			.toArray(size -> new CompletableFuture[size]);
		
		// allOf 는 CompletableFuture 배열을 받아서 CompletableFuture<Void> 를 반환한다.
		CompletableFuture.allOf(futures).join();
	}
	
	/**
	 * p529 예제 16-23 CompletableFuture 종료에 반응하기
	 */
	public void printPricesStream_p529_2(String product) {
		long start = System.nanoTime();
		
		CompletableFuture[] futures = 
			findPricesStream(product)
			.map(f -> f.thenAccept(
					s -> System.out.println(s + " (done in " + ((System.nanoTime() - start) / 1_000_000) + " ms)")))
			.toArray(size -> new CompletableFuture[size]);
		
		CompletableFuture.allOf(futures).join();
		System.out.println("All shops have now responded in " + ((System.nanoTime() - start) / 1_000_000) + " ms");
	}

}
