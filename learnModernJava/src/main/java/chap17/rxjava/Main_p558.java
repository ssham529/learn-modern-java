package chap17.rxjava;

import static chap17.rxjava.TempObservable.getTemperature;

import chap17.TempInfo;
import io.reactivex.Observable;

/**
 * packageName : chap17.rxjava
 * fileName    : Main_p558.java
 * @author     : HSS
 * date        : 2021.01.07
 * description : 예제 17-14 뉴욕의 온도를 출력하는 Main 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2021.01.07        HSS          최초 생성
 **/
public class Main_p558 {
	
	public static void main(String[] args) {
		// 매 초마다 뉴욕의 온도 보고를 방출하는 Observable 만들기
		Observable<TempInfo> observable = getTemperature("New York");
		// 단순 Observer로 이 Observable에 가입해서 온도 출력하기
		observable.blockingSubscribe(new TempObserver());
	}
}
