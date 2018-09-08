package playcoin.tdd.homework

fun roll(pin: Int): Boolean {
    if( pin < 0 || 10 < pin) throw IllegalArgumentException()
    return true
}