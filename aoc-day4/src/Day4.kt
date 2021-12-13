import java.io.File
import java.text.NumberFormat

object Day4 {
    const val BOARD_SIZE = 5
    val NUMBER_PARSER: NumberFormat = NumberFormat.getIntegerInstance()
}

fun main() {
    val file = File("input")
    val input = file.readLines()
    val numbers: List<Int>
    val boards: List<Board>
    input.iterator().also { it ->
        numbers = readNumbers(it.next())
        it.next() // Skip blank line
        boards = readBoards(it)
    }
    boards.forEach {
        println(it)
    }

    println("Final score from part 1: " + part1(numbers, boards))
    println("Final score from part 2: " + part2(numbers, boards))
}

fun part1(numbers: List<Int>, boards: List<Board>): Int {
    numbers.forEach { num ->
        // Match first, then score
        boards.forEach { it.match(num) }
        val matched = boards.filter { it.isMatched() }
        if (matched.isNotEmpty()) {
            return num * matched.maxOf { it.score() }
        }
    }
    return 0
}

fun part2(numbers: List<Int>, boards: List<Board>): Int? {
    var remainingBoards: List<Board> = ArrayList(boards)
    val scores = ArrayList<Int>()
    numbers.forEach { num ->
        // Match first, then score
        remainingBoards.forEach { it.match(num) }
        val matched = remainingBoards.filter { it.isMatched() }
        if (matched.isNotEmpty()) {
            remainingBoards = remainingBoards.filterNot { it.isMatched() }
            scores.addAll(matched.map { it.score() * num })
        }
    }
    return scores.last()
}

fun readBoards(iterator: Iterator<String>): List<Board> {
    val boards = ArrayList<Board>()
    while (iterator.hasNext()) {
        boards.add(Board.read(iterator))
        if (iterator.hasNext()) {
            // Skip blank line
            iterator.next()
        }
    }
    return boards
}

fun readNumbers(line: String): List<Int> = line.split(",").map {
    Day4.NUMBER_PARSER.parse(it).toInt()
}