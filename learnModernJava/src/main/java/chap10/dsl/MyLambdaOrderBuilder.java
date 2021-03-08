package chap10.dsl;

import java.util.function.Consumer;

import chap10.dsl.MyLambdaOrderBuilder;
import chap10.dsl.MyLambdaOrderBuilder.StockBuilder;
import chap10.dsl.MyLambdaOrderBuilder.TradeBuilder;
import chap10.dsl.model.Order;
import chap10.dsl.model.Stock;
import chap10.dsl.model.Trade;

/**
 * packageName : chap10.dsl
 * fileName    : MyLambdaOrderBuilder.java
 * @author     : HSS
 * date        : 2020.12.05
 * description : p345 예제 10-10 함수 시퀀싱 DSL을 제공하는 주문 빌더
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.12.05        HSS          최초 생성
 **/
public class MyLambdaOrderBuilder {

	  private Order order = new Order();

	  public static Order order(Consumer<MyLambdaOrderBuilder> consumer) {
	    MyLambdaOrderBuilder builder = new MyLambdaOrderBuilder();
	    consumer.accept(builder);
	    return builder.order;
	  }

	  public void forCustomer(String customer) {
	    order.setCustomer(customer);
	  }

	  public void buy(Consumer<TradeBuilder> consumer) {
	    trade(consumer, Trade.Type.BUY);
	  }

	  public void sell(Consumer<TradeBuilder> consumer) {
	    trade(consumer, Trade.Type.SELL);
	  }

	  private void trade(Consumer<TradeBuilder> consumer, Trade.Type type) {
	    TradeBuilder builder = new TradeBuilder();
	    builder.trade.setType(type);
	    consumer.accept(builder);
	    order.addTrade(builder.trade);
	  }

	  public static class TradeBuilder {

	    private Trade trade = new Trade();

	    public void quantity(int quantity) {
	      trade.setQuantity(quantity);
	    }

	    public void price(double price) {
	      trade.setPrice(price);
	    }

	    public void stock(Consumer<StockBuilder> consumer) {
	      StockBuilder builder = new StockBuilder();
	      consumer.accept(builder);
	      trade.setStock(builder.stock);
	    }

	  }

	  public static class StockBuilder {

	    private Stock stock = new Stock();

	    public void symbol(String symbol) {
	      stock.setSymbol(symbol);
	    }

	    public void market(String market) {
	      stock.setMarket(market);
	    }

	  }

}
