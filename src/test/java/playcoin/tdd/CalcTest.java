package playcoin.tdd;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


/**
 * 실패 테스트 시나리오
 * ["1", "2", "3"] => 13
 * []["1", ...] => IllegaAgumentExceptin
 * <p>
 * ["a", 1] => IllegaAgumentExceptin
 * <p>
 * 정상 테스트 시나리오
 * sum1 "1" => 1
 * sum1 "2" => 2
 */
class CalcTest {
	@Test
	@DisplayName("하나의 값을 넣으면 입력값을 그대로 반환한다.")
	void onNumber() {
		assertThat(Calc.sum1("1")).isEqualTo(1);
		assertThat(Calc.sum2("2")).isEqualTo(2);
	}

	@Test
	@DisplayName("입력값이 없는 경우 IllegaArgumentException을 반환한다.")
	void emptyShouldFail() {
		assertThatThrownBy(() -> {
			Calc.sum3();
		}).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("두개의 값을 더하면 두 값의 합을 반환한다.")
	void twoNumbers() {
		assertThat(Calc.sum4("2", "3")).isEqualTo(5);
	}

	@Test
	@DisplayName("셋 이상의 값을 입력시 입력값들의 합을 반환한다.")
	void manyNumbers() {
		assertThat(Calc.sum5("2", "3", "4")).isEqualTo(9);
		assertThat(Calc.sum5("2", "3", "4", "5")).isEqualTo(14);
	}

	@Test
	@DisplayName("숫자가 아닌 값을 입력시 IllegaArgumentException을 반환한다.")
	void noNumbers() {
		assertThatThrownBy(() -> Calc.sum6("a")).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	@DisplayName("0 이하, 1000 이상의 값을 입력시 IllegaArgumentException을 반환한다.")
	void outOfRange() {
		assertThatThrownBy(() -> Calc.sum7("0")).isInstanceOf(IllegalArgumentException.class);
		assertThatThrownBy(() -> Calc.sum7("1000")).isInstanceOf(IllegalArgumentException.class);
	}
}

