import java.nio.file.Files
import java.nio.file.Path
import java.util.*

fun main() {
    val grid = Files.readAllLines(Path.of("input")).map {
        line -> line.toCharArray().map { it.toString().toInt() }.toIntArray()
    }.filter {it.isNotEmpty()}.toTypedArray()
    part1(grid.copyOf().map { it -> it.copyOf() }.toTypedArray())
    part2(grid.copyOf().map { it -> it.copyOf() }.toTypedArray())
}

fun part1(grid: Array<IntArray>) {
    var flashes = 0
    for (i in 0 until 100) {
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                grid[row][col]++
            }
        }

        val flashed = mutableSetOf<Coord>()
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                val toRaise = Stack<Coord>()
                if (grid[row][col] > 9) {
                    val coord = Coord(row, col)
                    if (flashed.add(coord)) {
                        flashes++
                    } else {
                        // Already flashed this octopus, skip
                        continue
                    }
                    toRaise.addAll(coord.getNearby(grid))
                }
                while (toRaise.isNotEmpty()) {
                    val coord = toRaise.pop()
                    grid[coord.row][coord.col]++
                    if (grid[coord.row][coord.col] > 9 && !flashed.contains(coord)) {
                        flashes++
                        flashed.add(coord)
                        toRaise.addAll(coord.getNearby(grid))
                    }
                }
            }
        }
        flashed.forEach {
            grid[it.row][it.col] = 0
        }
    }
    println(flashes)
}

fun part2(grid: Array<IntArray>) {
    for (step in 0..Int.MAX_VALUE) {
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                grid[row][col]++
            }
        }

        val flashed = mutableSetOf<Coord>()
        for (row in grid.indices) {
            for (col in grid[row].indices) {
                val toRaise = Stack<Coord>()
                if (grid[row][col] > 9) {
                    val coord = Coord(row, col)
                    if (!flashed.add(coord)) {
                        // Already flashed this octopus, skip
                        continue
                    }
                    toRaise.addAll(coord.getNearby(grid))
                }
                while (toRaise.isNotEmpty()) {
                    val coord = toRaise.pop()
                    grid[coord.row][coord.col]++
                    if (grid[coord.row][coord.col] > 9 && !flashed.contains(coord)) {
                        flashed.add(coord)
                        toRaise.addAll(coord.getNearby(grid))
                    }
                }
            }
        }
        flashed.forEach {
            grid[it.row][it.col] = 0
        }
        for (element in grid) {
            println(element.joinToString("") { it.toString() })
        }
        println("")
        if (flashed.size == (grid.size * grid[0].size)) {
            println(step)
            break
        }
    }
}

data class Coord(val row: Int, val col: Int) {
    fun getNearby(grid: Array<IntArray>) = listOf<Coord>(
            Coord(row - 1, col - 1),
            Coord(row - 1, col),
            Coord(row - 1, col + 1),
            Coord(row, col - 1),
            Coord(row, col + 1),
            Coord(row + 1, col - 1),
            Coord(row + 1, col),
            Coord(row + 1, col + 1)
        ).filter { it.isValid(grid) }

    private fun isValid(grid: Array<IntArray>) = row >= 0 && col >= 0
            && row < grid.size && col < grid[row].size
}