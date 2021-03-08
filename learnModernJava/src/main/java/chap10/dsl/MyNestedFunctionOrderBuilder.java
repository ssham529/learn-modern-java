package chap10.dsl;

import java.util.stream.Stream;

import chap10.dsl.model.Order;
import chap10.dsl.model.Stock;
import chap10.dsl.model.Trade;

/**
 * packageName : chap10.dsl
 * fileName    : MyNestedFunctionOrderBuilder.java
 * @author     : HSS
 * date        : 2020.12.05
 * description : p343 예제 10-8 중첩된 함수 DSL을 제공하는 주문 빌더
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.12.05        HSS          최초 생성
 **/
public class MyNestedFunctionOrderBuilder {

	  public static Order order(String customer, Trade... trades) {
	    Order order = new Order();
	    order.setCustomer(customer);
	    Stream.of(trades).forEach(order::addTrade);
	    return order;
	  }

	  public static Trade buy(int quantity, Stock stock, double price) {
	    return buildTrade(quantity, stock, price, Trade.Type.BUY);
	  }

	  public static Trade sell(int quantity, Stock stock, double price) {
	    return buildTrade(quantity, stock, price, Trade.Type.SELL);
	  }

	  private static Trade buildTrade(int quantity, Stock stock, double price, Trade.Type buy) {
	    Trade trade = new Trade();
	    trade.setQuantity(quantity);
	    trade.setType(buy);
	    trade.setStock(stock);
	    trade.setPrice(price);
	    return trade;
	  }

	  public static double at(double price) {
	    return price;
	  }

	  public static Stock stock(String symbol, String market) {
	    Stock stock = new Stock();
	    stock.setSymbol(symbol);
	    stock.setMarket(market);
	    return stock;
	  }

	  public static String on(String market) {
	    return market;
	  }

}
