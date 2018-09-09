package playcoin.tdd.homework

import java.util.*

data class Frame(val stage: Int, var prev: Frame? = null, var next: Frame? = null, var score: Int = 0, val rolls: MutableList<Roll> = mutableListOf()) {
    fun addRoll(roll: Roll) = this.rolls.add(roll)
    fun frameScore(): Int = if (isStrike(this)) getScore(rolls[0], 2)
                                else if (isSpare(this))  getScore(rolls[0], 1)
                                else rolls[0].score + rolls[1].score

    private fun getScore(roll: Roll?, repeat: Int): Int {
        if(roll != null &&repeat > 0) {
            return roll.score + getScore(roll?.next, repeat - 1)
        }
        return roll?.score ?: 0
    }
}

data class Roll(var frame: Frame?, var score: Int, var prev: Roll? = null, var next: Roll? = null) {
    constructor(frame: Frame?, score: Int): this(frame, score, null, null) {
        frame ?: throw IllegalStateException()
        frame.addRoll(this)
    }
    override fun toString(): String = """Roll(${score})"""
}

fun isStrike(f: Frame): Boolean = f.rolls[0].score == 10
fun isSpare(f:Frame): Boolean = f.rolls.size == 2 && f.rolls[0].score + f.rolls[1].score == 10
fun isSecondRoll(f:Frame): Boolean = f.rolls.size == 2
fun isNot10Frame(f:Frame): Boolean = f.stage != 10
fun isNextFrame(f: Frame): Boolean = (isStrike(f) || isSpare(f) || isSecondRoll(f)) && isNot10Frame(f)
fun hasBonusGame(f: Frame): Boolean = f.stage == 10 && f.rolls.size >= 2 && ((f.rolls[0].score + f.rolls[1].score) % 10 == 0 )
fun hasNext(f: Frame): Boolean =f.stage < 10 || f.rolls.size < 2 ||((f.rolls[0].score + f.rolls[1].score) % 10 == 0)

class BowlingGame {
    var stage = 1
    // 1frame의 첫구는 초기화를 해둠
    var frame: Frame? = null
    var roll: Roll? = null

    private fun assertPinCount(pin: Int) { if (pin < 0 || 10 < pin) throw IllegalArgumentException() }
    private fun createRoll(pin: Int): Boolean {
        if (frame == null) {
            frame = Frame(stage)
        } else {
            if (isNextFrame(frame!!)) frame = Frame(++stage, frame)
        }
        var f = frame!!
        roll = Roll(f, pin)
        assertFramePinCount(frame!!)
        if(f!!.stage == 10 &&
                ((hasBonusGame(f) == false && f.rolls.size > 2)
                    || (hasBonusGame(f) && f.rolls.size > 3))) throw IllegalStateException()
        return hasNext(f)
//        return !(frame.stage == 10 &&
//                ((hasBonusGame(frame) && frame.rolls.size == 2)
//                    || (hasBonusGame(frame) && frame.rolls.size == 3)))
    }
    private fun assertFramePinCount(frame: Frame) {
        if(frame.rolls.size == 2) {
          val score = frame.rolls[0].score + frame.rolls[1].score
          if (frame.stage != 10 && (score >= 0 && score <= 10) == false) throw IllegalArgumentException()
          else if(frame.stage == 10 &&
                      ((frame.rolls[0].score == 10 && frame.rolls[1].score == 10) || (score >= 0 && score <= 10)) == false)
              throw IllegalArgumentException()
        }
    }

    fun roll(pin: Int): Boolean {
        assertPinCount(pin)
        return createRoll(pin)
//        rolls[index] = pin
//        if(frameIndex >= 9) {
//            if (index == 19 ) {
//                val firstAndSecondScore = rolls[index -1] + rolls[index]
//                if (firstAndSecondScore != 20 && firstAndSecondScore > 10 ) throw IllegalArgumentException()
//                hasNext = firstAndSecondScore % 10 == 0
//            }
//            index++
//        } else {
//            if(index % 2 == 1) {
//                val frameScore = rolls[index - 1] + rolls[index]
//                if (frameScore > 10) throw IllegalArgumentException()
//            }
//            val isStrike = pin == 10
//            if (isStrike) index += 2 else index++
//        }
//        if (frameIndex < 9 && index % 2 == 0) frameIndex++
//        return true
    }


//    fun score(): Int {
//        val frameScore = IntArray(10)
//        for(i in rolls.indices ) {
//            if (i < 18) {
//                var frameIndex = i / 2
//                frameScore[frameIndex] += rolls[i]
//                // strike
//                if (i % 2 == 0 && rolls[i] == 10) {
//                    if (rolls[i+2] == 10 && i < 16) {
//                        frameScore[frameIndex] += 10
//                        frameScore[frameIndex] += rolls[i+4]
//                    } else {
//                        frameScore[frameIndex] += rolls[i+2]
//                        frameScore[frameIndex] += rolls[i+3]
//                    }
//                }
//                // spare
//                else if (i % 2 == 1 && rolls[i-1] < 10 && (rolls[i-1] + rolls[i] == 10)) {
//                    frameScore[frameIndex] += rolls[i+1]
//                }
//            // 10 프레임 이상
//            } else {
//                frameScore[9] += rolls[i]
//            }
//        }
//        return frameScore.reduce {acc, i -> acc + i }
//    }
}
