package chap17;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

/**
 * 
 * packageName : chap17
 * fileName    : TempSubscription_p545.java
 * @author     : HSS
 * date        : 2021.01.05
 * description : 예제 17-6 Subscriber에게 TempInfo 스트림을 전송하는 Subscription
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2021.01.05        HSS          최초 생성
 *
 */
public class TempSubscription_p545 implements Subscription {

	private final Subscriber<? super TempInfo> subscriber;
	private final String town;

	public TempSubscription_p545(Subscriber<? super TempInfo> subscriber
								, String town) {
		this.subscriber = subscriber;
		this.town = town;
	}
	/**
	 * 구독자가 정보를 request를 통해 요청
	 */
	@Override
	public void request(long n) {
		// Subscriber가 만든 요청을 한 개씩 반복
		for (long i = 0L; i < n; i++) {
			try {
				// 현재 온도를 Subscriber 로 전달
				subscriber.onNext(TempInfo.fetch(town));
			} catch (Exception e) {
				// 온도 가져오기를 실패하면 Subscriber로 에러를 전달
				subscriber.onError(e);
				break;
			}
		}
	}
	@Override
	public void cancel() {
		// 구독이 취소되면 완료(onComplete)신호를 Subscriber로 전달
		subscriber.onComplete();
	}
}
