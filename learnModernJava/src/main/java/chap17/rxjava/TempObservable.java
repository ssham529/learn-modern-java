package chap17.rxjava;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import chap17.TempInfo;

public class TempObservable {

	public static Observable<TempInfo> getTemperature(String town) {
		// Observer를 소비하는 함수로부터 Observable 만들기
		return Observable.create(emitter -> 
				// 매 초마다 무한으로 증가하는 일련의 long값을 방출하는 Observable
				Observable.interval(1, TimeUnit.SECONDS)
				// HSS => 착각하면 안된다. Flow API 에서 사용했던 Publisher.subscribe 와 같은 기능의 메소드가 아니다.
				// 여러 오버로드 버전의 subscribe가 존재한다.
				.subscribe(i -> {
					// 소비된 옵저버가 아직 폐기 되지 않았으면 어떤 작업을 수행(이전 에러)
					if (!emitter.isDisposed()) {
						// 온도를 다선 번 보고했으면 옵저버를 완료하고 스트림을 종료
						if (i >= 5) {
							emitter.onComplete();
						} else {
							try {
								// 온도를 Observer로 보고
								emitter.onNext(TempInfo.fetch(town));
							} catch (Exception e) {
								// 에러가 발생하면 Observer에 알림
								emitter.onError(e);
							}
						}
					}
		}));
	}

	/*
	 * p560	예제 17-15 Observable에 map을 이용해 화씨를 섭씨로 변환
	 */
	public static Observable<TempInfo> getCelsiusTemperature(String town) {
		return getTemperature(town).map(
				temp -> new TempInfo(temp.getTown(), (temp.getTemp() - 32) * 5 / 9));
	}

	/*
	 * p561 s퀴즈 17-2 영하 온도만 거르기
	 */
	public static Observable<TempInfo> getNegativeTemperature(String town) {
		return getCelsiusTemperature(town).filter(
				temp -> temp.getTemp() < 0);
	}

	/*
	 * p562 예제 17-16 한 개 이상 도시의 온도 보고를 합친다
	 */
	public static Observable<TempInfo> getCelsiusTemperatures(String... towns) {
		return Observable.merge(
				Arrays.stream(towns)
				// .map(town -> TempObservable.getCelsiusTemperature(town))
				.map(TempObservable::getCelsiusTemperature)
				.collect(toList()));
	}

}
