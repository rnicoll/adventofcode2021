import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs
import kotlin.math.min


fun main() {
    val positions = Files.readAllLines(Path.of("input"))
        .first().trim().split(",").map { it.toInt() }
        .toList().sorted()
    part1(positions)
    part2(positions)
}

fun part1(positions: List<Int>) {
    val min = positions.minOrNull()!!
    val max = positions.maxOrNull()!!
    var bestFuel = Int.MAX_VALUE
    for (pos in min..max) {
        var fuel = 0
        positions.forEach {
            fuel += abs(it - pos)
        }
        bestFuel = min(bestFuel, fuel)
    }
    println("Fuel: $bestFuel")
}

fun part2(positions: List<Int>) {
    val min = positions.minOrNull()!!
    val max = positions.maxOrNull()!!
    var bestFuel = Long.MAX_VALUE
    for (pos in min..max) {
        var fuel = 0L
        positions.forEach {
            var crabFuel = 0
            val distance = abs(it - pos)
            for (i in 0..distance) {
                crabFuel += i
            }
            // println("Pos $pos, crab $it, fuel $crabFuel")
            fuel += crabFuel
        }
        // println("Total fuel at pos $pos is $fuel")
        bestFuel = min(bestFuel, fuel)
        // println("")
    }
    println("Fuel: $bestFuel")
}