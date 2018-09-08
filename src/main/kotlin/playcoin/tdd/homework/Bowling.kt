package playcoin.tdd.homework

var roles = IntArray(21)
var index = 0
var frameIndex = 0

fun roll(pin: Int): Boolean {
    var hasNext = true
    if( pin < 0 || 10 < pin) throw IllegalArgumentException()
    if (index >= 21) throw IllegalStateException()
    roles[index] = pin
    if(frameIndex >= 9) {
        if (index == 19 ) {
            val firstAndSecondScore = roles[index -1] + roles[index]
            if (firstAndSecondScore != 20 && firstAndSecondScore > 10 ) throw IllegalArgumentException()
            hasNext = firstAndSecondScore % 10 == 0
        }
        index++
    } else {
        if(index % 2 == 1) {
            val frameScore = roles[index - 1] + roles[index]
            if (frameScore > 10) throw IllegalArgumentException()
        }
        val isStrike = pin == 10
        if (isStrike) index += 2 else index++
    }
    if (frameIndex < 9 && index % 2 == 0) frameIndex++
    return hasNext
}