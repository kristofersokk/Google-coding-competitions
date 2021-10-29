package roundC2021

import kotlin.math.ceil
import kotlin.math.pow

fun main() {
    val cases = readLine()!!.toInt()
    (1..cases).forEach { caseIndex ->
        val G = readLine()!!.toLong()
        var result = 0L
        for (i in 1..ceil((2 * G).toDouble().pow(0.5) - 0.5).toLong()) {
            val numerator = 2 * G - i * i + i
            if (numerator > 0 && numerator % (2 * i) == 0L) {
//                println("K: ${upper / (2 * i)}")
                result += 1
            }
        }
        println("Case #$caseIndex: $result")
    }
}