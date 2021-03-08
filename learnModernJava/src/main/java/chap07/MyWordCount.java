package chap07;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import myutil.MyUtil;

/**
 * 
 * packageName : chap07
 * fileName    : MyWordCount.java
 * @author     : HSS
 * date        : 2020.11.01
 * description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2020.11.01        HSS          최초 생성
 *
 */
public class MyWordCount {

	public static final String SENTENCE = " Nel   mezzo del cammin  di nostra  vita "
			+ "mi  ritrovai in una  selva oscura" + " che la  dritta via era   smarrita ";

	public static void main(String[] args) {
//		System.out.println("Found " + countWordsIteratively(SENTENCE) + " words");
//		System.out.println("Found " + countWords1(SENTENCE) + " words");
//		System.out.println("Found " + countWords2(SENTENCE) + " words");
		System.out.println("Found " + countWords3(SENTENCE) + " words");
		
	}

	/**
	 * p265. 반복형으로 단어수를 세는 메서드
	 */
	public static int countWordsIteratively(String s) {
		int counter = 0;
		boolean lastSpace = true;
		for (char c : s.toCharArray()) {
			if (Character.isWhitespace(c)) {
				lastSpace = true;
			} else {
				if (lastSpace) {
					counter++;
				}
				lastSpace = false;
			}
		}
		return counter;
	}

	/**
	 *  
	 */
	private static int countWords1(String s) {
		
		// 문자열을 Stream<Character> 로 만듬
		Stream<Character> stream = IntStream.range(0, s.length())
										   .mapToObj(s::charAt);
		return countWords(stream);
	}
	
	/**
	 * 병렬 stream실행 (문제 생김)
	 */
	private static int countWords2(String s) {
		
		// 문자열을 Stream<Character> 로 만듬
		Stream<Character> stream = IntStream.range(0, s.length())
										   .mapToObj(s::charAt);
		return countWords(stream.parallel());
	}
	
	/**
	 * p271. Spliterator 를 이용한 병렬 실행
	 */
	private static int countWords3(String s) {
		
	    Spliterator<Character> spliterator = new WordCounterSpliterator(s);
	    Stream<Character> stream = StreamSupport.stream(spliterator, true);

	    return countWords(stream);
	}
	
	/**
	 * stream 을 reduce 한다.
	 */
	private static int countWords(Stream<Character> stream) {
		WordCounter wordCounter = 
				stream.reduce(new WordCounter(0, true)
							, WordCounter::accumulate
							, WordCounter::combine); // 병렬 stream 실행시 사용
		return wordCounter.getCounter();
	}
	
	/**
	 * packageName : chap07
	 * fileName    : MyWordCount.java
	 * @author     : HSS
	 * date        : 2020.11.05
	 * description : p266. 예제 7-5 문자열 스트림을 탐색하면서 단어 수를 세는 클래스
	 * ===========================================================
	 * DATE              AUTHOR             NOTE
	 * -----------------------------------------------------------
	 * 2020.11.05        HSS          최초 생성
	 *
	 */
	private static class WordCounter {

		private final int counter;
		private final boolean lastSpace;

		public WordCounter(int counter, boolean lastSpace) {
			this.counter = counter;
			this.lastSpace = lastSpace;
		}

		/**
		 * WordCounter 클래스를 어떤 상태로 생설할 것인지 정의
		 * 스트림을 탐색하면서 새로운 문자를 찾을 때마다 accmulate 메서드를 호출한다.
		 */
		public WordCounter accumulate(Character c) {
			
			MyUtil.printCallerInfo();
			
			if (Character.isWhitespace(c)) {
				return lastSpace ? this : new WordCounter(counter, true);
			} else {
				return lastSpace ? new WordCounter(counter + 1, false) : this;
			}
		}

		/**
		 * WordCounter의 내부 counter 값을 서로 합친다.
		 */
		public WordCounter combine(WordCounter wordCounter) {
			System.out.println("--- combine ---");
			return new WordCounter(counter + wordCounter.counter, wordCounter.lastSpace);
		}

		public int getCounter() {
			return counter;
		}
	}

	/**
	 * 
	 * packageName : chap07
	 * fileName    : MyWordCount.java
	 * @author     : HSS
	 * date        : 2020.11.05
	 * description : p269 예제 7-6 WordCounterSpliterator
	 * ===========================================================
	 * DATE              AUTHOR             NOTE
	 * -----------------------------------------------------------
	 * 2020.11.05        HSS          최초 생성
	 *
	 */
	private static class WordCounterSpliterator implements Spliterator<Character> {

		private final String string;
		// 현재 탐색 위치
		private int currentChar = 0;

		private WordCounterSpliterator(String string) {
			this.string = string;
		}

		@Override
		public boolean tryAdvance(Consumer<? super Character> action) {
			// 현재 문자를 소비한다.
			action.accept(string.charAt(currentChar++));
			// 소비할 문자가 남아있으면 true를 반환한다.
			return currentChar < string.length();
		}

		@Override
		public Spliterator<Character> trySplit() {
			// currentSize : 남은 탐색 길이
			int currentSize = string.length() - currentChar;
			if (currentSize < 10) {
				return null;
			}
			
			// 012345 6789012345 6789012345
			// abcdef ghijklmnop qrstuvwxyz
			// ex) 현재 위치 f, 분할 위치 p, 문자열길이 26, 남은 문자열길이 20  
			
			// for문의 시작 위치는 남은문자열의 1/2 지점이다. 
			// currentChar 를 더하는 이유는 탐색이 남은 문자 길이의 1/2 한 위치(Position)를 찾기 위해서 있다.
			// whitespace 를 찾을때까지 for문을 반복한다.
			for (int splitPos = currentSize / 2 + currentChar; 
					splitPos < string.length(); 
					splitPos++) {
				
				if (Character.isWhitespace(string.charAt(splitPos))) {
					
					// 탐색 후 남은 문자열의 처음부터 분할위치 까지의 문자열로 
					// WordCounterSpliterator 객체를 새로 생성한다.
					Spliterator<Character> spliterator = new WordCounterSpliterator(
							string.substring(currentChar, splitPos));
					// 탐색 현재 위치를 분할위치로 설정한다.
					currentChar = splitPos;
					// 루프 종료
					return spliterator;
				}
			}
			return null;
		}

		@Override
		public long estimateSize() {
			return string.length() - currentChar;
		}

		@Override
		public int characteristics() {
			return ORDERED + SIZED + SUBSIZED + NONNULL + IMMUTABLE;
		}

	}	
	
	
	
}
