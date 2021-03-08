package chap17;

import java.util.concurrent.Flow.Publisher;


/**
 * packageName : chap17
 * fileName    : MainCelsius.java
 * @author     : HSS
 * date        : 2021.01.06
 * description : 예제 17-11 Main 클래스 : Publisher를 만들고 TempSubscriber를 구독시킴
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2021.01.06        HSS          최초 생성
 **/
public class MainCelsius {

	public static void main(String[] args) {
		// 뉴욕의 섭씨 온도를 전송할 Publisher를 만듦.
		getCelsiusTemperatures("New York")
		// TempSubscriber를 Publisher로 구독.
		.subscribe(new TempSubscriber());
	}

	public static Publisher<TempInfo> getCelsiusTemperatures(String town) {
		return subscriber -> {
			// TempProcessor를 만들고 Subscriber와 반환된 Publisher 사이로 연결.
			TempProcessor processor = new TempProcessor();
			processor.subscribe(subscriber);
			processor.onSubscribe(new TempSubscription(processor, town));
		};
	}
}
