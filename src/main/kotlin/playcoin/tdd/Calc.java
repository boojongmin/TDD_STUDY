package playcoin.tdd;

public class Calc {
	public static int sum1(String s) {
		return 1;
	}

	public static int sum2(String s) {
		return Integer.parseInt(s);
	}

	public static int sum3(String...s) {
		if(s.length == 0) throw new IllegalArgumentException();
		return Integer.parseInt(s[0]);
	}

	public static int sum4(String... s) {
		if(s.length == 0) throw new IllegalArgumentException();
		return Integer.parseInt(s[0]) + Integer.parseInt(s[1]);
	}

	public static int sum5(String... s) {
		if(s.length == 0) throw new IllegalArgumentException();
		int result =0;
		for(int i=0; i<s.length; i++) {
			result += Integer.parseInt(s[i]);
		}
		return result;
	}

	public static int sum6(String...s) {
		if(s.length == 0) throw new IllegalArgumentException();
		int result =0;
		for(int i=0; i<s.length; i++) {
			result += Integer.parseInt(s[i]);
		}
		return result;
	}

	public static int sum7(String... s) {
		if(s.length == 0) throw new IllegalArgumentException();
		int sum =0;
		for(int i=0; i<s.length; i++) {
			int val = Integer.parseInt(s[i]);
			if(val <= 0 || 1000 <= val) { throw new IllegalArgumentException(); }
			sum += val;
		}
		return sum;

	}
}
