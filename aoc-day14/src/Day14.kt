import java.lang.StringBuilder
import java.nio.file.Files
import java.nio.file.Path

object Day14 {
    val cache: MutableMap<Triple<Char, Char, Int>, Map<Char, Long>> = mutableMapOf()
}

fun main() {
    var input: String? = null
    val instructions = mutableListOf<Instruction>()
    Files.readAllLines(Path.of("input")).spliterator().apply {
        tryAdvance { line -> input = line }
        tryAdvance { // Skip a blank line
        }
        forEachRemaining { it ->
            instructions.add(Instruction.parse(it))
        }
    }
    val mappedInstructions = instructions.groupBy { it.input }
    println(part1(input!!, mappedInstructions))
    println(part2(input!!, mappedInstructions))
}

private fun part1(
    input: String,
    mappedInstructions: Map<String, List<Instruction>>
): Int {
    val result = polymerise(input, mappedInstructions, 10)
    val counts = result
        .toCharArray().toList().groupingBy { it }.eachCount()
    val most = counts.values.maxOf { it }
    val least = counts.values.minOf { it }
    return most - least
}

fun polymerise(input: String, instructions: Map<String, List<Instruction>>, iterations: Int): String {
    // println(input)
    if (iterations == 0) {
        return input
    }
    val builder = StringBuilder(input)
    var i = 0
    while (i < (builder.length - 1)) {
        val pair = builder.substring(i, i + 2)
        val apply = instructions[pair]
        apply?.forEach {
            builder.insert(i + 1, it.insert)
            i++
        }
        i++
    }
    return polymerise(builder.toString(), instructions, iterations - 1)
}

fun part2(input: String, instructions: Map<String, List<Instruction>>): Long {
    val counts = mutableMapOf<Char, Long>()
    for (i in input.indices) {
        counts.compute(input[i]) { _, value ->
            if (value == null) {
                1
            } else {
                value + 1
            }
        }
    }
    for (i in 0..(input.length - 2)) {
        addCounts(input[i], input[i + 1], instructions, 40)
            .forEach { (key, value) -> apply(counts, key, value) }
    }
    val most = counts.values.maxOf { it }
    val least = counts.values.minOf { it }
    return most - least
}

fun addCounts(a: Char, b: Char, instructions: Map<String, List<Instruction>>, iterations: Int): Map<Char, Long> {
    if (iterations == 0) {
        return emptyMap()
    }
    val cacheKey = Triple(a, b, iterations)
    if (Day14.cache.contains(cacheKey)) {
        return Day14.cache[cacheKey]!!
    }
    val pair = StringBuilder().run {
        append(a)
        append(b)
        toString()
    }
    val instruction = instructions[pair]!!.single()
    val counts: MutableMap<Char, Long> = mutableMapOf(Pair(instruction.insert,  1))

    addCounts(a, instruction.insert, instructions, iterations - 1)
        .forEach { (key, value) -> apply(counts, key, value) }
    addCounts(instruction.insert, b, instructions, iterations - 1)
        .forEach { (key, value) -> apply(counts, key, value) }

    Day14.cache[cacheKey] = counts
    return counts
}

private fun apply(counts: MutableMap<Char, Long>, key: Char, value: Long) {
    counts.compute(key) { _, innerValue ->
        when (innerValue) {
            null -> {
                value
            }
            else -> {
                innerValue + value
            }
        }
    }
}