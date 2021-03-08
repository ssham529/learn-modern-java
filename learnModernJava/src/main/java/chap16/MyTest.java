package chap16;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * packageName : chap16
 * fileName    : MyTest.java
 * @author     : HSS
 * date        : 2021.12.31
 * description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2021.12.31        HSS          최초 생성
 **/
public class MyTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		p503_1();
	}

	/**
	 * p498 예제 16-1 Future로 오래 걸리는 작업을 비동기적으로 실행하기
	 */
	private static void p498_1() {
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Future<Double> future = executor.submit(new Callable<Double>() {
			public Double call() {
				// 시간이 오래 걸리는 작업은 다른 스레드에서 비동기적으로 실행한다.
				return doSomeLongComputation();
			}
		});
		// 비동기 작업을 수행하는 동안 다른 작업을 한다.
		doSomethingElse();
		
		try {
			// 비동기 작업의 결과를 가져온다. 결과가 준비되어 있지 않으면 호출 스레드가 블록된다. 
			// 하지만 최대 1초까지만 기다린다.
			Double result = future.get(1, TimeUnit.SECONDS);
		} catch (ExecutionException ee) {
			// 계산 중 예외 발생
		} catch (InterruptedException ei) {
			// 현재 스레드에서 대기 중 인ㅓ럽트 발생
		} catch (TimeoutException te) {
			// Future가 완료되기 전에 타임아웃 발생
		}
	}
	
	
	private static Double doSomeLongComputation() {
		return 1.0D;
	}
	
	private static void doSomethingElse() {
		System.out.println("doSometihngElse");
	}
	
	/**
	 * p502 예제 16-3 getPrice 메서드의 지연 흉내 내기
	 */
	public double getPrice(String product) {
		return calculatePrice(product);
	}
	
	/**
	 * p502 예제 16-3 getPrice 메서드의 지연 흉내 내기
	 */
	private double calculatePrice(String product) {
		delay();
		Random random = new Random();
		return random.nextDouble()*product.charAt(0) + product.charAt(1);
	}
	
	/**
	 * p502 예제 16-2 1초 지연을 흉내 내는 메서드
	 */
	public static void delay() {
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * p503 예제 16-5 비동기 API사용
	 */
	public static void p503_1() {
		Shop shop = new Shop("BestShop");
		
		long start = System.nanoTime();
		// 1. 비동기 호출
		Future<Double> futurePrice = shop.getPriceAsync("my favorite product");
		
		long invocationTime = ((System.nanoTime() - start)/1_000_000);
		System.out.println("Invocation returned after " + invocationTime + " msecs");
		
		// 2. 다른 작업 실행
		doSomethingElse();
		
		try {
			// 가격 정보가 있으면 Future에서 가격 정보를 읽고, 
			// 가격 정보가 없으면 가격 정보를 받을 때까지 블록한다.
			double price = futurePrice.get(10, TimeUnit.SECONDS);
			System.out.printf("Price is %.2f%n", price);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		long retrievalTime = ((System.nanoTime() - start)/1_000_000);
		System.out.println("Price returned after " + retrievalTime + " msecs");
				
	}
	
}
