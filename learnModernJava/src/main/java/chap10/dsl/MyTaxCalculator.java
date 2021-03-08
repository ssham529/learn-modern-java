package chap10.dsl;

import static chap10.dsl.MyMixedBuilder.buy;
import static chap10.dsl.MyMixedBuilder.forCustomer;
import static chap10.dsl.MyMixedBuilder.sell;

import java.util.function.DoubleUnaryOperator;

import chap10.dsl.model.Order;
import chap10.dsl.model.Tax;


/**
 * packageName : chap10.dsl
 * fileName    : MyTaxCalculator.java
 * @author     : HSS
 * date        : 2020.12.06
 * description : p351 예제 10-15 적용할 세금을 유창하게 정의하는 세금 계산기
 *				 p352 예제 10-16 유창하게 세금 함수를 적용하는 세금 계산기 
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.12.06        HSS          최초 생성
 **/
public class MyTaxCalculator {

	/**
	 * p350 예제 10-14 불리언 플래그 집합을 이용해 주문에 세금 적용
	 */
	public static double calculate(Order order, boolean useRegional, boolean useGeneral, boolean useSurcharge) {
		double value = order.getValue();
		if (useRegional) {
			value = Tax.regional(value);
		}
		if (useGeneral) {
			value = Tax.general(value);
		}
		if (useSurcharge) {
			value = Tax.surcharge(value);
		}
		return value;
	}

	private boolean useRegional;
	private boolean useGeneral;
	private boolean useSurcharge;

	public MyTaxCalculator withTaxRegional() {
		useRegional = true;
		return this;
	}

	public MyTaxCalculator withTaxGeneral() {
		useGeneral = true;
		return this;
	}

	public MyTaxCalculator withTaxSurcharge() {
		useSurcharge = true;
		return this;
	}

	public double calculate(Order order) {
		return calculate(order, useRegional, useGeneral, useSurcharge);
	}

	public DoubleUnaryOperator taxFunction = d -> d;

	public MyTaxCalculator with(DoubleUnaryOperator f) {
		taxFunction = taxFunction.andThen(f);
		return this;
	}

	public double calculateF(Order order) {
		return taxFunction.applyAsDouble(order.getValue());
	}

	public static void main(String[] args) {
		Order order = forCustomer("BigBank", buy(t -> t.quantity(80).stock("IBM").on("NYSE").at(125.00)),
				sell(t -> t.quantity(50).stock("GOOGLE").on("NASDAQ").at(125.00)));

		double value = MyTaxCalculator.calculate(order, true, false, true);
		System.out.printf("Boolean arguments: %.2f%n", value);

		value = new MyTaxCalculator().withTaxRegional().withTaxSurcharge().calculate(order);
		System.out.printf("Method chaining: %.2f%n", value);

		value = new MyTaxCalculator().with(Tax::regional).with(Tax::surcharge).calculateF(order);
		System.out.printf("Method references: %.2f%n", value);
	}

}
