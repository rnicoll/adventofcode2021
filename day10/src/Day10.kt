import java.nio.file.Files
import java.nio.file.Path
import java.util.*

object Day10 {
    val PART1_SCORES: Map<Char, Int> = mapOf(
        Pair(')', 3),
        Pair(']', 57),
        Pair('}', 1197),
        Pair('>', 25137)
    )
    val PART2_SCORES: Map<Char, Int> = mapOf(
        Pair(')', 1),
        Pair(']', 2),
        Pair('}', 3),
        Pair('>', 4)
    )
    val COMPLETION: Map<Char, Char> = mapOf(
        Pair('(', ')'),
        Pair('[', ']'),
        Pair('{', '}'),
        Pair('<', '>')
    )
}

fun main() {
    val commands = Files.readAllLines(Path.of("input"))
        .map { it.trim() }
        .filter { it.isNotBlank() }
    part1(commands)
    part2(commands)
}

fun part1(commands: Iterable<String>) {
    val incorrect = mutableListOf<Char>()
    commands.forEach { command ->
        val stack = Stack<Char>()
        for (i in command.indices) {
            when (val c = command[i]) {
                '(' -> stack.add(c)
                '[' -> stack.add(c)
                '{' -> stack.add(c)
                '<' -> stack.add(c)
                ')' -> {
                    if (stack.pop() != '(') {
                        incorrect.add(c)
                        break
                    }
                }
                '}' -> {
                    if (stack.pop() != '{') {
                        incorrect.add(c)
                        break
                    }
                }
                ']' -> {
                    if (stack.pop() != '[') {
                        incorrect.add(c)
                        break
                    }
                }
                '>' -> {
                    if (stack.pop() != '<') {
                        incorrect.add(c)
                        break
                    }
                }
            }
        }
    }
    println(incorrect.mapNotNull { Day10.PART1_SCORES[it] }.sum())
}

fun part2(commands: Iterable<String>) {
    val scores = commands.map { command ->
        val missing = completeLine(command)
        var lineScore: Long = 0L
        missing.mapNotNull { Day10.PART2_SCORES[it] }.forEach {
            lineScore = lineScore * 5 + it
        }
        lineScore
    }.filter { it != 0L }
        .sorted()
    println(scores)
    println(scores[(scores.size - 1) / 2])
}

fun completeLine(command: String): Iterable<Char> {
    val stack = Stack<Char>()
    for (i in command.indices) {
        when (val c = command[i]) {
            '(', '[', '{', '<' -> stack.add(c)
            ')', '}', ']', '>' -> {
                if (Day10.COMPLETION[stack.pop()] != c) {
                    return emptyList()
                }
            }
        }
    }
    return stack.mapNotNull { Day10.COMPLETION[it] }.toList().reversed()
}