package chap12;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import myutil.MyUtil;

public class DateTimeExamples {

	private static final ThreadLocal<DateFormat> formatters = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("dd-MMM-yyyy");
		}
	};

	public static void main(String[] args) {
//		useOldDate();
//		useLocalDate();
//		useTemporalAdjuster();
//		useDateFormatter();
		
		p397_1();
		
	}
	
	private static void useOldDate() {
		MyUtil.printMethodName();

		Date date = new Date(114, 2, 18);
		System.out.println(date);

		System.out.println(formatters.get().format(date));

		Calendar calendar = Calendar.getInstance();
		calendar.set(2014, Calendar.FEBRUARY, 18);
		System.out.println(calendar);
	}

	private static void useLocalDate() {
		MyUtil.printMethodName();

		LocalDate date = LocalDate.of(2014, 3, 18);
		int year = date.getYear(); // 2014
		Month month = date.getMonth(); // MARCH
		int day = date.getDayOfMonth(); // 18
		DayOfWeek dow = date.getDayOfWeek(); // TUESDAY
		int len = date.lengthOfMonth(); // 31 (3월의 길이)
		boolean leap = date.isLeapYear(); // false (윤년이 아님)
		System.out.println(date);
		
		int y = date.get(ChronoField.YEAR);
		int m = date.get(ChronoField.MONTH_OF_YEAR);
		int d = date.get(ChronoField.DAY_OF_MONTH);

		//==================================================================
		//
		//==================================================================
		LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
		int hour = time.getHour(); // 13
		int minute = time.getMinute(); // 45
		int second = time.getSecond(); // 20
		System.out.println(time);

		//==================================================================
		//
		//==================================================================
		LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45
		LocalDateTime dt2 = LocalDateTime.of(date, time);
		LocalDateTime dt3 = date.atTime(13, 45, 20);
		LocalDateTime dt4 = date.atTime(time);
		LocalDateTime dt5 = time.atDate(date);
		System.out.println(dt1);

		//==================================================================
		//
		//==================================================================
		LocalDate date1 = dt1.toLocalDate();
		System.out.println(date1);
		LocalTime time1 = dt1.toLocalTime();
		System.out.println(time1);

		//==================================================================
		//
		//==================================================================
		Instant instant = Instant.ofEpochSecond(44 * 365 * 86400);
		Instant now = Instant.now();

		//==================================================================
		//
		//==================================================================
		Duration d1 = Duration.between(LocalTime.of(13, 45, 10), time);
		Duration d2 = Duration.between(instant, now);
		System.out.println(d1.getSeconds());
		System.out.println(d2.getSeconds());

		Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
		System.out.println(threeMinutes);

		JapaneseDate japaneseDate = JapaneseDate.from(date);
		System.out.println(japaneseDate);
	}
	
	/**
	 * p397 예제 12-7 상대적인 방식으로 LocalDate 속성 바꾸기
	 */
	private static void p397_1() {
		MyUtil.printMethodName();
		
		LocalDate date1 = LocalDate.of(2017, 9, 21); // 2017-09-21
		LocalDate date2 = date1.plusWeeks(1); // 2017-09-28
		LocalDate date3 = date2.minusYears(6); // 2011-09-28
		LocalDate date4 = date3.plus(6, ChronoUnit.MONTHS); // 2012-03-28
		System.out.println(date4);
		
	}
	
	/**
	 * 예제 12-5 Duration과 Period
	 */
	private static void p395_1() {
		MyUtil.printMethodName();
		
		Duration threeMinutes = Duration.ofMinutes(3);
		Duration threeMinutes2 = Duration.of(3, ChronoUnit.MINUTES);
		
		Period tenDays = Period.ofDays(10);
		Period threeWeeks = Period.ofWeeks(3);
		Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
		
	}
	
	/**
	 * 예제 12-8 미리 정의된 TemporalAdjusters 사용하기
	 */
	private static void p398_1() {
		
		LocalDate date1 = LocalDate.of(2014, 3, 18);
		// 2014 03 23
		LocalDate date2 = date1.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
		// 2014 03 31
		LocalDate date3 = date2.with(TemporalAdjusters.lastDayOfMonth());
		
	}

	/**
	 * 
	 */
	private static void p402_1() {
		
		// 포메터로 문자열을 만드는 예제
		LocalDate date = LocalDate.of(2014, 3, 18);
		String s1 = date.format(DateTimeFormatter.BASIC_ISO_DATE); // 20140318
		String s2 = date.format(DateTimeFormatter.ISO_LOCAL_DATE); // 2014-03-18
		
		// 문자열을 파싱해서 날짜 객체를 만든다
		LocalDate date1 = LocalDate.parse("20140318", DateTimeFormatter.BASIC_ISO_DATE);
		LocalDate date2 = LocalDate.parse("2014-03-18", DateTimeFormatter.ISO_LOCAL_DATE);
		

		
	}
	
	/**
	 * 예제 12-10 패턴으로 DateTimeFormatter 만들기
	 */
	private static void p402_2() {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate date1 = LocalDate.of(2014, 3, 18);
		String formattedDate = date1.format(formatter);
		LocalDate date2 = LocalDate.parse(formattedDate, formatter);
		
	}
	
	/**
	 * 예제 12-11 지역화된 DateTimeFormatter 만들기
	 */
	private static void p402_3() {
		
		DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMM yyyy", Locale.ITALIAN);
		LocalDate date1 = LocalDate.of(2014, 3, 18);
		String formattedDate = date1.format(italianFormatter); // 18. marzo 2014
		LocalDate date2 = LocalDate.parse(formattedDate, italianFormatter);
		
	}
	
	/**
	 * 예제 12-13 특정 시점에 시간대 적용
	 */
	private static void p404_1() {
		
		ZoneId romeZone = ZoneId.of("Europe/Rome");
		
		LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
		ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
		
		LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
		ZonedDateTime zdt2 = dateTime.atZone(romeZone);
		
		Instant instant = Instant.now();
		ZonedDateTime zdt3 = instant.atZone(romeZone);
		
		
	}
	
	private static void useTemporalAdjuster() {
		MyUtil.printMethodName();

		LocalDate date = LocalDate.of(2014, 3, 18);
		date = date.with(nextOrSame(DayOfWeek.SUNDAY));
		System.out.println(date);
		date = date.with(lastDayOfMonth());
		System.out.println(date);

		date = date.with(new NextWorkingDay());
		System.out.println(date);
		date = date.with(nextOrSame(DayOfWeek.FRIDAY));
		System.out.println(date);
		date = date.with(new NextWorkingDay());
		System.out.println(date);

		date = date.with(nextOrSame(DayOfWeek.FRIDAY));
		System.out.println(date);
		date = date.with(temporal -> {
			DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
			int dayToAdd = 1;
			if (dow == DayOfWeek.FRIDAY) {
				dayToAdd = 3;
			}
			if (dow == DayOfWeek.SATURDAY) {
				dayToAdd = 2;
			}
			return temporal.plus(dayToAdd, ChronoUnit.DAYS);
		});
		System.out.println(date);
	}

	private static class NextWorkingDay implements TemporalAdjuster {

		@Override
		public Temporal adjustInto(Temporal temporal) {
			DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
			int dayToAdd = 1;
			if (dow == DayOfWeek.FRIDAY) {
				dayToAdd = 3;
			}
			if (dow == DayOfWeek.SATURDAY) {
				dayToAdd = 2;
			}
			return temporal.plus(dayToAdd, ChronoUnit.DAYS);
		}
	}

	private static void useDateFormatter() {
		MyUtil.printMethodName();
		LocalDate date = LocalDate.of(2014, 3, 18);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter italianFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.ITALIAN);

		System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
		System.out.println(date.format(formatter));
		System.out.println(date.format(italianFormatter));

		DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder().appendText(ChronoField.DAY_OF_MONTH)
				.appendLiteral(". ").appendText(ChronoField.MONTH_OF_YEAR).appendLiteral(" ")
				.appendText(ChronoField.YEAR).parseCaseInsensitive().toFormatter(Locale.ITALIAN);

		System.out.println(date.format(complexFormatter));
	}

}
