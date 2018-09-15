package playcoin.tdd.day3;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;

public class TestCase {
	public final String name;

	public boolean wasRun = false;

	public TestCase(String name) {
		this.name = name;
	}

	public void run() {
		this.wasRun = true;
		Method method = ReflectionUtils.findMethod(getClass(), name);
		ReflectionUtils.invokeMethod(method, this);
	}
}
