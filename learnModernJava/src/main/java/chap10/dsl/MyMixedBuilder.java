package chap10.dsl;

import java.util.function.Consumer;
import java.util.stream.Stream;

import chap10.dsl.MyMixedBuilder.StockBuilder;
import chap10.dsl.MyMixedBuilder.TradeBuilder;
import chap10.dsl.model.Order;
import chap10.dsl.model.Stock;
import chap10.dsl.model.Trade;

/**
 * packageName : chap10.dsl
 * fileName    : MyMixedBuilder.java
 * @author     : HSS
 * date        : 2020.12.05
 * description : p348 예제 10-12 여러 형식을 혼합한 DSL을 제공하는 주문 빌더
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.12.05        HSS          최초 생성
 **/
public class MyMixedBuilder {

	  public static Order forCustomer(String customer, TradeBuilder... builders) {
	    Order order = new Order();
	    order.setCustomer(customer);
	    Stream.of(builders).forEach(b -> order.addTrade(b.trade));
	    return order;
	  }

	  public static TradeBuilder buy(Consumer<TradeBuilder> consumer) {
	    return buildTrade(consumer, Trade.Type.BUY);
	  }

	  public static TradeBuilder sell(Consumer<TradeBuilder> consumer) {
	    return buildTrade(consumer, Trade.Type.SELL);
	  }

	  private static TradeBuilder buildTrade(Consumer<TradeBuilder> consumer, Trade.Type buy) {
	    TradeBuilder builder = new TradeBuilder();
	    builder.trade.setType(buy);
	    consumer.accept(builder);
	    return builder;
	  }

	  public static class TradeBuilder {

	    private Trade trade = new Trade();

	    public TradeBuilder quantity(int quantity) {
	      trade.setQuantity(quantity);
	      return this;
	    }

	    public TradeBuilder at(double price) {
	      trade.setPrice(price);
	      return this;
	    }

	    public StockBuilder stock(String symbol) {
	      return new StockBuilder(this, trade, symbol);
	    }

	  }

	  public static class StockBuilder {

	    private final TradeBuilder builder;
	    private final Trade trade;
	    private final Stock stock = new Stock();

	    private StockBuilder(TradeBuilder builder, Trade trade, String symbol) {
	      this.builder = builder;
	      this.trade = trade;
	      stock.setSymbol(symbol);
	    }

	    public TradeBuilder on(String market) {
	      stock.setMarket(market);
	      trade.setStock(stock);
	      return builder;
	    }

	  }

}
