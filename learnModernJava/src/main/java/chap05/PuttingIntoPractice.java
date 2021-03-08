package chap05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import myutil.MyUtil;

public class PuttingIntoPractice {

	public static void main(String[] args) {
		PuttingIntoPractice pip = new PuttingIntoPractice();
		
	    Trader raoul = new Trader("Raoul", "Cambridge");
	    Trader mario = new Trader("Mario", "Milan");
	    Trader alan = new Trader("Alan", "Cambridge");
	    Trader brian = new Trader("Brian", "Cambridge");

	    List<Transaction> transactions = Arrays.asList(
	        new Transaction(brian, 2011, 300),
	        new Transaction(raoul, 2012, 1000),
	        new Transaction(raoul, 2011, 400),
	        new Transaction(mario, 2012, 710),
	        new Transaction(mario, 2012, 700),
	        new Transaction(alan, 2012, 950)
	    );

	    
		
	}
	
	/**
	 * 질의 1: 2011년부터 발생한 모든 거래를 찾아 값으로 정렬(작은 값에서 큰 값).
	 */
	private void p179_예제5_1(List<Transaction> transactions) {
		MyUtil.printMethodName("질의 1: 2011년부터 발생한 모든 거래를 찾아 값으로 정렬(작은 값에서 큰 값).");
		
	    List<Transaction> tr2011 = transactions.stream()
	        .filter(transaction -> transaction.getYear() == 2011)
	        .sorted(Comparator.comparing(Transaction::getValue))
	        .collect(Collectors.toList());
	    
	    System.out.println(tr2011);
	}
	

}
