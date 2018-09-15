package playcoin.tdd.homework01

sealed class A
data class BBB(val a: Int): A()
data class CCC(val a: Int, val b: Int): A()

fun main(args: Array<String>) {
    val bbb: A = BBB(1)

    when(bbb) {
        is BBB -> {
            val (a) = bbb
            println(a)
        }
        is CCC -> {
            val (a, b) = bbb
             println("${a} - ${b}")
        }
    }
}


