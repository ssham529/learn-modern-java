package chap10.dsl;

import chap10.dsl.model.Order;
import chap10.dsl.model.Stock;
import chap10.dsl.model.Trade;

/**
 * packageName : chap10.dsl
 * fileName    : MyMethodChainingOrderBuilder.java
 * @author     : HSS
 * date        : 2020.12.05
 * description : p340 예제 10-6 메서드 체인 DSL을 제공하는 주문 빌더
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.12.05        HSS          최초 생성
 **/
public class MyMethodChainingOrderBuilder {

	  public final Order order = new Order();

	  private MyMethodChainingOrderBuilder(String customer) {
	    order.setCustomer(customer);
	  }

	  public static MyMethodChainingOrderBuilder forCustomer(String customer) {
	    return new MyMethodChainingOrderBuilder(customer);
	  }

	  public Order end() {
	    return order;
	  }

	  public TradeBuilder buy(int quantity) {
	    return new TradeBuilder(this, Trade.Type.BUY, quantity);
	  }

	  public TradeBuilder sell(int quantity) {
	    return new TradeBuilder(this, Trade.Type.SELL, quantity);
	  }

	  private MyMethodChainingOrderBuilder addTrade(Trade trade) {
	    order.addTrade(trade);
	    return this;
	  }

	  public static class TradeBuilder {

	    private final MyMethodChainingOrderBuilder builder;
	    public final Trade trade = new Trade();

	    private TradeBuilder(MyMethodChainingOrderBuilder builder, Trade.Type type, int quantity) {
	      this.builder = builder;
	      trade.setType(type);
	      trade.setQuantity(quantity);
	    }

	    public StockBuilder stock(String symbol) {
	      return new StockBuilder(builder, trade, symbol);
	    }

	  }

	  public static class TradeBuilderWithStock {

	    private final MyMethodChainingOrderBuilder builder;
	    private final Trade trade;

	    public TradeBuilderWithStock(MyMethodChainingOrderBuilder builder, Trade trade) {
	      this.builder = builder;
	      this.trade = trade;
	    }

	    public MyMethodChainingOrderBuilder at(double price) {
	      trade.setPrice(price);
	      return builder.addTrade(trade);
	    }

	  }

	  public static class StockBuilder {

	    private final MyMethodChainingOrderBuilder builder;
	    private final Trade trade;
	    private final Stock stock = new Stock();

	    private StockBuilder(MyMethodChainingOrderBuilder builder, Trade trade, String symbol) {
	      this.builder = builder;
	      this.trade = trade;
	      stock.setSymbol(symbol);
	    }

	    public TradeBuilderWithStock on(String market) {
	      stock.setMarket(market);
	      trade.setStock(stock);
	      return new TradeBuilderWithStock(builder, trade);
	    }

	  }

}
