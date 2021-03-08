package chap17;

import java.util.Random;

/**
 * 
 * packageName : chap17
 * fileName    : TempInfo.java
 * @author     : HSS
 * date        : 2021.01.05
 * description : 예제 17-5 현재 보고된 온도를 전달하는 자바 빈
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2021.01.05        HSS          최초 생성
 *
 */
public class TempInfo {

	public static final Random random = new Random();

	private final String town;
	private final int temp;

	public TempInfo(String town, int temp) {
		this.town = town;
		this.temp = temp;
	}

	/*
	 * 정적 팩토리 메서드를 이용해 해당 도시의 TempInfo 인스턴스를 만든다.
	 */
	public static TempInfo fetch(String town) {

		// 10 분의 1 확률로 온도 가져오기 작업이 실패한다.
		if (random.nextInt(10) == 0) {
			throw new RuntimeException("Error!");
		}
		// 0에서 99 사이에서 임의의 화씨 온도를 반환한다.
		return new TempInfo(town, random.nextInt(100));
	}

	@Override
	public String toString() {
		return town + " : " + temp;
	}

	public int getTemp() {
		return temp;
	}

	public String getTown() {
		return town;
	}
}
