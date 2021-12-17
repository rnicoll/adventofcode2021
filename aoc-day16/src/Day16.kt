import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Path

fun main() {
    val inputs = Files.readAllLines(Path.of("input")).mapNotNull { line ->
        if (line.isBlank()) {
            null
        } else {
            line.trim()
        }
    }
    inputs.forEach { input ->
        println(input)
        part1(parseHexToBinary(input))
        part2(parseHexToBinary(input))
    }
}

fun parseHexToBinary(input: String) = ArrayDeque<Byte>().also {
    it.addAll(input.flatMap { digit ->
        digit.digitToInt(16).toBits()
    })
}

fun part1(input: ArrayDeque<Byte>) {
    val packet = readPacket(input)
    println(packet.versions)
}

fun part2(input: ArrayDeque<Byte>) {
    val packet = readPacket(input)
    println(packet)
    println(packet.eval)
}

fun readPacket(input: ArrayDeque<Byte>): Packet {
    val version = readInt(input, 3)
    return when (val type = readInt(input, 3)) {
        Packet.TYPE_LITERAL -> {
            LiteralPacket.parse(input, version)
        }
        else -> {
            OperatorPacket.parse(input, version, type)
        }
    }
}

fun Collection<Byte>.toBigInteger(): BigInteger {
    var value = BigInteger.ZERO
    this.reversed().forEachIndexed { i, byte ->
        value += when(byte.toInt()) {
            1 -> {
                BigInteger.ONE.shiftLeft(i)
            }
            else -> {
                BigInteger.ZERO
            }
        }
    }
    return value
}

fun Collection<Byte>.toInt(): Int {
    var value = 0
    this.reversed().forEachIndexed { i, byte ->
        value += byte.toInt().shl(i)
    }
    return value
}

fun <T> ArrayDeque<T>.removeFirst(count: Int): List<T> {
    val newStack = mutableListOf<T>()
    for (i in 0 until count) {
        newStack.add(this.removeFirst())
    }
    return newStack
}

fun readInt(input: ArrayDeque<Byte>, count: Int) = input.removeFirst(count).toInt()

fun Int.toBits(): List<Byte> {
    val bits = mutableListOf<Byte>()
    var current = this
    for (i in 0..3) {
        bits.add(current.and(1).toByte())
        current = current.shr(1)
    }
    return bits.reversed()
}