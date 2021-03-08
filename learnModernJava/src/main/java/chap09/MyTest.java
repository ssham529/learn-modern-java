package chap09;

import myutil.MyUtil;

public class MyTest {

	public static void main(String[] args) {
		p295_1();

	}
	
	/**
	 * 익명 클래스는 감싸고 있는 클래스의 변수를 가릴 수 있다. 하지만 람다 표현식으로는 변수를 가릴 수 없다
	 */
	private static void p295_1() {
		MyUtil.printMethodName();
		
		int a = 10;
		Runnable r1 = () -> {
//			int a = 2;	// 컴파일 에러
			System.out.println(a);
		};
		
		Runnable r2 = new Runnable() {
			public void run() {
				int a = 2; // 모든 것이 잘 작동한다.
				System.out.println(a);
			}
		};
	}
	
	private interface Task {
		public void execute();
	}
	public static void doSomething(Runnable r) { r.run(); }
	public static void doSomething(Task a) { a.execute(); }
	
	/**
	 *
	 */
	private static void p296_1() {
		MyUtil.printMethodName();
		
		// 1. 익명 클래스를 전달 가능.
		doSomething( new Task() {
			public void execute() {
				System.out.println("Danger danger!!");
			}
		});
		
		// 2. 람다 표현식으로 바꾸면  Runnable 과 Task 모두 대상 형식이 될 수 있어 문제 생김.
		// doSomething( () -> System.out.println("Danger danger!!") );
		
		// 3. 명시적 형변환 (Task )를 이용.
		doSomething( (Task)() -> System.out.println("Danger danger!!") );
	}
	
	
	/**
	 * 
	 */
	private static void p305_1() {
		MyUtil.printMethodName();
		
	}
	



}
