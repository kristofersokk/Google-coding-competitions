package roundC2021

import kotlin.math.ceil
import kotlin.math.pow

fun main() {
    val cases = readLine()!!.toInt()
    (1..cases).forEach { caseIndex ->
        val G = readLine()!!.toLong()
        val result = (1..ceil((2 * G).toDouble().pow(0.5) - 0.5).toLong()).count { i ->
            val numerator = 2 * G - i * i + i
            numerator > 0 && numerator % (2 * i) == 0L
        }
        println("Case #$caseIndex: $result")
    }
}