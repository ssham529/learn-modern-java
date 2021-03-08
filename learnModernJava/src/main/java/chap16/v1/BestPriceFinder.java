package chap16.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import chap16.ExchangeService;
import chap16.ExchangeService.Money;

public class BestPriceFinder {

  private final List<Shop> shops = Arrays.asList(
      new Shop("BestPrice"),
      new Shop("LetsSaveBig"),
      new Shop("MyFavoriteShop"),
      new Shop("BuyItAll")/*,
      new Shop("ShopEasy")*/);

    // p513 예제 16-12 우리의 최저가격 검색 애플리케이션에 맞는 커스텀 Executor
	private final Executor executor = Executors.newFixedThreadPool(
			Math.min(shops.size(), 100) // 상점수 만큼의 스레드, 최대는 100
		  , new ThreadFactory() {
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r);
					t.setDaemon(true); // 데몬 스레드
					return t;
				}
			}
		);

  /**
   * p507 예제 16-8 모든 상점에 순차적으로 정보를요청하는 findPrices
   */
  public List<String> findPricesSequential(String product) {
	  return shops.stream()
			  .map(shop -> String.format("%s price is %.2f"
					  , shop.getName(), shop.getPrice(product)))
			  .collect(Collectors.toList());
  }
  
  /**
   * p508 예제 16-10 findPrices 메서드 병렬화
   */
  public List<String> findPricesParallel(String product) {
	  return shops.parallelStream()
			  .map(shop -> String.format("%s price is %.2f"
					  , shop.getName(), shop.getPrice(product)))
			  .collect(Collectors.toList());
  }


  /**
   * p510 예제 16-11 CompletableFuture로 findPrices 구현하기
   */
  public List<String> findPricesFuture(String product) {
    List<CompletableFuture<String>> priceFutures =
        shops.stream()
            .map(shop ->
            	// CompletableFuture로 각각의 가격을 비동기적으로 계산한다.
            	CompletableFuture.supplyAsync(
            			() -> shop.getName() + " price is " + shop.getPrice(product)
            			, executor))
            .collect(Collectors.toList());

    List<String> prices = priceFutures.stream()
    	// 모든 비동기 동작이 끝나길 기다린다.
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
    return prices;
  }
  
  	/**
  	 * p525 예제 16-19 CompletableFuture에 타임아웃 추가
  	 */
  	public void findPricesInUSD_p525(String product) {
  		
  		Shop shop = shops.get(0);
  		
  		Future<Double> futurePriceInUSD =
  			CompletableFuture.supplyAsync(() -> shop.getPrice(product))
  			.thenCombine(
  				CompletableFuture.supplyAsync(() -> ExchangeService.getRate(Money.EUR, Money.USD))
  				, (price, rate) -> price * rate
  			// 3초 뒤에 작업이 완료되지 않으면 Future 가 TimeoutException 을 발생시키도록 설ㅓㅇ.
  			// 자바 9에서는 비동기 타임아웃 관리 기능이 추가됨.
  			).orTimeout(3, TimeUnit.SECONDS);
  	}
  	
  	/**
  	 * p526 예제 16-20 CompletableFuture에 타임아웃이 발생하면 기본값으로 처리
  	 */
  	public void findPricesInUSD_p526(String product) {
  		
  		Shop shop = shops.get(0);
  		
  		Future<Double> futurePriceInUSD =
  			CompletableFuture.supplyAsync(() -> shop.getPrice(product))
  			.thenCombine(
  				CompletableFuture.supplyAsync(
  					() -> ExchangeService.getRate(Money.EUR, Money.USD)
  				).completeOnTimeout(ExchangeService.DEFAULT_RATE, 1, TimeUnit.SECONDS)
  				, (price, rate) -> price * rate
  			).orTimeout(3, TimeUnit.SECONDS);
  	}
  
  /**
   * p526 예제 16-20 CompletableFuture에 타임아웃이 발생하면 기본값으로 처리
   */
  public List<String> findPricesInUSD(String product) {
    List<CompletableFuture<Double>> priceFutures = new ArrayList<>();
    for (Shop shop : shops) {
      // 예제 10-20 시작.
      // 아래 CompletableFuture::join와 호환되도록 futurePriceInUSD의 형식만 CompletableFuture로 바꿈.
      CompletableFuture<Double> futurePriceInUSD =
          CompletableFuture.supplyAsync(() -> shop.getPrice(product))
          .thenCombine(
              CompletableFuture.supplyAsync(
                  () ->  ExchangeService.getRate(Money.EUR, Money.USD))
              // 자바 9에 추가된 타임아웃 관리 기능
              .completeOnTimeout(ExchangeService.DEFAULT_RATE, 1, TimeUnit.SECONDS),
              (price, rate) -> price * rate
          )
          // 자바 9에 추가된 타임아웃 관리 기능
          .orTimeout(3, TimeUnit.SECONDS);
      priceFutures.add(futurePriceInUSD);
    }
    // 단점: 루프 밖에서 shop에 접근할 수 없으므로 아래 getName() 호출을 주석처리함.
    // so the getName() call below has been commented out.
    List<String> prices = priceFutures.stream()
        .map(CompletableFuture::join)
        .map(price -> /*shop.getName() +*/ " price is " + price)
        .collect(Collectors.toList());
    return prices;
  }

  /**
   * p524 예제 16-18 자바 7로 두 Future 합치기  
   */
  public List<String> findPricesInUSDJava7(String product) {
    ExecutorService executor = Executors.newCachedThreadPool();
    List<Future<Double>> priceFutures = new ArrayList<>();
    for (Shop shop : shops) {
      final Future<Double> futureRate = executor.submit(new Callable<Double>() {
        @Override
        public Double call() {
          return ExchangeService.getRate(Money.EUR, Money.USD);
        }
      });
      Future<Double> futurePriceInUSD = executor.submit(new Callable<Double>() {
        @Override
        public Double call() {
          try {
            double priceInEUR = shop.getPrice(product);
            return priceInEUR * futureRate.get();
          }
          catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
          }
        }
      });
      priceFutures.add(futurePriceInUSD);
    }
    List<String> prices = new ArrayList<>();
    for (Future<Double> priceFuture : priceFutures) {
      try {
        prices.add(/*shop.getName() +*/ " price is " + priceFuture.get());
      }
      catch (ExecutionException | InterruptedException e) {
        e.printStackTrace();
      }
    }
    return prices;
  }

  /**
   * p524 예제 16-17 독립적인 두 개의 CompletableFuture 합치기
   */
  public List<String> findPricesInUSD2(String product) {
    List<CompletableFuture<String>> priceFutures = new ArrayList<>();
    for (Shop shop : shops) {
      // 루프에서 상점 이름에 접근할 수 있도록 동작을 추가함. 결과적으로 CompletableFuture<String> 인스턴스를 사용할 수 있음.
      CompletableFuture<String> futurePriceInUSD =
          CompletableFuture.supplyAsync(() -> shop.getPrice(product))
          .thenCombine(
              CompletableFuture.supplyAsync(
                  () -> ExchangeService.getRate(Money.EUR, Money.USD)),
              (price, rate) -> price * rate
          ).thenApply(price -> shop.getName() + " price is " + price);
      priceFutures.add(futurePriceInUSD);
    }
    List<String> prices = priceFutures
        .stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
    return prices;
  }

  
  public List<String> findPricesInUSD3(String product) {
    // 루프를 매핑 함수로 바꿈...
    Stream<CompletableFuture<String>> priceFuturesStream = shops.stream()
        .map(shop -> CompletableFuture
            .supplyAsync(() -> shop.getPrice(product))
            .thenCombine(
                CompletableFuture.supplyAsync(() -> ExchangeService.getRate(Money.EUR, Money.USD)),
                (price, rate) -> price * rate)
            .thenApply(price -> shop.getName() + " price is " + price));
    // 하지만 합치기 전에 연산이 실행되도록 CompletableFuture를 리스트로 모음
    List<CompletableFuture<String>> priceFutures = priceFuturesStream.collect(Collectors.toList());
    List<String> prices = priceFutures.stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toList());
    return prices;
  }

}
