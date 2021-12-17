import kotlin.math.abs

fun main() {
    val input = "x=20..30, y=-10..-5"
    // val input = "x=282..314, y=-80..-45"
    Day17.part1(TargetArea.parse(input))
    Day17.part2(TargetArea.parse(input))
}

object Day17 {
    fun launch(target: TargetArea, x: Int, y: Int): List<ProbeTrajectory>? {
        var trajectory = ProbeTrajectory(0, 0, x, y)
        val path = mutableListOf(trajectory)
        while (trajectory.compare(target) <= 0) {
            trajectory = trajectory.tick()
            path.add(trajectory)
            if (trajectory.compare(target) == 0) {
                return path
            }
        }
        return null
    }

    private fun generatePaths(target: TargetArea): List<List<ProbeTrajectory>> {
        val validHeights = mutableListOf<List<ProbeTrajectory>?>()
        for (x in 1..target.xRange.max) {
            for (y in target.yRange.min..abs(target.yRange.min)) {
                validHeights.add(launch(target, x, y))
            }
        }
        return validHeights.filterNotNull()
    }

    fun part1(target: TargetArea) {
        val highestY = generatePaths(target).flatten().maxOf { it.yPos }
        println(highestY)
    }

    fun part2(target: TargetArea) {
        val validPaths = generatePaths(target)
        println(validPaths.size)
    }
}