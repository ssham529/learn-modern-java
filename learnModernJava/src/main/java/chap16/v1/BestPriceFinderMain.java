package chap16.v1;

import java.util.List;
import java.util.function.Supplier;

public class BestPriceFinderMain {

  private static BestPriceFinder bestPriceFinder = new BestPriceFinder();

  public static void main(String[] args) {
	  
//	  execute("예제 16-8", () -> bestPriceFinder.findPricesSequential("myPhone27S") );
//	  execute("예제 16-10", () -> bestPriceFinder.findPricesParallel("myPhone27S") );
	  execute("예제 16-11", () -> bestPriceFinder.findPricesFuture("myPhone27S") );
	  
	  
//    execute("sequential", () -> bestPriceFinder.findPricesSequential("myPhone27S"));
//    execute("parallel", () -> bestPriceFinder.findPricesParallel("myPhone27S"));
//    execute("composed CompletableFuture", () -> bestPriceFinder.findPricesFuture("myPhone27S"));
//    execute("combined USD CompletableFuture", () -> bestPriceFinder.findPricesInUSD("myPhone27S"));
//    execute("combined USD CompletableFuture v2", () -> bestPriceFinder.findPricesInUSD2("myPhone27S"));
//    execute("combined USD CompletableFuture v3", () -> bestPriceFinder.findPricesInUSD3("myPhone27S"));
  }

  /**
   * p508 예제 16-9 findPrices의 결과와 성능 확인
   */
  private static void execute(String msg, Supplier<List<String>> s) {
    long start = System.nanoTime();
    System.out.println(s.get());
    long duration = (System.nanoTime() - start) / 1_000_000;
    System.out.println(msg + " done in " + duration + " ms");
  }

}
