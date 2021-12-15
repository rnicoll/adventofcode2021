import java.nio.file.Files
import java.nio.file.Path
import java.util.*

fun main() {
    val map = Files.readAllLines(Path.of("input")).map { line ->
        line.toCharArray().map {
            it.toString().toInt()
        }.toIntArray()
    }.toTypedArray()
    println(part1(map))
    println(part2(map))
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

    dist[source] = 0
    for (row in map.costs.indices) {
        for (col in map.costs.indices) {
            val v = Coord(col, row)
            if (v != source) {
                dist[v] = Int.MAX_VALUE / 2
            }
            q.add(CoordWithDistance(v, dist[v]!!))
        }
    }

    val end = Coord(map.cols - 1, map.rows - 1)
    while (q.isNotEmpty()) {
        val u = q.poll().coord
        println(q.size)
        if (u == end) {
            // We're at the end
            var current = Coord(map.cols - 1, map.rows - 1)
            while (current != source) {
                println(current)
                current = prev[current]!!
            }
            return dist[u]
        }

        u.adjacent(map).forEach { v ->
            val alt = dist[u]!! + map.get(v)
            if (alt < dist[v]!!) {
                dist[v] = alt
                prev[v] = u
                val newCoord = CoordWithDistance(v, alt)
                // Replace the coordinate in the priority queue
                q.remove(newCoord)
                q.add(newCoord)
            }
        }
    }
    return null
}
