package roundC2021

fun main() {
    val days = readLine()!!.toInt()
    readLine()
    (1..days).forEach { dayIndex ->
        val (W, E) = readLine()!!.split(" ").map { it.toInt() }
        println("Case #$dayIndex: ${getSequence(W, E)}")
    }
}

private fun getSequence(W: Int, E: Int) = when (E) {
    W -> "RSPRSPRSPRSPRSPRSPRSPRSPRSPRSPRSPRSPRSPRSPRSPRSPRSPRSPRSPRSP"
    W / 2 -> "RSSPPPPRRRRRRSSSSSSSSSSSPPPPPPPPPPPPPPPPPPRRRRRRRRRRRRRRRRRR"
    W / 10 -> "RSSSPPPPPPPPPRRRRRRRRRRRRRRRRRRRRRRRRSSSSSSSSSSSSSSSSSSSSSSS"
    0 -> "RSSSSSPPPPPPPPPPPPPPPPPPPPPPPPPPPRRRRRRRRRRRRRRRRRRRRRRRRRRR"
    else -> 0.5
}
