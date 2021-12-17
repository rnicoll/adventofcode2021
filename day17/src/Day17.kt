import kotlin.math.abs
import kotlin.math.max

fun main() {
    val input = "x=20..30, y=-10..-5"
    // val input = "x=282..314, y=-80..-45"
    Day17.part1(Target.parse(input))
    Day17.part2(Target.parse(input))
}

object Day17 {
    fun launch(target: Target, x: Int, y: Int): Int? {
        var trajectory = ProbeTrajectory(0, 0, x, y)
        var maxY: Int = Int.MIN_VALUE
        while (trajectory.compare(target) <= 0) {
            trajectory = trajectory.tick()
            maxY = max(maxY, trajectory.yPos)
            if (trajectory.compare(target) == 0) {
                return maxY
            }
        }
        return null
    }

    fun part1(target: Target) {
        val validHeights = mutableListOf<Int?>()
        for (x in 1..target.xRange.max) {
            for (y in 1..abs(target.yRange.min)) {
                validHeights.add(launch(target, x, y))
            }
        }
        val highestY = validHeights.filterNotNull().maxOf { it }
        println(highestY)
    }

    fun part2(target: Target) {
        val validHeights = mutableListOf<Int?>()
        for (x in 1..target.xRange.max) {
            for (y in target.yRange.min..abs(target.yRange.min)) {
                validHeights.add(launch(target, x, y))
            }
        }
        println(validHeights.filterNotNull().size)
    }
}