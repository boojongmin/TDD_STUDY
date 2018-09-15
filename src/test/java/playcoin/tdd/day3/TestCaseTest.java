package playcoin.tdd.day3;

public class TestCaseTest extends TestCase {

	public TestCaseTest(String name) {
		super(name);
	}

	public void testRunnng() {
		final WasRun test = new WasRun("testMethod");
		System.out.println(test.wasRun);
		test.run();
		System.out.println(test.wasRun);
//		AnnotationUtils.fin
	}

	//TODO assertTrue
	public static void main(String[] args) {
		new TestCaseTest("testRun").run();
	}
}
