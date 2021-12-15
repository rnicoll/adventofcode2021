import java.nio.file.Files
import java.nio.file.Path
import java.util.*

fun main() {
    val map = Files.readAllLines(Path.of("input_short")).map { line ->
        line.toCharArray().map {
            it.toString().toInt()
        }.toIntArray()
    }.toTypedArray()
    println(part1(map))
    // println(part2(map))
}

private fun part1(map: Array<IntArray>) = route(CaveMap(map), Coord(0, 0))
private fun part2(map: Array<IntArray>): Int? {
    val expandedMap = Array<IntArray>(map.size * 5) { col ->
        IntArray(map[0].size * 5) { 0 } }
    for (colAdd in 0..4) {
        for (rowAdd in 0..4) {
            for (col in map.indices) {
                for (row in map[col].indices) {
                    val expandedRow = row + (map.size * rowAdd)
                    val expandedCol = col + (map.size * colAdd)
                    expandedMap[expandedCol][expandedRow] = (map[col][row] + colAdd + rowAdd)
                    while (expandedMap[expandedCol][expandedRow] > 9) {
                        expandedMap[expandedCol][expandedRow] -= 9
                    }
                }
            }
        }
    }
    return route(CaveMap(expandedMap), Coord(0, 0))
}

private fun route(map: CaveMap, source: Coord): Int? {
    val q: PriorityQueue<CoordWithDistance> = PriorityQueue<CoordWithDistance>()
    val dist: MutableMap<Coord, Int> = mutableMapOf()
    val prev: MutableMap<Coord, Coord> = mutableMapOf()

    for (row in map.costs.indices) {
        for (col in map.costs.indices) {
            if (source.row == row && source.col == col) {
                // Skip
                continue
            }
            val coord = CoordWithDistance(col, row, Int.MAX_VALUE / 2)
            dist[coord] = coord.distance
            q.add(coord)
        }
    }
    dist[source] = 0
    q.add(CoordWithDistance(source.col, source.row, 0))

    while (q.isNotEmpty()) {
        val u = q.poll()
        println("Lowest: $u")
        if (u.col == map.cols - 1 && u.row == map.rows - 1) {
            // We're at the end
            var current = Coord(map.cols - 1, map.rows - 1)
            while (current != source) {
                println(current)
                current = prev[current]!!
            }
            return u.distance
        }

        u.adjacent(map).filter { q.contains(it) }.forEach { v ->
            val alt = dist[u]!! + map.get(v)
            if (alt < dist[v]!!) {
                val newCoord = CoordWithDistance(v.col, v.row, alt)
                // Replace the coordinate in the priority queue
                q.remove(newCoord)
                q.add(newCoord)
                prev[v] = u
            }
        }
    }
    return null
}

class CoordWithDistance(col: Int, row: Int, var distance: Int) : Coord(col, row), Comparable<CoordWithDistance> {
    override fun compareTo(other: CoordWithDistance): Int {
        return this.distance - other.distance
    }

    override fun equals(other: Any?) = other is Coord && other.col == this.col && other.row == this.row
    override fun hashCode(): Int {
        var hash = 1
        hash = hash * 31 + row
        hash = hash * 31 + col
        return hash
    }

    override fun toString() = "$row,$col: $distance"
}
