package roundC2021

import kotlin.random.Random.Default.nextDouble

fun main() {
//    prod()
//    test(40, 40)
//    test(40, 20)
//    test(40, 4)
//    test(40, 0)
//    val seq1 = RPS.getListFromString("RSP".repeat(20))
//    testSequence(seq1, 40, 40)
//    testSequence(seq1, 40, 20)
//    testSequence(seq1, 40, 4)
//    testSequence(seq1, 40, 0)
    printSequences()
}

private fun prod() {
    val days = readLine()!!.toInt()
    readLine()
    (1..days).forEach { dayIndex ->
        val (W, E) = readLine()!!.split(" ").map { it.toInt() }
        val chance = getChance(W, E)
        val (mySequence) = calculateSequencesFromChanceLimit(chance)
//        val mySequence = RPS.getListFromString("RSP".repeat(20))
        println("Case #$dayIndex: ${mySequence.joinToString("")}")
    }
}

private fun getChance(W: Int, E: Int) = when (E) {
    W -> 0.168
    W / 2 -> 0.522
    W / 10 -> 0.668
    0 -> 0.816
    else -> 0.5
}

private fun printSequences() {
    intArrayOf(40, 20, 4, 0).forEach {
        val (mySequence) = calculateSequencesFromChanceLimit(getChance(40, it))
        println("E: $it")
        println(mySequence.joinToString(separator = ""))
    }
}

private fun test(W: Int, E: Int) {
    val chanceLowers = mutableListOf<Int>()
    val averageScores = mutableListOf<Double>()
    (0 until 1000 step 2).forEach { chanceLower ->
        val chance = chanceLower / 1000.0
        val averageScore = (1..1000).map {
            val (mine, opponents) = calculateSequencesFromChanceLimit(chance)
            calculateScore(mine, opponents, W, E)
        }.average()
        chanceLowers.add(chanceLower)
        averageScores.add(averageScore)
        if (chanceLower == 0) {
            println(averageScore)
        }
        if (chanceLower % 10 == 0)
            print("\r${chanceLower}/1000")
    }
    println()
    val bestChanceLimit = chanceLowers.zip(averageScores).maxByOrNull { pair -> pair.second }!!.first / 1000.0
    println("Best chance limit: $bestChanceLimit")
    println("N = (G,H)")
    println("G = [${chanceLowers.joinToString(separator = ",")}]")
    println("H = [${averageScores.joinToString(separator = ",")}]")
}

private fun testSequence(sequence: List<RPS>, W: Int, E: Int) {
    val averageScore = (1..10000).map {
        val opponents = calculateOpponentsSequenceFromMySequence(sequence)
        calculateScore(sequence, opponents, W, E)
    }.average()
    println(sequence.joinToString(separator = ""))
    println(calculateOpponentsSequenceFromMySequence(sequence).joinToString(separator = ""))
    println(averageScore)
}


private fun calculateOpponentsSequenceFromMySequence(mine: List<RPS>): List<RPS> {
    val opponents =
        (1..60).map { roundIndex ->
            chooseFromMapWithChances(
                RPS.values().associate {
                    it.enemy to (if (roundIndex == 1) 0.33 else mine.subList(0, roundIndex - 1)
                        .count { myChoice -> myChoice == it } / (roundIndex - 1).toDouble())
                }
            )
        }
    return opponents
}

private fun calculateSequencesFromChanceLimit(chance: Double): Pair<List<RPS>, List<RPS>> {
    val mine = mutableListOf<RPS>()
    val opponents = mutableListOf<RPS>()
    var myCurrentChoice = RPS.R
    (1..60).forEach { roundIndex ->
        if (roundIndex > 1 && mine.count { it == myCurrentChoice } / mine.size.toDouble() > chance) {
            myCurrentChoice = myCurrentChoice.target
        }
        mine.add(myCurrentChoice)
        opponents.add(chooseFromMapWithChances(
            RPS.values().associate { it.enemy to mine.count { myChoice -> myChoice == it } / mine.size.toDouble() }
        ))
    }
    return mine to opponents
}

private fun <T> chooseFromMapWithChances(map: Map<T, Double>): T {
    val pairs = map.entries.toList().map { it.key to it.value }
    val runningTotals = pairs.runningFold(0.0) { prev, pair -> prev + pair.second }
        .drop(1).mapIndexed { index, value -> value to pairs[index].first }
    val rand = nextDouble()
    for (pair in runningTotals) {
        val (total, choice) = pair
        if (rand < total) {
            return choice
        }
    }
    return pairs.first().first
}

private fun calculateScore(mine: List<RPS>, opponents: List<RPS>, W: Int, E: Int) =
    mine.zip(opponents).sumOf { (myChoice, opponentsChoice) ->
        if (myChoice == opponentsChoice)
            E
        else if (myChoice.target == opponentsChoice)
            W
        else
            0
    }


private enum class RPS {
    R,
    P,
    S;

    val target: RPS
        get() = when (this) {
            P -> R
            R -> S
            S -> P
        }
    val enemy: RPS
        get() = when (this) {
            P -> S
            S -> R
            R -> P
        }

    companion object {
        fun getListFromString(s: String) =
            s.map {
                when (it) {
                    'P' -> P
                    'R' -> R
                    'S' -> S
                    else -> P
                }
            }
    }
}
