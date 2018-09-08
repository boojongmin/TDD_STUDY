package playcoin.tdd.homework

var roles = IntArray(21)
var index = 0

fun roll(pin: Int): Boolean {
    if( pin < 0 || 10 < pin) throw IllegalArgumentException()
    roles[index] = pin
    if(index % 2 == 1)  {
        val frameScore = roles[index -1] + roles[index]
        if (frameScore > 10) throw IllegalArgumentException()
    }
    index++
    return true
}