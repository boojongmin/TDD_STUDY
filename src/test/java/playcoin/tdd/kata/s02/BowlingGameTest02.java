package playcoin.tdd.kata.s02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BowlingGameTest02 {
	BowlingGame02 g;
	@BeforeEach
	void before() {
		g = new BowlingGame02();

	}
	@Test
	void testGutterGame() {
		rollMany(20, 0);
		assertThat(g.score()).isEqualTo(0);
	}

	private void rollMany(int n, int pin) {
		for (int i = 0; i < n; i++) {
			g.roll(pin);
		}
	}

	@Test
	void testAllOnes() {
		rollMany(20, 1);
		assertThat(g.score()).isEqualTo(20);
	}

	@Test
	void testOneSpare() {
		rollSpare();
		g.roll(3);
		rollMany(17, 0);
		assertThat(g.score()).isEqualTo(16);
	}

	@Test
	void testStrike() {
		rollStrike();
		g.roll(3);
		g.roll(4);
		rollMany(16, 0);
		assertThat(g.score()).isEqualTo(24);

	}

	@Test
	void testPerfectGame() {
		rollMany(12, 10);
		assertThat(g.score()).isEqualTo(300);
	}

	private void rollStrike() {
		g.roll(10);
	}

	private void rollSpare() {
		g.roll(5);
		g.roll(5); //spare
	}

}
