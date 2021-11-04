package roundA2021

import kotlin.math.abs

fun main() {
    val cases = readLine()!!.toInt()
    for (j in 1..cases) {
        val (N, K) = readLine()!!.split(" ").map { it.toInt() }
        val S = readLine()!!
        var runningK = 0
        for (i in 0 until (N / 2)) {
            if (S[i] != S[S.length - 1 - i]) {
                runningK++
            }
        }
        println("Case #$j: ${abs(K - runningK)}")
    }
}
