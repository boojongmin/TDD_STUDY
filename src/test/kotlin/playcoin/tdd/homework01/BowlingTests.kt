package playcoin.tdd.homework01


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import playcoin.tdd.homework.roll


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
class `볼링 프로그램 테스트` {

    @Nested
    inner class `roll 메서드 테스트` {

        /**
         * 테스트 시나리오
         * [desc] roll 단일 실행 테스트
         * - roll(0) then return true
         * - (roll(-1) || roll(11)) then throw IllegalArgumentException
         *
         * [desc] 프레임 테스트
         * [desc] `roles` is int[21]
         * [desc] (index % 2 == 0) is `first` of a frame, (index %2 == 1) is `second` of a frame
         * - 0 <= first + second <= 10, or IllegalArgumentException
         * - 0 <= first <= 9 : index += 1
         * - first == 10 strike : index += 2
         * - index / 2 == frameIndex
         * - 0 <= farameIndex <= 9
         * [desc] bouns role 계산
         * [desc] `pin`은 0~10까지의 임의 숫자
         * - index == 21 and (9frame first pin + second pin % 10 == (0: strike or spare))
         * - role(10) REPEAT 9 + role(5) then role(4): false
         * - role(10) REPEAT 9 + role(5) then role(5): true
         * - role(10) REPEAT 10 then role(0): false
         * - role(10) REPEAT 10 then role(10) : true
         * - role(10) REPEAT 10 then role(10): true
         * - role(10) REPEAT 12 : false
         * - role(10) REPEAT 9 then role(pin1) + role(pin2) : false   <== pin1 + pin2 < 10
         * [desc] 보너스룰이 안되는 상황 role(pin): false 인 경우
         * - if(role(pin) == false) role(pin) throw IllegalStateException
         */
        @Nested
        inner class `roll` {
            @Test
            fun `roll(0) then return true`() {
                assertThat(roll(0)).isTrue();
            }
        }
    }

}

