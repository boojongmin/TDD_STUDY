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

fun goTo10Frame() {
    (0..8).forEach {
        game.roll(10)
    }
}

var game: BowlingGame = BowlingGame()

class `볼링 프로그램 테스트` {
    @BeforeEach
    fun before() {
        game = BowlingGame()
    }

    @Nested
    inner class `roll 메서드 테스트` {

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
                    assertThat(game.roll(0)).isTrue()
                }

                @Test
                fun `(roll(-1) || roll(11)) then throw IllegalArgumentException`() {
                    assertThatThrownBy{ game.roll(-1) }.isInstanceOf(IllegalArgumentException::class.java)
                    assertThatThrownBy{ game.roll(11) }.isInstanceOf(IllegalArgumentException::class.java)
                }
            }

            @Nested
            inner class `프레임 테스트` {
                @Test
                @DisplayName("0 <= first + second <= 10, or IllegalArgumentException")
                fun `test01`() {
                    assertThatThrownBy {
                        game.roll(9)
                        game.roll(2)
                    }.isInstanceOf(IllegalArgumentException::class.java)

                    // reset
                    game = BowlingGame()
                    assertThatThrownBy {
                        game.roll(0)
                        game.roll(0)
                        game.roll(9)
                        game.roll(2)
                    }.isInstanceOf(IllegalArgumentException::class.java)
                }

                @Test
                @DisplayName("0 <= farameIndex <= 9")
                fun `test02`() {
                    assertThatThrownBy {
                        game.roll(9)
                        game.roll(2)
                    }.isInstanceOf(IllegalArgumentException::class.java)
                    // Exception 때문에 index 증가를하지 않음.

                }

                @Test
                @DisplayName("(10 frame)when strike 3 and roll(pin) then expect IllegalStateException")
                fun `test03`() {
                    goTo10Frame()
                    game.roll(10)
                    game.roll(10)
                    game.roll(10)
                    assertThatThrownBy {
                        game.roll(0)
                    }.isInstanceOf(IllegalStateException::class.java)
                }
            }

            @Nested
            inner class `bouns role 계산` {
                @Test
                @DisplayName("role(10) REPEAT 9 + role(9) then role(2) then expect IllegalArgumentException")
                fun `test01`() {
                    goTo10Frame()
                    game.roll(9)
                    assertThatThrownBy {
                        game.roll(2)
                    }.isInstanceOf(IllegalArgumentException::class.java)
                }

                @Test
                @DisplayName("role(10) REPEAT 9 + role(10) then role(10): true")
                fun `test02`() {
                    goTo10Frame()
                    game.roll(10)
                    val isBonus = game.roll(10)
                    assertThat(isBonus).isTrue()
                }


                @Test
                @DisplayName("role(10) REPEAT 9 + role(5) then role(5): true")
                fun `test03`() {
                    goTo10Frame()
                    game.roll(5)
                    val isBonus = game.roll(5)
                    assertThat(isBonus).isTrue()
                }

                @Test
                @DisplayName("role(10) REPEAT 9 + role(5) then role(4): false")
                fun `test04`() {
                    goTo10Frame()
                    game.roll(5)
                    val isBonus = game.roll(4)
                    assertThat(isBonus).isFalse()
                }

                @Test
                @DisplayName("role(10) REPEAT 9 + role(10) then role(9): false")
                fun `test05`() {
                    goTo10Frame()
                    game.roll(5)
                    val isBonus = game.roll(5)
                    assertThat(isBonus).isTrue()
                }

                @Test
                @DisplayName("role(10) REPEAT 9 + role(10) then role(9): false")
                fun `test06`() {
                    goTo10Frame()
                    game.roll(5)
                    val isBonus = game.roll(5)
                    assertThat(isBonus).isTrue()
                }

                @Test
                @DisplayName("if(role(pin) == false) and role(pin) throw IllegalStateException")
                fun `test07`() {
                    goTo10Frame()
                    game.roll(5)
                    game.roll(4)
                    assertThatThrownBy { game.roll(0) }.isInstanceOf(IllegalStateException::class.java)
                }

            }

        }
    }

    @Nested
    inner class `score 메서드 테스트` {
        /**
         *  테스트 시나리오
         *  - roll REPEAT 0 score == 0
         *  - roll(pin) score == pin
         *  - roll(1) REPEAT 20  score == 20
         *  [desc] spare 테스트
         *  - roll(9), roll(1), roll(pin) then score == (10 + pin) + pin
         *  - roll(5), roll(5), roll(5), roll(5), roll(1) then score == 27
         *  [desc] strike 테스트
         *  - roll(10), roll(10), roll(10) then score (10 + 10 + 10) + (10 + 10 + 0) + (10 + 0 + 0) == 60
         *  [desc] 10 프레임 테스트
         *  - roll(10), roll(10), roll(10) then score (10 + 10 + 10) + (10 + 10 + 0) + (10 + 0 + 0) == 60
         */
        @Test
        @DisplayName("roll REPEAT 0 score == 0")
        fun test01() {
            assertThat(game.score()).isEqualTo(0)
        }

        @Test
        @DisplayName("roll(pin) score == pin")
        fun test02() {
            val pin = 1
            game.roll(pin)
            assertThat(game.score()).isEqualTo(pin)
        }

        @Test
        @DisplayName("game.roll(1) REPEAT 20  score == 20")
        fun test03() {
            (0..19).forEach { game.roll(1) }
            assertThat(game.score()).isEqualTo(1 * 20)
//            assertThatThrownBy { (0..20).forEach { game.roll(1) } }.isInstanceOf(IllegalStateException::class.java)
        }

        @Test
        @DisplayName("roll(1) REPEAT 20  score == 20")
        fun test04() {
            val pin = 1
            (0..19).forEach { game.roll(1) }
            assertThat(game.score()).isEqualTo(1 * 20)
            assertThatThrownBy { (0..20).forEach { game.roll(1) } }.isInstanceOf(IllegalStateException::class.java)
        }

        @Nested
        inner class `spare 테스트` {
            @Test
            @DisplayName("roll(9), roll(1), roll(pin) then score == (10 + pin) + pin")
            fun test01() {
                game.roll(9)
                game.roll(1)
                //spare
                game.roll(1)
                assertThat(game.score()).isEqualTo(12)
            }

            @Test
            @DisplayName("roll(5), roll(5), roll(5), roll(5), roll(1) then score == 27")
            fun test02() {
                game.roll(5)
                game.roll(5)
                //spare
                game.roll(5)
                game.roll(5)
                //spare
                game.roll(1)
                assertThat(game.score()).isEqualTo(27)
            }
        }

        @Nested
        inner class `strike 테스트` {
            @Test
            @DisplayName("roll(10), roll(10), roll(10) then score (10 + 10 + 10) + (10 + 10 + 0) + (10 + 0 + 0) == 60")
            fun test01() {
                game.roll(10)
                game.roll(10)
                game.roll(10)
                assertThat(game.score()).isEqualTo(60)
            }

            @Test
            @DisplayName("roll(10), roll(10), roll(5), roll(4)  then score 25 + 19 + 5 + 4 == 53")
            fun test02() {
                game.roll(10)
                game.roll(10)
                game.roll(5)
                game.roll(4)
                assertThat(game.score()).isEqualTo(53)
            }
        }

        @Nested
        inner class `10 프레임 테스트` {
            @Test
            @DisplayName("roll(10) REPEAT 12  then score 300")
            fun test01() {
                (0..11).forEach { game.roll(10) }
                assertThat(game.score()).isEqualTo(300)
            }

            @Test
            @DisplayName("game.roll(10) REPEAT 12  then score 300")
            fun test02() {
                (0..10).forEach { game.roll(10) }
                game.roll(1)
                assertThat(game.score()).isEqualTo(291)
            }

            @Test
            @DisplayName("roll(10) REPEAT 9 and roll(5) roll(5) roll(1)  then score 300")
            fun test03() {
                (0..8).forEach { game.roll(10) }
                game.roll(5)
                game.roll(5)
                game.roll(1)
                assertThat(game.score()).isEqualTo(266)
            }
            @Test
            @DisplayName("roll(10) REPEAT 9 and roll(5) roll(4) roll(pin)  then expect IllegalStateException")
            fun test04() {
                (0..8).forEach { game.roll(10) }
                game.roll(5)
                game.roll(4)
                assertThatThrownBy { game.roll(1) }.isInstanceOf(IllegalStateException::class.java)
            }
        }
    }
}

