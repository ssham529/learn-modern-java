package chap10;

import myutil.MyUtil;

/**
 * packageName : chap10
 * fileName    : MyTest.java
 * @author     : HSS
 * date        : 2020.12.03
 * description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.12.03        HSS          최초 생성
 **/
public class MyTest {

	public static void main(String[] args) {

		p331_1();
	}
	
	/**
	 * 최신 자바 API의 작은 DSL p331
	 */
	private static void p331_1() {
		MyUtil.printMethodName();
		
//		//
//		Collections.sort(persons,  new Comparator<Person>() {
//			public int compare(Person p1, Person p2) {
//				return p1.getAge() - p2.getAge();
//			}
//		});
//		
//		//
//		Collections.sort(people, (p1, p2) -> p1.getAge() - p2.getAge() );
//		
//		// 
//		Collections.sort(persons, Comparator.comparing(p -> p.getAge()));
//		
//		//
//		Collections.sort(persons, Comparator.comparing(Person::getAge));
//
//		//
//		Collections.sort(persons, Comparator.comparing(Person::getAge).reverse());
//		
//		//
//		Collections.sort(persons, Comparator.comparing(Person::getAge).thenComparing(Person::getName));
//		
//		//
//		persons.sort(Comparator.comparing(Person::getAge).thenComparing(Person::getName));
		
	}

}
