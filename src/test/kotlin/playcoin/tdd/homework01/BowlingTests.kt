package playcoin.tdd.homework01


import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import playcoin.tdd.homework.*


/**
 *

볼링 점수 계산 - http://stparkms.tistory.com/62

- roll(int n)과 score()를 구현
- roll(n: int): boolean
- n : 공을 굴린후 쓰러트린 핀의 갯수
- return value : 더이상 roll을 할 수 있는지 여부를 반환
- score(): int
- 내부적으로 각 프레임(1-10)의 점수를 관리할 것
- spare, strike 점수 구현
- 범위를 초과했거나 횟수를 초과하여 roll을 호출하면 IllegalArgumentException과 IllegalStateException 발생
- 최저 점수와 최대 점수까지 테스트에 반드시 반영
- 새 테스트 통과마다 commit 해서 github에 올릴 것

 */
fun reset() {
    rolls  = IntArray(21)
    index = 0
    frameIndex = 0
    hasNext = true
}

fun goTo10Frame() {
    (0..8).forEach {
        roll(10)
    }
}

class `볼링 프로그램 테스트` {
    @Nested
    inner class `roll 메서드 테스트` {
        @BeforeEach
        fun before() {
            reset()
        }

        /**
         * 테스트 시나리오
         * [desc] roll 단일 실행 테스트
         * - roll(0) then return true
         * - (roll(-1) || roll(11)) then throw IllegalArgumentException
         *
         * [desc] 프레임 테스트
         * [desc] `rolls` is int[index], max index is 20
         * [desc] (index % 2 == 0) is `first` of a frame, (index %2 == 1) is `second` of a frame
         * - 0 <= first + second <= 10, or IllegalArgumentException
         * - [조건] if( 0 <= first <= 9) then index += 1
         * - [조건] first == 10 strike : index += 2
         * - [조건] index / 2 == frameIndex
         * - 0 <= farameIndex <= 9
         * [desc] 10프레임 테스트
         * [desc] `pin`은 0~10까지의 임의 숫자
         * - if frameIndex is 9
         * - if( 0 <= first <= 9) then index += 1
         * - strike => index +1
         * - (10 frame)when strike 3 and roll(pin) then expect IllegalStateException
         * [desc] bouns role 계산
         * - index == 20 and (9frame first pin + second pin % 10 == (0: strike or spare))
         * - role(10) REPEAT 9 + role(10) then role(10): true
         * - role(10) REPEAT 9 + role(5) then role(5): true
         * - role(10) REPEAT 9 + role(5) then role(4): false
         * - role(10) REPEAT 9 + role(10) then role(9): false
         * - role(10) REPEAT 9 + role(9) then role(2) then expect IllegalArgumentException  <== pin1 + pin2 < 10
         * - if(role(pin) == false) and role(pin) throw IllegalStateException
         */
        @Nested
        inner class `roll` {

            @Nested
            inner class `roll 단일 실행 테스트` {
                @Test
                fun `roll(0) then return true`() {
                    assertThat(roll(0)).isTrue()
                }

                @Test
                fun `(roll(-1) || roll(11)) then throw IllegalArgumentException`() {
                    assertThatThrownBy{ roll(-1) }.isInstanceOf(IllegalArgumentException::class.java)
                    assertThatThrownBy{ roll(11) }.isInstanceOf(IllegalArgumentException::class.java)
                }
            }

            @Nested
            inner class `프레임 테스트` {
                @Test
                @DisplayName("0 <= first + second <= 10, or IllegalArgumentException")
                fun `test01`() {
                    assertThatThrownBy {
                        roll(9)
                        roll(2)
                    }.isInstanceOf(IllegalArgumentException::class.java)
                    // Exception 때문에 index 증가를하지 않음.
                    reset()
                    assertThatThrownBy {
                        roll(0)
                        roll(0)
                        roll(9)
                        roll(2)
                    }.isInstanceOf(IllegalArgumentException::class.java)
                }

                @Test
                @DisplayName("0 <= farameIndex <= 9")
                fun `test02`() {
                    assertThatThrownBy {
                        roll(9)
                        roll(2)
                    }.isInstanceOf(IllegalArgumentException::class.java)
                    // Exception 때문에 index 증가를하지 않음.

                }

                @Test
                @DisplayName("(10 frame)when strike 3 and roll(pin) then expect IllegalStateException")
                fun `test03`() {
                    goTo10Frame()
                    roll(10)
                    roll(10)
                    roll(10)
                    assertThatThrownBy {
                        roll(0)
                    }.isInstanceOf(IllegalStateException::class.java)
                }
            }

            @Nested
            inner class `bouns role 계산` {
                @Test
                @DisplayName("role(10) REPEAT 9 + role(9) then role(2) then expect IllegalArgumentException")
                fun `test01`() {
                    goTo10Frame()
                    roll(9)
                    assertThatThrownBy {
                        roll(2)
                    }.isInstanceOf(IllegalArgumentException::class.java)
                }

                @Test
                @DisplayName("role(10) REPEAT 9 + role(10) then role(10): true")
                fun `test02`() {
                    goTo10Frame()
                    roll(10)
                    val isBonus = roll(10)
                    assertThat(isBonus).isTrue()
                }


                @Test
                @DisplayName("role(10) REPEAT 9 + role(5) then role(5): true")
                fun `test03`() {
                    goTo10Frame()
                    roll(5)
                    val isBonus = roll(5)
                    assertThat(isBonus).isTrue()
                }

                @Test
                @DisplayName("role(10) REPEAT 9 + role(5) then role(4): false")
                fun `test04`() {
                    goTo10Frame()
                    roll(5)
                    val isBonus = roll(4)
                    assertThat(isBonus).isFalse()
                }

                @Test
                @DisplayName("role(10) REPEAT 9 + role(10) then role(9): false")
                fun `test05`() {
                    goTo10Frame()
                    roll(5)
                    val isBonus = roll(5)
                    assertThat(isBonus).isTrue()
                }

                @Test
                @DisplayName("role(10) REPEAT 9 + role(10) then role(9): false")
                fun `test06`() {
                    goTo10Frame()
                    roll(5)
                    val isBonus = roll(5)
                    assertThat(isBonus).isTrue()
                }

                @Test
                @DisplayName("if(role(pin) == false) and role(pin) throw IllegalStateException")
                fun `test07`() {
                    goTo10Frame()
                    roll(5)
                    roll(4)
                    assertThatThrownBy { roll(0) }.isInstanceOf(IllegalStateException::class.java)
                }

            }

        }
    }

    @Nested
    inner class `score 메서드 테스트` {
        @BeforeEach
        fun before() {
            reset()
        }

        /**
         *  테스트 시나리오
         *  - roll REPEAT 0 score == 0
         *  - roll(pin) score == pin
         */
        @Test
        @DisplayName("roll REPEAT 0 score == 0")
        fun test01() {
            assertThat(score()).isEqualTo(0)
        }

        @Test
        @DisplayName("roll(pin) score == pin")
        fun test02() {
            val pin = 1
            roll(pin)
            assertThat(score()).isEqualTo(pin)
        }
    }

}

