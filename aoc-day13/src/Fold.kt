import java.lang.RuntimeException

interface Fold {
    companion object {
        fun parse(input: String): Fold {
            val parts = input.split("=")
            require(parts.size == 2)
            when (parts[0]) {
                "fold along x" -> {
                    return HorizontalFold(parts[1].toInt())
                }
                "fold along y" -> {
                    return VerticalFold(parts[1].toInt())
                }
                else ->
                    throw RuntimeException("Unknown fold ${parts[0]}")
            }
        }
    }
    fun apply(grid: Grid): Grid
}

class VerticalFold(private val y: Int): Fold {
    override fun apply(grid: Grid): Grid {
        println("Grid height ${grid.dots.size}, fold at $y")
        val newGrid = Array(y) { BooleanArray(grid.dots[0].size) { false } }
        println("New height: ${newGrid.size}")
        for (row in newGrid.indices) {
            newGrid[row] = grid.dots[row].copyOf()
        }
        for (distance in 1..y) {
            val postFold = y + distance
            val preFold = y - distance
            if (postFold >= grid.dots.size) {
                break
            }
            for (col in newGrid[preFold].indices) {
                newGrid[preFold][col] = newGrid[preFold][col] || grid.dots[postFold][col]
            }
        }
        return Grid(newGrid)
    }
}

class HorizontalFold(private val x: Int): Fold {
    override fun apply(grid: Grid): Grid {
        val newGrid = Array(grid.dots.size) { BooleanArray(x) { false } }
        for (row in grid.dots.indices) {
            for (col in newGrid[row].indices) {
                newGrid[row][col] = grid.dots[row][col]
            }
            for (distance in 1..x) {
                val postFold = x + distance
                val preFold = x - distance
                for (col in newGrid[preFold].indices) {
                    newGrid[row][preFold] = newGrid[row][preFold] || grid.dots[row][postFold]
                }
            }
        }
        return Grid(newGrid)
    }
}