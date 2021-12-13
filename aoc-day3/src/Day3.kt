import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow

fun main() {
    val filename = File("input");
    val contents = parse(filename);
    part1(contents);
    part2(contents);
}

fun parse(path: File): ArrayList<ArrayList<Int>> {
    val bits = ArrayList<ArrayList<Int>>();
    path.forEachLine {
        if (it.isEmpty()) {
            return@forEachLine;
        }
        val current = ArrayList<Int>();
        for (i in it.indices) {
            if (it[i] == '1') {
                current.add(1);
            } else {
                current.add(0);
            }
        }
        bits.add(current);
    }
    return bits;
}

fun countBits(contents: List<List<Int>>, value: Int): Array<Int> {
    val counts = Array(contents.first().size) { _ -> 0 }
    contents.forEach { row ->
        row.forEachIndexed { idx, col ->
            if (col == value) {
                counts[idx]++;
            }
        }
    }
    return counts;
}

fun part2(contents: ArrayList<ArrayList<Int>>) {
    val oxygen = filter(contents, OxygenGenerator())?.toTypedArray()
    val cO2Scrubber = filter(contents, CO2Scrubber())?.toTypedArray()
    println("Oxygen: $oxygen")
    println("CO2Scrubber: $cO2Scrubber")
    println(binToDec(oxygen!!) * binToDec(cO2Scrubber!!));
}

fun filter(contents: ArrayList<ArrayList<Int>>, filter: Filter): List<Int>? {
    var current: List<List<Int>> = contents
    for (bitIdx in 0..contents.size) {
        current = filter.filter(current, bitIdx)
        if (current.size < 2) {
            break
        }
    }
    return current.singleOrNull()
}

fun part1(contents: ArrayList<ArrayList<Int>>) {
    val ones = countBits(contents, 1)
    val gamma = Array(ones.size) { _ -> 0 }
    val epsilon = Array(ones.size) { _ -> 0 }
    for (i in ones.indices) {
        val oneCount = ones[i];
        val zeroCount = contents.size - ones[i];
        if (oneCount > zeroCount) {
            gamma[i] = 1;
            epsilon[i] = 0;
        } else {
            gamma[i] = 0;
            epsilon[i] = 1;
        }
    }
    println("Gamma: ${Arrays.toString(gamma)}")
    println("Epsilon: ${Arrays.toString(epsilon)}")
    val gammaDec = binToDec(gamma);
    val epsilonDec = binToDec(epsilon);
    println(gammaDec * epsilonDec);
}

fun binToDec(bin: Array<Int>): Int {
    var total = 0;
    for (i in bin.indices) {
        val multiplier = 2.toDouble().pow(i.toDouble());
        val current = bin[bin.size - 1 - i];
        total += (current * multiplier).toInt();
    }
    return total;
}