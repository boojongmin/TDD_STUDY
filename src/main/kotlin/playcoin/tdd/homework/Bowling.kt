package playcoin.tdd.homework

data class Frame(val stage: Int, var prev: Frame?, var next: Frame? = null, val rolls: MutableList<Roll> = mutableListOf()) {
    constructor(stage: Int, prev: Frame): this(stage, prev, null) { prev.next = this }
    fun addRoll(roll: Roll) = this.rolls.add(roll)
    fun frameScore(): Int = if (isStrike(this)) travRolls(rolls[0], 2)
                                else if (isSpare(this)) travRolls(rolls[0], 2)
                                else  rolls.map { it.score }.reduce { acc, i -> acc + i }

    private fun travRolls(roll: Roll?, repeat: Int): Int {
        if(roll != null && repeat > 0) {
            return roll.score + travRolls(roll?.next, repeat - 1)
        }
        return roll?.score ?: 0
    }
}

data class Roll(var frame: Frame?, var score: Int, var prev: Roll?, var next: Roll?) {
    init {
        frame?: throw IllegalStateException()
        frame!!.addRoll(this)
        prev?.next = this
    }
    override fun toString(): String = "Roll(${score})"
}

private fun isStrike(f: Frame): Boolean = f.rolls[0].score == 10
private fun isSpare(f:Frame): Boolean = f.rolls.size == 2 && f.rolls[0].score + f.rolls[1].score == 10
private fun isSecondRoll(f:Frame): Boolean = f.rolls.size == 2
private fun isNot10Frame(f:Frame): Boolean = f.stage != 10
private fun isNextFrame(f: Frame): Boolean = (isStrike(f) || isSpare(f) || isSecondRoll(f)) && isNot10Frame(f)
private fun hasBonusGame(f: Frame): Boolean = f.stage == 10 && f.rolls.size >= 2 && ((f.rolls[0].score + f.rolls[1].score) % 10 == 0 )
private fun hasNext(f: Frame): Boolean =f.stage < 10 || f.rolls.size < 2 ||((f.rolls[0].score + f.rolls[1].score) % 10 == 0)
private fun calcScore(f: Frame): Int = if (f.next == null) f.frameScore() else f.frameScore() + calcScore(f.next!!)

class BowlingGame {
    var stage = 1
    var frame: Frame? = null
    var roll: Roll? = null
    var firstFrame: Frame? = null

    private fun assertPinCount(pin: Int) { if (pin < 0 || 10 < pin) throw IllegalArgumentException() }

    private fun createRoll(pin: Int): Boolean {
        if (frame == null) {
            frame = Frame(stage, null)
            firstFrame = frame
        } else {
            if (isNextFrame(frame!!)) frame = Frame(++stage, frame!!)
        }
        val f = frame!!
        roll = Roll(f, pin, roll, null)
        assertFramePinCount(frame!!)
        if(f.stage == 10 &&
                ((hasBonusGame(f) == false && f.rolls.size > 2)
                    || (hasBonusGame(f) && f.rolls.size > 3))) throw IllegalStateException()
        return hasNext(f)
    }

    private fun assertFramePinCount(frame: Frame) {
        if(frame.rolls.size == 2) {
          val score = frame.rolls[0].score + frame.rolls[1].score
          if (frame.stage != 10 && (score in 0..10) == false) throw IllegalArgumentException()
          else if(frame.stage == 10 &&
                      ((frame.rolls[0].score == 10 && frame.rolls[1].score == 10) || (score in 0..10)) == false)
              throw IllegalArgumentException()
        }
    }

    fun roll(pin: Int): Boolean {
        assertPinCount(pin)
        return createRoll(pin)
    }

    fun score(): Int {
        return if (firstFrame == null) 0 else calcScore(firstFrame!!)
    }
}
