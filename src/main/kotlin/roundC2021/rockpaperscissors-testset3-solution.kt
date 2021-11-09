package roundC2021

fun main() {
    val days = readLine()!!.toInt()
    readLine()
    (1..days).forEach { dayIndex ->
        val (W, E) = readLine()!!.split(" ").map { it.toInt() }
        println("Case #$dayIndex: ${getSequence(W, E)}")
    }
}

const val SEQUENCE_LENGTH = 60

private fun getSequence(w: Int, e: Int): String {
    val v = Array(SEQUENCE_LENGTH + 1) { Array(SEQUENCE_LENGTH + 1) { DoubleArray(SEQUENCE_LENGTH + 1) } }
    val steps =
        Array(SEQUENCE_LENGTH + 1) { Array(SEQUENCE_LENGTH + 1) { Array(SEQUENCE_LENGTH + 1) { intArrayOf(0, 0, 0) } } }
    v[1][0][0] = 1.0 / 3 * (w + e)
    v[0][1][0] = 1.0 / 3 * (w + e)
    v[0][0][1] = 1.0 / 3 * (w + e)
    steps[1][0][0] = intArrayOf(-1, 0, 0)
    steps[0][1][0] = intArrayOf(0, -1, 0)
    steps[0][0][1] = intArrayOf(0, 0, -1)
    var bestCombination = intArrayOf(0, 0, 1)
    var bestCombinationValue = v[0][0][1]
    (0..SEQUENCE_LENGTH).forEach { r ->
        (0..SEQUENCE_LENGTH - r).forEach { p ->
            (0..SEQUENCE_LENGTH - r - p).forEach { s ->
                val n = r + p + s
                if (n >= 2) {
                    val (newValue, step) = calculateNewValue(v, r, p, s, w, e)
                    v[r][p][s] = newValue
                    steps[r][p][s] = step
                    if (n == SEQUENCE_LENGTH && newValue > bestCombinationValue) {
                        bestCombination = intArrayOf(r, p, s)
                        bestCombinationValue = newValue
                    }
                }
            }
        }
    }
    return findPath(bestCombination, steps)
}

private fun calculateNewValue(
    v: Array<Array<DoubleArray>>,
    r: Int,
    p: Int,
    s: Int,
    w: Int,
    e: Int
): Pair<Double, IntArray> {
    val n = r + p + s
    return arrayOf(
        (if (r > 0) v[r - 1][p][s] + p.toDouble() * w / (n - 1) + s.toDouble() * e / (n - 1) else -1.0) to intArrayOf(
            -1,
            0,
            0
        ),
        (if (p > 0) v[r][p - 1][s] + s.toDouble() * w / (n - 1) + r.toDouble() * e / (n - 1) else -1.0) to intArrayOf(
            0,
            -1,
            0
        ),
        (if (s > 0) v[r][p][s - 1] + r.toDouble() * w / (n - 1) + p.toDouble() * e / (n - 1) else -1.0) to intArrayOf(
            0,
            0,
            -1
        ),
    ).maxByOrNull { pair -> pair.first } ?: (-1.0 to intArrayOf(0, 0, 0))
}


private fun findPath(bestCombination: IntArray, steps: Array<Array<Array<IntArray>>>): String {
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
