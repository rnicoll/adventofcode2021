import java.nio.file.Files
import java.nio.file.Path
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.streams.toList

fun main() {
    val file = Path.of("input")
    val lines = Files.lines(file).map { line -> parseLine(line) }.toList()
    part1(lines)
    part2(lines)
}

fun part1(lines: Iterable<Line>) {
    val seenOnce = mutableSetOf<Coord>()
    val seenTwice = mutableSetOf<Coord>()

    lines.filter {
        it.from.x == it.to.x ||
                it.from.y == it.to.y
    }.forEach { line ->
        getCoords(line.from, line.to).forEach {
            if (!seenOnce.add(it)) {
                seenTwice.add(it)
            }
        }
    }
    println("Part 1: ${seenTwice.count()}")
}

fun part2(lines: Iterable<Line>) {
    val seenOnce = mutableSetOf<Coord>()
    val seenTwice = mutableSetOf<Coord>()

    lines.forEach { line ->
        getCoords2(line.from, line.to).forEach { coord ->
            if (!seenOnce.add(coord)) {
                seenTwice.add(coord)
            }
        }
    }
    println("Part 2: ${seenTwice.count()}")
}

fun getCoords(from: Coord, to: Coord): Iterable<Coord> {
    val coords = mutableListOf<Coord>()
    when {
        (from.y == to.y) -> {
            for (x in min(from.x, to.x)..max(from.x, to.x)) {
                coords.add(Coord(x, from.y))
            }
        }
        (from.x == to.x) -> {
            for (y in min(from.y, to.y)..max(from.y, to.y)) {
                coords.add(Coord(from.x, y))
            }
        }
        else -> {
            throw Exception("Invalid line")
        }
    }
    return coords
}

fun getCoords2(from: Coord, to: Coord): Iterable<Coord> {
    val coords = mutableListOf<Coord>()
    val distanceX = abs(from.x - to.x)
    val distanceY = abs(from.y - to.y)
    val delta = max(distanceX, distanceY)
    val deltaX = (to.x - from.x) / delta
    val deltaY = (to.y - from.y) / delta

    for (i in 0..delta) {
        coords.add(Coord(from.x + (i * deltaX), from.y + (i * deltaY)))
    }

    return coords
}

fun parseLine(input: String): Line {
    val parts = input.split(" ")
    assert(parts.size == 3)
    return Line(Coord.parse(parts[0]), Coord.parse(parts[2]))
}

data class Coord(val x: Int, val y: Int): Comparable<Coord> {
    companion object {
        fun parse(input: String) = input.split(",").let {
            Coord(it[0].toInt(), it[1].toInt())
        }
    }

    override fun toString(): String {
        return "$x,$y"
    }

    override fun compareTo(other: Coord): Int {
        return (this.x - other.x) - (this.y - other.y);
    }
}

data class Line(val from: Coord, val to: Coord) {
    override fun toString(): String {
        return "$from -> $to"
    }
}
