import java.lang.StringBuilder

object Day21  {
    const val TARGET_SCORE_PART_1 = 1000
    const val TARGET_SCORE_PART_2 = 21
}

fun main() {
    val positions = mapOf(Pair(Player.ONE, 6), Pair(Player.TWO, 8))
    println(part1(Day21.TARGET_SCORE_PART_1, positions))
    println(part2(Day21.TARGET_SCORE_PART_2, positions))
}

private fun part1(target: Int, positions: Map<Player, Int>): Outcome {
    val score1 = PlayerScore(Player.ONE, positions[Player.ONE]!!)
    val score2 = PlayerScore(Player.TWO, positions[Player.TWO]!!)
    val die = DeterministicDie()
    while (true) {
        if (score1.move(die.roll() + die.roll() + die.roll()) >= target) {
            return Outcome(score1, score2, die)
        } else if (score2.move(die.roll() + die.roll() + die.roll()) >= target) {
            return Outcome(score2, score2, die)
        }
    }
}

private fun part2(target: Int, positions: Map<Player, Int>): Map<Player, Long> {
    val rolls: Map<Int, Int> = generateAllRolls(1..3)
    println(rolls)
    return rollPart2(
        PlayerScore(Player.ONE, positions[Player.ONE]!!),
        PlayerScore(Player.TWO, positions[Player.TWO]!!),
        target, rolls)
}

private fun rollPart2(currentPlayer: PlayerScore,
                      lastPlayer: PlayerScore,
                      target: Int,
                      rolls: Map<Int, Int>): Map<Player, Long> {
    val outcomes = mutableMapOf<Player, Long>()
    for ((roll, count) in rolls.entries) {
        val splitPlayer = currentPlayer.split(roll)
        val universe: Map<Player, Long> = if (splitPlayer.score >= target) {
            mapOf(Pair(splitPlayer.player, 1), Pair(lastPlayer.player, 0))
        } else {
            rollPart2(lastPlayer, splitPlayer, target, rolls)
        }
        universe.forEach { (player, winUniverses) ->
            outcomes.merge(player, winUniverses * count) { a, b ->
                a + b
            }
        }
    }
    return outcomes
}

private fun generateAllRolls(range: IntRange): Map<Int, Int> {
    val result = mutableMapOf<Int, Int>()
    for (dieA in range) {
        for (dieB in range) {
            for (dieC in range) {
                result.compute(dieA + dieB + dieC) { _, v ->
                    (v ?: 0) + 1
                }
            }
        }
    }
    return result
}

data class Outcome(val winner: PlayerScore, val loser: PlayerScore, val die: Dice) {
    override fun toString(): String {
        val builder = StringBuilder()
        builder.append("Loser score: ${loser.score}\n")
            .append("Winner score: ${winner.score}\n")
            .append("Die rolls: ${die.rollCount}\n")
            .append(loser.score * die.rollCount)
        return builder.toString()
    }
}