package playcoin.tdd.homework

var rolls = IntArray(21)
var index = 0
var frameIndex = 0
var hasNext = true

fun roll(pin: Int): Boolean {
    if( pin < 0 || 10 < pin) throw IllegalArgumentException()
    if (index >= 21) throw IllegalStateException()
    if (hasNext == false) throw IllegalStateException()
    rolls[index] = pin
    if(frameIndex >= 9) {
        if (index == 19 ) {
            val firstAndSecondScore = rolls[index -1] + rolls[index]
            if (firstAndSecondScore != 20 && firstAndSecondScore > 10 ) throw IllegalArgumentException()
            hasNext = firstAndSecondScore % 10 == 0
        }
        index++
    } else {
        if(index % 2 == 1) {
            val frameScore = rolls[index - 1] + rolls[index]
            if (frameScore > 10) throw IllegalArgumentException()
        }
        val isStrike = pin == 10
        if (isStrike) index += 2 else index++
    }
    if (frameIndex < 9 && index % 2 == 0) frameIndex++
    return hasNext
}

fun score(): Int {
    return 0
}