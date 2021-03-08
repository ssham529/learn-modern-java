package chap08;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * packageName : chap08
 * fileName    : MyWorkingWithCollections.java
 * @author     : HSS
 * date        : 2020.11.16
 * description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.11.16        HSS          최초 생성
 **/
public class MyWorkingWithCollections {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		test();
		
	}
	
	/**
	 * p288 
	 * 8.3.7 합침. 
	 * Map 의 새로운 메서드 merge 에 대한 테스트
	 */
	private static void test() {

		Map<String, String> map1 = new HashMap<>();
		map1.put("key1", "value1");
		map1.put("key2", "value2");
		map1.put("key3", null);
		
		// map1 과  키가 중복은 아닌 경우
		// map1.merge("key9", "value9", ((val1, val2) -> val1 + " & " + val2));
		// 결과 : {key1=value1, key2=value2, key3=null, key9=value9}

		// map1 과 키가 중복은 아니고, 새로운 값이 null 인 경우
		// map1.merge("key9", null, ((val1, val2) -> val1 + " & " + val2));
		// 결과 : java.lang.NullPointerException
		
		// map1 과 키가 중복은 아니고, 리맵핑이 null 인 경우
		// map1.merge("key9", "value9", ((val1, val2) -> null));
		// 결과 : {key1=value1, key2=value2, key3=null, key9=value9}
		
		// -----------------------------------------
		
		// map1 과 키가 중복되면서, map1 의 값이 null 인 경우
		// map1.merge("key3", "value3", ((val1, val2) -> val1 + " & " + val2));
		// 결과 : {key1=value1, key2=value2, key3=value3}
		
		// map1 과 키가 중복되면서, map1 의 값이 null 이고, 리맵핑 결과가 null 인 경우.
		// map1.merge("key3", "valueNew", ((val1, val2) -> null));
		// 결과 : {key1=value1, key2=value2, key3=valueNew}
		
		// map1 과 키가 중복되면서, 새로운 값이 null 인 경우
		// map1.merge("key2", null, ((val1, val2) -> val1 + " & " + val2));
		// 결과 : java.lang.NullPointerException
		
		// map1 과 키가 중복되면서, map1, 새로운값 모두 값이 있는 경우.
		// map1.merge("key2", "valueNew", ((val1, val2) -> val1 + " & " + val2));
		// 결과 : {key1=value1, key2=value2 & valueNew, key3=null}

		// map1 과 키가 중복되면서, map1, 새로운값 모두 값이 있고, 리맵핑 결과가 null 인 경우
		// map1.merge("key2", "valueNew", ((val1, val2) -> null));
		// 결과 : {key1=value1, key3=null}

		// System.out.println(map1);
		
		//=====================================
		// 새로운 키가 null인 경우..
		//=====================================
		Map<String, String> map2 = new HashMap<>();
		map2.put("key1", "value1");

		// 새로운 키가 null 인 경우
		// map2.merge(null, "value2", ((val1, val2) -> val1 + " & " + val2));
		// 결과 : {null=value2, key1=value1}
		
		// System.out.println(map2);
		
		//=====================================
		// 키가 null 이면서 중복되는 경우.
		//=====================================
		Map<String, String> map3 = new HashMap<>();
		map3.put("key1", "value1");
		map3.put(null, "value2");
		
		// map 에도 키가 null 이 있고, 새로운 키도 null 인 경우
		// map3.merge(null, "value9", ((val1, val2) -> val1 + " & " + val2));
		// 결과 : {key1=value1, null=value2 & value9}

		// map 에도 키가 null, 새로운 키도 null 이면서, 리맵핑 결과가 null 인 경우.
		// map3.merge(null, "value9", ((val1, val2) -> null));
		// 결과 : {key1=value1}

		// System.out.println(map3);
		
		//=====================================
		// 키가 null 이면서 중복되고, 해당 키의 값이 null 인 경우.
		//=====================================
		Map<String, String> map4 = new HashMap<>();
		map4.put("key1", "value1");
		map4.put(null, null);
		
		// 키가 null 로 중복되고, map 의 값도 null 인 경우
		// map4.merge(null, "value2", ((val1, val2) -> val1 + " & " + val2));
		// 결과 : {key1=value1, null=value2}
		
		// 키가 null 로 중복되고, map 의 값도 null 이면서, 리맵핑 결과가 null 인 경우.
		// map4.merge(null, "value2", ((val1, val2) -> null));
		// 결과 : {key1=value1, null=value2}
		
		// System.out.println(map4);
	}

}
