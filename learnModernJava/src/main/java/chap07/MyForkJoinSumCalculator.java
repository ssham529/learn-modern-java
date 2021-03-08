package chap07;


import java.util.concurrent.ForkJoinTask;
import java.util.stream.LongStream;


public class MyForkJoinSumCalculator extends java.util.concurrent.RecursiveTask<Long> {

	private final long[] numbers;
	private final int start;
	private final int end;
	public static final long THRESHOLD = 10_000;

	public MyForkJoinSumCalculator(long[] numbers) {
		this(numbers, 0, numbers.length);
	}

	private MyForkJoinSumCalculator(long[] numbers, int start, int end) {
		this.numbers = numbers;
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		
	    int length = end - start;
	    if (length <= THRESHOLD) {
	      return computeSequentially();
	    }
	    MyForkJoinSumCalculator leftTask = new MyForkJoinSumCalculator(numbers, start, start + length / 2);
	    leftTask.fork();
	    MyForkJoinSumCalculator rightTask = new MyForkJoinSumCalculator(numbers, start + length / 2, end);
	    Long rightResult = rightTask.compute();
	    Long leftResult = leftTask.join();
	    return leftResult + rightResult;
	}

	private long computeSequentially() {
		long sum = 0;
		for (int i = start; i < end; i++) {
			sum += numbers[i];
		}
		return sum;
	}
	
	public static long forkJoinSum(long n) {
		long[] numbers = LongStream.rangeClosed(1, n).toArray();
		ForkJoinTask<Long> task = new MyForkJoinSumCalculator(numbers);
		return MyParallelStreamsHarness.FORK_JOIN_POOL.invoke(task);
	}

}
