package chap17;

import java.util.concurrent.Flow.Publisher;


/**
 * packageName : chap17
 * fileName    : Main.java
 * @author     : HSS
 * date        : 2021.01.05
 * description : 예제 17-8 Publisher를 만들고 TempSubscriber를 구독시킴
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2021.01.05        HSS          최초 생성
 **/
public class Main {

	public static void main(String[] args) {
		// 뉴욕에 새 Publisher를 만들고 TempSubscriber를 구독 시킴
		getTemperatures("New York").subscribe(new TempSubscriber());
	}

	/**
	 * getTemperatures 정적 메서드를 호출하면
	 * Publisher 인터페이스를 구현하고 인스턴스를 생성하여 반환한다.
	 */
	private static Publisher<TempInfo> getTemperatures(String town) {
		// 구독한 Subscriber에게 TempSubscription을 전송하는 Publisher를 반환
		// Publisher.subscribe(TempSubscriber) 를 호출하면 아래 코드가 동작함.
		// Subscriber 와 Subscription 은 서로 참조 하게 된다.
		return subscriber -> subscriber.onSubscribe(
				new TempSubscription(subscriber, town) );
	}
}
