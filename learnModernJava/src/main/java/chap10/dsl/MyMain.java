package chap10.dsl;

import static chap10.dsl.MyMethodChainingOrderBuilder.forCustomer;
import static chap10.dsl.MyMixedBuilder.buy;
import static chap10.dsl.MyMixedBuilder.forCustomer;
import static chap10.dsl.MyMixedBuilder.sell;
import static chap10.dsl.MyNestedFunctionOrderBuilder.at;
import static chap10.dsl.MyNestedFunctionOrderBuilder.buy;
import static chap10.dsl.MyNestedFunctionOrderBuilder.on;
import static chap10.dsl.MyNestedFunctionOrderBuilder.order;
import static chap10.dsl.MyNestedFunctionOrderBuilder.sell;
import static chap10.dsl.MyNestedFunctionOrderBuilder.stock;

import chap10.dsl.model.Order;
import chap10.dsl.model.Stock;
import chap10.dsl.model.Trade;

/**
 * packageName : chap10.dsl
 * fileName    : MyMain.java
 * @author     : HSS
 * date        : 2020.12.05
 * description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.12.05        HSS          최초 생성
 **/
public class MyMain {

	  public static void main(String[] args) {
	    MyMain main = new MyMain();
	    main.plain();
	    main.methodChaining();
	    main.nestedFunction();
	    main.lambda();
	    main.mixed();
	  }

	  /**
	   * p338 예제 10.4 도메인 객체의 API를 직접 이용해 주식 거래 주문을 만든다.
	   * 
	   * 일반적으로 도메인 객체를 조합하게 되면 이렇게 만들게 된다.
	   */
	  public void plain() {
	    Order order = new Order();
	    order.setCustomer("BigBank");

	    Trade trade1 = new Trade();
	    trade1.setType(Trade.Type.BUY);

	    Stock stock1 = new Stock();
	    stock1.setSymbol("IBM");
	    stock1.setMarket("NYSE");

	    trade1.setStock(stock1);
	    trade1.setPrice(125.00);
	    trade1.setQuantity(80);
	    order.addTrade(trade1);

	    Trade trade2 = new Trade();
	    trade2.setType(Trade.Type.BUY);

	    Stock stock2 = new Stock();
	    stock2.setSymbol("GOOGLE");
	    stock2.setMarket("NASDAQ");

	    trade2.setStock(stock2);
	    trade2.setPrice(375.00);
	    trade2.setQuantity(50);
	    order.addTrade(trade2);

	    System.out.println("Plain:");
	    System.out.println(order);
	  }

	  /**
	   * p339 10.3.1 메서드 체인
	   */
	  public void methodChaining() {
	    Order order = forCustomer("BigBank")
	        .buy(80)
	        .stock("IBM")
	        .on("NYSE")
	        .at(125.00)
	        .sell(50)
	        .stock("GOOGLE")
	        .on("NASDAQ")
	        .at(375.00)
	        .end();

	    System.out.println("Method chaining:");
	    System.out.println(order);
	  }

	  /**
	   * p343 예제 10-7 중첩된 함수로 주식 거래 만들기
	   */
	  public void nestedFunction() {
	    Order order = order("BigBank",
	        buy(80,
	            stock("IBM", on("NYSE")),
	            at(125.00)),
	        sell(50,
	            stock("GOOGLE", on("NASDAQ")),
	            at(375.00))
	    );

	    System.out.println("Nested function:");
	    System.out.println(order);
	  }

	  /**
	   * p345 예제 10-9 함수 시퀀싱으로 주식 거래 주문 만들기
	   */
	  public void lambda() {
	    Order order = MyLambdaOrderBuilder.order( o -> {
	      o.forCustomer( "BigBank" );
	      o.buy( t -> {
	        t.quantity(80);
	        t.price(125.00);
	        t.stock(s -> {
	          s.symbol("IBM");
	          s.market("NYSE");
	        });
	      });
	      o.sell( t -> {
	        t.quantity(50);
	        t.price(375.00);
	        t.stock(s -> {
	          s.symbol("GOOGLE");
	          s.market("NASDAQ");
	        });
	      });
	    });

	    System.out.println("Lambda:");
	    System.out.println(order);
	  }

	  /**
	   * p347 예제 10-11 여러 DSL 패턴을 이용해 주식 거래 주문 만들기
	   */
	  public void mixed() {
	    Order order =
	        forCustomer("BigBank",
	            buy(t -> t.quantity(80)
	                .stock("IBM")
	                .on("NYSE")
	                .at(125.00)),
	            sell(t -> t.quantity(50)
	                .stock("GOOGLE")
	                .on("NASDAQ")
	                .at(375.00)));

	    System.out.println("Mixed:");
	    System.out.println(order);
	  }

}
