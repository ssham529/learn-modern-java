package myutil;

public class MyUtil {

	public static void printMethodName(String memo) {
		StackTraceElement[] ste = new Throwable().getStackTrace();
//		System.out.println("1 - 1 " + " ==> class : [" + ste[1].getClassName() + "] [Method : " +            ste[1].getMethodName() + "] [Line : " + ste[1].getLineNumber() + "]");
		System.out.println("================ " + ste[1].getMethodName() + " :: " + memo + " =================");
	}
	
	public static void printMethodName() {
		StackTraceElement[] ste = new Throwable().getStackTrace();
		System.out.println("================ " + ste[1].getMethodName() + " =================");
	}
	
	public static void printCallerInfo() {
		StackTraceElement[] ste = new Throwable().getStackTrace();
		System.out.println("1 " + " ==> class : [" + ste[2].getClassName() + "] [Method : " +            ste[2].getMethodName() + "] [Line : " + ste[2].getLineNumber() + "]");
//		System.out.println("1 " + " ==> class : [" + ste[3].getClassName() + "] [Method : " +            ste[3].getMethodName() + "] [Line : " + ste[3].getLineNumber() + "]");
	}
}
