import java.nio.file.Files
import java.nio.file.Path

object Day6 {
    const val PART_1_MAX = 8
    const val PART_1_RESET = 6
    const val PART_1_DAYS = 80
    const val PART_2_DAYS = 256
}

fun main() {
    val path = Path.of("input")
    val ages = Files.readAllLines(path)
        .first()
        .split(",")
        .map { age -> age.toInt() }
        .toList()
    part1(ages)
    part2(ages)
}

fun part1(input: Iterable<Int>) {
    val ages = Array(Day6.PART_1_MAX + 1) { 0 }
    input.forEach {
        ages[it + 1]++
    }
    for (day in 0 .. Day6.PART_1_DAYS) {
        val zeroFish = ages[0]
        for (i in 1 until ages.size) {
            ages[i - 1] = ages[i]
        }
        ages[Day6.PART_1_RESET] += zeroFish
        ages[Day6.PART_1_MAX] = zeroFish
    }
    println(ages.sum())
}

fun part2(input: Iterable<Int>) {
    val ages = Array<Long>(Day6.PART_1_MAX + 1) { 0 }
    input.forEach {
        ages[it + 1]++
    }
    for (day in 0 .. Day6.PART_2_DAYS) {
        val zeroFish = ages[0]
        for (i in 1 until ages.size) {
            ages[i - 1] = ages[i]
        }
        ages[Day6.PART_1_RESET] += zeroFish
        ages[Day6.PART_1_MAX] = zeroFish
    }
    println(ages.sum())
}