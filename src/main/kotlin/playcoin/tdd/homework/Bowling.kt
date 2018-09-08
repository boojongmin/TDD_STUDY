package playcoin.tdd.homework

var rolls = IntArray(21)
var index = 0
var frameIndex = 0
var hasNext = true
//var frameScore = IntArray(10)
//var calcFrameScore = IntArray(10)

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
    val frameScore = IntArray(10)
    for(i in rolls.indices ) {
        if (i < 18) {
            var frameIndex = i / 2
            frameScore[frameIndex] += rolls[i]
            // strike
            if (i % 2 == 0 && rolls[i] == 10) {
                if (rolls[i+2] == 10) {
                    frameScore[frameIndex] += 10
                    frameScore[frameIndex] += rolls[i+4]
                } else {
                    frameScore[frameIndex] += rolls[i+2]
                    frameScore[frameIndex] += rolls[i+3]
                }
            }
            // spare
            else if (i % 2 == 1 && rolls[i-1] < 10 && (rolls[i-1] + rolls[i] == 10)) {
                frameScore[frameIndex] += rolls[i+1]
            }
        // 10 프레임 이상
        } else {
            //ignore
        }
    }
    return frameScore.reduce {acc, i -> acc + i }
}