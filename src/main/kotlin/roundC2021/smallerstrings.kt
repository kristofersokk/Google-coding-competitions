package roundC2021

import kotlin.math.max
import kotlin.math.min

fun main() {
    val cases = readLine()!!.toInt()
    (1..cases).forEach { caseIndex ->
        val (N, K) = readLine()!!.split(" ").map { it.toLong() }
        val S = readLine()!!

        if (N == 1L) {
            val y = max(min((S[0].letterNr).code.toLong(), K), 0)
            println("Case #$caseIndex: $y")
        } else {
            val maxi = (N.toInt() - 1) / 2
            val result = algorithm(S, K, 0, maxi)
            println("Case #$caseIndex: $result")
        }
    }
}

private fun Long.modPow(power: Long, mod: Long): Long {
    if (power == 0L) {
        return 1L
    }
    if (power == 1L) {
        return this % mod
    }
    val halfPower = this.modPow(power / 2L, mod)
    return if (power % 2 == 0L) {
        (halfPower * halfPower) % mod
    } else {
        val halfPlusOnePower = (halfPower * this) % mod
        (halfPower * halfPlusOnePower) % mod
    }
}

private fun algorithm(S: String, K: Long, index: Int, maxi: Int): Long {
    if (index > maxi) {
        return 0
    }
    val possibleSmallerLetters = max(min((S[index].letterNr).code.toLong(), K), 0)
    var result = (possibleSmallerLetters * K.modPow((maxi - index).toLong(), 1000000007)).appropriateMod()
    if (K >= S[index].letterNr.code.toLong()) {
        if (index == maxi) {
            if (S.length % 2 == 0) {
                val half = S.substring(0..index)
                if (half + half.reversed() < S) {
                    result += 1
                }
            } else {
                val half = S.substring(0 until index)
                if (half + S[index] + half.reversed() < S) {
                    result += 1
                }
            }
        } else {
            result = (result + algorithm(S, K, index + 1, maxi)).appropriateMod()
        }
    }
    return result.appropriateMod()
}

private val Char.letterNr: Char
    get() = this - 97

private fun Long.appropriateMod() =
    ((this % 1000000007) + 1000000007) % 1000000007
