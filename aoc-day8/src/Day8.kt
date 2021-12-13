import java.nio.file.Files
import java.nio.file.Path
import java.util.*

fun main() {
    val scenarios = Files.readAllLines(Path.of("input")).map {
        Scenario.parse(it)
    }.toList()
    part1(scenarios)
    part2(scenarios)
}

fun part1(scenarios: List<Scenario>) {
    val uniqueDigits = EnumSet.of(Digit.ONE, Digit.FOUR, Digit.SEVEN, Digit.EIGHT)
    val uniqueCounts = uniqueDigits.map { it.active.size }.toSet()
    val uniques = scenarios.sumOf { scenario ->
        scenario.outputs.filter { uniqueCounts.contains(it.size) }.count()
    }
    println("$uniques")
}

fun part2(scenarios: List<Scenario>) {
    var total = 0
    scenarios.forEach { scenario ->
        val display = resolveScenario(scenario.inputs, scenario.outputs)
        total += display.toInt()
        // println(display)
    }
    println(total)
}

private fun resolveScenario(inputs: List<EnumSet<Signal>>, outputs: List<EnumSet<Signal>>): String {
    val digits = extractDigits(inputs)
    return outputs.map {
        digits.indexOf(it)
    }.joinToString("")
}

private fun extractDigits(inputs: List<EnumSet<Signal>>): Array<EnumSet<Signal>> {
    // Mapping from signal, to the potential display position it relates to
    val digits = Array<EnumSet<Signal>>(10) { EnumSet.noneOf(Signal::class.java) }
    var remaining: List<EnumSet<Signal>> = mutableListOf<EnumSet<Signal>>().also {
        it.addAll(inputs)
    }
    digits[1] = inputs.single { it.size == 2 }
    digits[4] = inputs.single { it.size == 4 }
    digits[7] = inputs.single { it.size == 3 }
    digits[8] = inputs.single { it.size == 7 }
    remaining = remaining.filter { it !in digits }
    require(remaining.size == 6)

    val top: Signal = digits[7].single { it !in digits[1] }
    println("For inputs $inputs top segment must be $top")
    val zeroSixOrNine = inputs.filter { it.size == 6 }
    require(zeroSixOrNine.size == 3)
    digits[6] = findSix(zeroSixOrNine, digits[1])
    remaining = remaining.filter { it !in digits }
    require(remaining.size == 5)

    val lowerRight = digits[6].single { it in digits[1] }
    val upperRight = digits[1].single() { it != lowerRight }
    digits[5] = inputs.filter { !it.contains(upperRight) }.single { it != digits[6] }
    remaining = remaining.filter { it !in digits }
    require(remaining.size == 4)

    val lowerLeft = digits[6].single { it !in digits[5] }
    digits[9] = zeroSixOrNine.single { candidate -> !candidate.contains(lowerLeft) }
    remaining = remaining.filter { it !in digits }
    require(remaining.size == 3)
    digits[0] = zeroSixOrNine.single { it != digits[6] && it != digits[9] }
    remaining = remaining.filter { it !in digits }
    require(remaining.size == 2)

    // 2, 3 left to find
    digits[2] = remaining.single { it.contains(lowerLeft) }
    digits[3] = remaining.single { !it.contains(lowerLeft) }

    return digits
}

private fun findSix(candidates: List<EnumSet<Signal>>, one: EnumSet<Signal>)
    = candidates.single { input ->
        overlap(input, one) == 1
}

private fun overlap(a: EnumSet<Signal>, b: EnumSet<Signal>) = a.count { it in b }