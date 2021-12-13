import java.nio.file.Files
import java.nio.file.Path
import java.util.*

fun main() {
    val map: Array<IntArray> = Files.readAllLines(Path.of("input")).mapNotNull { line ->
        if (line.isBlank()) {
            null
        } else {
            line.toCharArray().map { it.toString().toInt() }.toIntArray()
        }
    }.toTypedArray()
    part1(map)
    part2(SeaMap(map))
}

fun part1(map: Array<IntArray>) {
    val lowest = mutableListOf<Int>()
    for (row in map.indices) {
        for (col in map[row].indices) {
            val value = map[row][col]
            if (getValue(map, row, col - 1) > value
                && getValue(map, row, col + 1) > value
                && getValue(map, row - 1, col) > value
                && getValue(map, row + 1, col) > value) {
                lowest.add(value)
            }
        }
    }
    println(lowest.sum() + lowest.count())
}

fun part2(map: SeaMap) {
    val basins = mutableListOf<Set<Coord>>()
    val seen = mutableSetOf<Coord>()

    for (row in 0 until map.rows) {
        for (col in 0 until map.cols) {
            val coord = Coord(row, col)
            val value = map.get(coord)
            if (value < 9) {
                if (coord !in seen) {
                    val basin = getBasin(map, coord, seen)
                    basins.add(basin)
                    seen.addAll(basin)
                }
            }
        }
    }
    val sizes: List<Int> = basins.map { it.size }.sorted().reversed()
    println(sizes)
    var total = 1
    for (i in 0 until 3) {
        total *= (sizes[i])
    }
    println(total)
}

fun getBasin(map: SeaMap, start: Coord, seen: Set<Coord>): Set<Coord> {
    val coords = mutableSetOf<Coord>()
    val toProcess = Stack<Coord>()
    toProcess.add(start)
    while (toProcess.isNotEmpty()) {
        val centre = toProcess.pop()
        coords.add(centre)
        val around = listOfNotNull(centre.west(), centre.east(map), centre.north(), centre.south(map))
            .filter { map.get(it) < 9 }
            .filter { it !in seen }
            .filter { it !in coords }
        toProcess.addAll(around)
    }
    return coords
}

fun getValue(map: Array<IntArray>, row: Int, col: Int): Int {
    if (row < 0 || row >= map.size) {
        return Int.MAX_VALUE
    }
    if (col < 0 || col >= map[row].size) {
        return Int.MAX_VALUE
    }
    return map[row][col]
}