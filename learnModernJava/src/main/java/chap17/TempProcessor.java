package chap17;

import java.util.concurrent.Flow.Processor;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;



/**
 * packageName : chap17
 * fileName    : TempProcessor.java
 * @author     : HSS
 * date        : 2021.01.06
 * description : 예제 17-10 화씨를 섭씨로 변환하는 Processor
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2021.01.06        HSS          최초 생성
 **/
public class TempProcessor implements Processor<TempInfo, TempInfo> {

	// implements Processor<TempInfo, TempInfo> 
	// : TempInfo를 다른 TempInfo로 변환하는 프로세서
	// public static interface Processor<T,R> extends Subscriber<T>, Publisher<R> {
	//
	// }	
	
	private Subscriber<? super TempInfo> subscriber;

	@Override
	public void subscribe(Subscriber<? super TempInfo> subscriber) {
		this.subscriber = subscriber;
	}
	@Override
	public void onNext(TempInfo temp) {
		subscriber.onNext(
			// 섭씨로 변환한 다음 TempInfo를 다시 전송
			new TempInfo(temp.getTown(), (temp.getTemp() - 32) * 5 / 9)
		);
	}
	
	// 아래 나머지 세개의 다른 신호는 업스트림 구독자에 전달
	@Override
	public void onSubscribe(Subscription subscription) {
		subscriber.onSubscribe(subscription);
	}
	@Override
	public void onError(Throwable throwable) {
		subscriber.onError(throwable);
	}
	@Override
	public void onComplete() {
		subscriber.onComplete();
	}
}
