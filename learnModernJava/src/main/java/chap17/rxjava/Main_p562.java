package chap17.rxjava;

import static chap17.rxjava.TempObservable.getCelsiusTemperatures;

import chap17.TempInfo;
import io.reactivex.Observable;


/**
 * packageName : chap17.rxjava
 * fileName    : Main_p562.java
 * @author     : HSS
 * date        : 2021.01.07
 * description : p562 예제 17-17 세 도시의 온도를 출력하는 Main 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2021.01.07        HSS          최초 생성
 **/
public class Main_p562 {
	
	public static void main(String[] args) {
		Observable<TempInfo> observable = getCelsiusTemperatures(
				"New York", "Chicago", "San Francisco ");
		observable.blockingSubscribe( new TempObserver() );
	}
}
