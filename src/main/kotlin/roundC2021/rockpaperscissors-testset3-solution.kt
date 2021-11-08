package roundC2021

fun main() {
    val days = readLine()!!.toInt()
    readLine()
    (1..days).forEach { dayIndex ->
        val (W, E) = readLine()!!.split(" ").map { it.toInt() }
        println("Case #$dayIndex: ${getSequence(W, E)}")
    }
}

val sequenceLength = 60

private fun getSequence(W: Int, E: Int): String {
    val v = Array(sequenceLength + 1) { Array(sequenceLength + 1) { DoubleArray(sequenceLength + 1) } }
    val steps =
        Array(sequenceLength + 1) { Array(sequenceLength + 1) { Array(sequenceLength + 1) { intArrayOf(0, 0, 0) } } }
    v[1][0][0] = 1.0 / 3 * (W + E)
    v[0][1][0] = 1.0 / 3 * (W + E)
    v[0][0][1] = 1.0 / 3 * (W + E)
    steps[1][0][0] = intArrayOf(-1, 0, 0)
    steps[0][1][0] = intArrayOf(0, -1, 0)
    steps[0][0][1] = intArrayOf(0, 0, -1)
    var bestCombination = intArrayOf(0, 0, 1)
    var bestCombinationValue = v[0][0][1]
    (0..sequenceLength).forEach { r ->
        (0..sequenceLength - r).forEach { p ->
            (0..sequenceLength - r - p).forEach { s ->
                val n = r + p + s
                if (n >= 2) {
                    val (newValue, step) = arrayOf(
                        (if (r > 0) v[r - 1][p][s] + p.toDouble() * W / (n - 1) + s.toDouble() * E / (n - 1) else -1.0) to intArrayOf(
                            -1,
                            0,
                            0
                        ),
                        (if (p > 0) v[r][p - 1][s] + s.toDouble() * W / (n - 1) + r.toDouble() * E / (n - 1) else -1.0) to intArrayOf(
                            0,
                            -1,
                            0
                        ),
                        (if (s > 0) v[r][p][s - 1] + r.toDouble() * W / (n - 1) + p.toDouble() * E / (n - 1) else -1.0) to intArrayOf(
                            0,
                            0,
                            -1
                        ),
                    ).maxByOrNull { pair -> pair.first } ?: (-1.0 to intArrayOf(0, 0, 0))
                    v[r][p][s] = newValue
                    steps[r][p][s] = step
                    if (n == sequenceLength && newValue > bestCombinationValue) {
                        bestCombination = intArrayOf(r, p, s)
                        bestCombinationValue = newValue
                    }
                }
            }
        }
    }
    var currentStep = bestCombination
    val resultString = StringBuilder()
    while (!currentStep.contentEquals(intArrayOf(0, 0, 0))) {
        val (r, p, s) = currentStep
        val (dr, dp, ds) = steps[r][p][s]
        resultString.append(
            when {
                dr == -1 -> "R"
                dp == -1 -> "P"
                ds == -1 -> "S"
                else -> ""
            }
        )
        currentStep = intArrayOf(r + dr, p + dp, s + ds)
    }
    return resultString.toString().reversed()
}
