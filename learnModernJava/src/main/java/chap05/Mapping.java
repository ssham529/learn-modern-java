package chap05;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import myutil.MyUtil;


/**
 * packageName : chap05
 * fileName    : Mapping.java
 * @author     : HSS
 * date        : 2020.09.25
 * description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.09.25        HSS          최초 생성
 **/
public class Mapping {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Mapping mp = new Mapping();
		
		mp.p163();
		
		mp.p164_arraysStreamTest();
	}

	/**
	 * 5.3.2 스트림 평면화
	 * ["Hellow", "World"] 를 
	 * ["H", "e", "l", "o", "W", "r", "d"] 포함하는 리스트가 되도록 하려면
	 */
	public void p163() {
		MyUtil.printMethodName("5.3.2 스트림 평면화");
		
	    List<String> words = Arrays.asList("Hello", "World");
	    
	    // 1. 이렇게 하면 될거 같지만 실패다. 
	    List<String[]> test1= words.stream()
		    .map(word -> word.split("")) // Stream<String[]> 리턴
		    .distinct()
		    .collect(toList());

	    // 2. map 과 Arrays.stream 활용. 문제가 해결 되진 않음.
	    // String[] >> Stream<String>
	    words.stream()
	   		.map(word -> word.split(""))
	   		.map(Arrays::stream) // Stream<Stream<String>> 를 리턴
	   		.distinct()
	   		.collect(toList());
	    
	    // 3. flatMap 사용
	    List<String> uniqueCharacters = words.stream()
	    	.map(word -> word.split("")) // Stream<String[]> 리턴
	    	.flatMap(Arrays::stream)     // Stream<String> 리턴
	    	.distinct()
	    	.collect(toList());

	    uniqueCharacters.forEach(System.out::println);
	    
	}
	
	/**
	 * p164. Arrays.stream 테스트  
	 */
	public void p164_arraysStreamTest() {
		MyUtil.printMethodName("Arrays.stream 테스트  ");
				
		String[] array = {"goodbye", "World"};
		Stream<String> stream = Arrays.stream(array);
		
		stream.forEach(System.out::println);
		
	}
	
}
